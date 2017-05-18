package instantiation;
import java.util.ArrayList;
import java.util.Scanner;
public class ClockCycle{

	public ArrayList<ArrayList<Instruction>> clockcycle; // arraylist of an arraylist of instructions
						// the outer arraylist represents all the clock cycles
						// the inner arraylist represents each clockcycle containing instructions
	private Integer stallCounter;
	private Integer[][] hazardCounter;
	int currentCC;
	public ClockCycle(){
		stallCounter = 0;
		currentCC = 0;
		clockcycle = new ArrayList<ArrayList<Instruction>>();
	}


	public void buildClockCycles(){	 // called to construct the out of order execution design of instructions
		this.currentCC = 0;
		int clock = 0;
		int limit;
		int stalled;
		int tempCC = 1;

		int maxRow = 5*Initialization.instructions.size();
		if(maxRow == 0) maxRow = 1; 
		this.hazardCounter = new Integer[maxRow][4]; // hazard counter array per clock cycle is assigned
		for(int i = 0; i < maxRow; i++){
			for(int j = 0; j < 4; j++){
				this.hazardCounter[i][j] = 0; // reset values to 0
			}
		}
		do{
			clockcycle.add(new ArrayList<Instruction>()); 
			stalled = 0; // holds the number total number of stalls in the whole cycle
			if(clock!= 0){
				for(int i = 0; i < clockcycle.get(clock-1).size(); i++){ //adds the instructions that are already fetched but not yet done being processed
					if(clockcycle.get(clock-1).get(i).getStatus() < Instruction.END){  
						clockcycle.get(clock).add(new Instruction(clockcycle.get(clock-1).get(i)));
					}
				}
				
			}
			if(clock < Initialization.instructions.size()){ // adds atmost one instruction for every clock cycle
				clockcycle.get(clock).add(new Instruction(Initialization.instructions.get(clock)));
			}

			for(int i = 0; i <  clockcycle.get(clock).size(); i++){
				clockcycle.get(clock).get(i).setStall(false); //resets an instructions boolean stalled to false
				clockcycle.get(clock).get(i).setHazard("");	// clears hazard
				for(int j = i-1; j >= 0; j--){	// compares a current instructions to all instructions before it and searches for dependencies
					if(clockcycle.get(clock).get(i).getStatus() == Instruction.START) break;
					if( checkRAW(clockcycle.get(clock).get(j), clockcycle.get(clock).get(i),tempCC) || 
							checkWAR(clockcycle.get(clock).get(j), clockcycle.get(clock).get(i),tempCC) || 
								checkWAW(clockcycle.get(clock).get(j), clockcycle.get(clock).get(i),tempCC) || 
									checkDuplicateStage(clockcycle.get(clock).get(j), clockcycle.get(clock).get(i),tempCC)) {
						// performs these whenever atleast one hazard is observed
						clockcycle.get(clock).get(i).setStall(true);
						stalled = 1; // there exists atleast one stall in that clock cycle
						break;
					}

				}
				if(!clockcycle.get(clock).get(i).getStall()){ // if that instruction is not stalled, it can now move on the its next stage
					clockcycle.get(clock).get(i).setStatus(
						clockcycle.get(clock).get(i).getStatus() + 1 // moves to next stage
					);

				}				
			}
			stallCounter += stalled; // total stall counter
			clock++;	// next clock cycle
			tempCC++;
		} while(!allInstructionsDone(clock));
	}

	public boolean allInstructionsDone(int clock){ // checks if all instructions have already been executed and performed
		int size = clockcycle.get(clock-1).size();
		for(int i = 0; i<size; i++){
			if(clockcycle.get(clock-1).get(i).getStatus() != Instruction.END) return false;
		}
		return true;
		// clockcycle.get(clock-1).get(clockcycle.get(clock-1).size()-1).getStatus() != Instruction.END
	}

	public void performInstructions(int cc){ // called in the GUI whenever 'next' button is pressed
		// basically it performs all the instructions in the clock cycle number given
		int index = cc-1;
		for(int i = 5; i > 0; i--){
			for(int j = 0; j < clockcycle.get(index).size(); j++){
				if(clockcycle.get(index).get(j).getStatus() == i){ // avoiding asynchronous stage processing
					clockcycle.get(index).get(j).perform();
				}
			}
		}
	}

	
	
	public static void store(String reg, int value){ // function used to store a 
		Initialization.registers.replace(reg,value);
	}


	public boolean checkWAW(Instruction inst1, Instruction inst2, int cc){ // checks for WAW dependence
		if(inst1.getInstructionType().equals("CMP") || inst2.getInstructionType().equals("CMP")) return false;
		if(inst1.getParam1().equals(inst2.getParam1()) && inst1.getStatus() != Instruction.END) {
			inst2.setHazard("WAW");
			hazardCounter[cc][0]++;
			return true;
		}
		return false;
	}
	public boolean checkRAW(Instruction inst1, Instruction inst2, int cc){ // checks for RAW dependence
		if(inst1.getInstructionType().equals("CMP")) return false;
		if(inst2.getInstructionType().equals("CMP")){
			if(inst2.getParam1().equals(inst1.getParam1()) && inst1.getStatus() != Instruction.END){
				hazardCounter[cc][1]++;
				return true;	
			}
		}
		if(inst1.getParam1().equals(inst2.getParam2()) && inst1.getStatus() != Instruction.END){
			inst2.setHazard("RAW");
			hazardCounter[cc][1]++;
			return true;
		}
		return false;
	}
	public boolean checkWAR(Instruction inst1, Instruction inst2, int cc){ // checks for WAR dependence
		if(inst2.getInstructionType().equals("CMP")) return false;
		if(inst1.getParam2().equals(inst2.getParam1()) && inst1.getStatus() != Instruction.END){
			inst2.setHazard("WAR");
			hazardCounter[cc][2]++;
			return true;
		}
		if(inst1.getInstructionType().equals("CMP")){
			if(inst1.getParam1().equals(inst2.getParam1()) && inst1.getStatus() != Instruction.END){
				inst2.setHazard("WAR");
				hazardCounter[cc][2]++;
				return true;
			}
		}
		return false;
	}
	public boolean checkDuplicateStage(Instruction inst1, Instruction inst2, int cc){ // checks for structural hazard 
		if(inst1.getStatus() == inst2.getStatus() + 1 && !inst1.getStall() && inst1.getStatus() != Instruction.END){
			inst2.setHazard("Structural Hazard");
			hazardCounter[cc][3]++;
			return true;
		}
		return false;
	}

/*=======getters=========*/
	public int getWAWCount(){
		return this.hazardCounter[this.currentCC][0];
	}

	public int getRAWCount(){
		return this.hazardCounter[this.currentCC][1];
	}

	public int getWARCount(){
		return this.hazardCounter[this.currentCC][2];
	}

	public int getStructHazard(){
		return this.hazardCounter[this.currentCC][3];
	}

   	public int getStalls(){
   		return this.stallCounter;
   	}
}