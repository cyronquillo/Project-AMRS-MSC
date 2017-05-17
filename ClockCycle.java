package instantiation;
import java.util.ArrayList;
import java.util.Scanner;
public class ClockCycle{

	public ArrayList<ArrayList<Instruction>> clockcycle;
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
		this.hazardCounter = new Integer[maxRow][4];
		for(int i = 0; i < maxRow; i++){
			for(int j = 0; j < 4; j++){
				this.hazardCounter[i][j] = 0; // reset values to 0
			}
		}
		do{
			clockcycle.add(new ArrayList<Instruction>());
			stalled = 0;
			if(clock!= 0){
				for(int i = 0; i < clockcycle.get(clock-1).size(); i++){
					if(clockcycle.get(clock-1).get(i).getStatus() < Instruction.END){
						clockcycle.get(clock).add(new Instruction(clockcycle.get(clock-1).get(i)));
					}
				}
				
			}
			if(clock < Initialization.instructions.size()){
				clockcycle.get(clock).add(new Instruction(Initialization.instructions.get(clock)));
			}

			for(int i = 0; i <  clockcycle.get(clock).size(); i++){
				clockcycle.get(clock).get(i).setStall(false);
				clockcycle.get(clock).get(i).setHazard("");
				for(int j = i-1; j >= 0; j--){
					if(clockcycle.get(clock).get(i).getStatus() == Instruction.START) break;
					if( checkRAW(clockcycle.get(clock).get(j), clockcycle.get(clock).get(i),tempCC) || 
							checkWAR(clockcycle.get(clock).get(j), clockcycle.get(clock).get(i),tempCC) || 
								checkWAW(clockcycle.get(clock).get(j), clockcycle.get(clock).get(i),tempCC) || 
								checkDuplicateStage(clockcycle.get(clock).get(j), clockcycle.get(clock).get(i),tempCC)) {
						clockcycle.get(clock).get(i).setStall(true);
						stalled = 1;
						break;
					}

				}
				if(!clockcycle.get(clock).get(i).getStall()){
					clockcycle.get(clock).get(i).setStatus(
						clockcycle.get(clock).get(i).getStatus() + 1 
					);

				}				
			}
			stallCounter += stalled;
			clock++;
			tempCC++;
		} while(!allInstructionsDone(clock));
	}

	public boolean allInstructionsDone(int clock){
		int size = clockcycle.get(clock-1).size();
		for(int i = 0; i<size; i++){
			if(clockcycle.get(clock-1).get(i).getStatus() != Instruction.END) return false;
		}
		return true;
		// clockcycle.get(clock-1).get(clockcycle.get(clock-1).size()-1).getStatus() != Instruction.END
	}
	public void outputClockCycleSummary(int cc){
		int index = cc-1;
		if(cc == 0){
			System.out.println ("Clock Cycle 0: START");
			return;
		}
		System.out.println("Clock Cycle " + (cc) + ": ");
		for(int j = 0; j < clockcycle.get(index).size(); j++){
			clockcycle.get(index).get(j).printStatus();
		}
	}

	public void performInstructions(int cc){
		int index = cc-1;
		for(int i = 5; i > 0; i--){
			for(int j = 0; j < clockcycle.get(index).size(); j++){
				if(clockcycle.get(index).get(j).getStatus() == i){ // avoiding asynchronous stage processing
					clockcycle.get(index).get(j).perform();
				}
			}
		}
	}

	public void hazardsEncountered(int cc){
		if(cc == 0) return;
		int index = cc-1;
		boolean noHazard = true;
		for(int j = 0; j < clockcycle.get(index).size(); j++){
				if(clockcycle.get(index).get(j).getStall()){
					if(noHazard) System.out.println("Hazards: ");
					System.out.println("\t"+clockcycle.get(index).get(j).getHazard());
					noHazard = false;
				}
		}
		if(noHazard) System.out.println("No Hazards for this Clock Cycle");
	}
	public void printRegisters(){
		System.out.println("PC: " + Initialization.PC);
		System.out.println("MAR: " + Initialization.MAR);
		System.out.print("MBR: ");

		if(Initialization.MBR == null) System.out.println("null");
		else Initialization.MBR.printInstruction();
		System.out.println("OF: " + Initialization.OF);
		System.out.println("ZF: " + Initialization.ZF);
		System.out.println("NF: " + Initialization.NF);

		for(int i = 1; i < 33; i++){
			System.out.println("R"+i+": " + Initialization.registers.get("R" + i));
		}
	}
	
	
	public static void store(String reg, int value){
		Initialization.registers.replace(reg,value);
	}

	public static void print(String reg){
		System.out.println(Initialization.registers.get(reg));
	}

	public boolean checkWAW(Instruction inst1, Instruction inst2, int cc){
		if(inst1.getInstructionType().equals("CMP") || inst2.getInstructionType().equals("CMP")) return false;
		if(inst1.getParam1().equals(inst2.getParam1()) && inst1.getStatus() != Instruction.END) {
			inst2.setHazard("WAW");
			hazardCounter[cc][0]++;
			return true;
		}
		return false;
	}
	public boolean checkRAW(Instruction inst1, Instruction inst2, int cc){
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
	public boolean checkWAR(Instruction inst1, Instruction inst2, int cc){ // write (inst2) after read(inst1)
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
	public boolean checkDuplicateStage(Instruction inst1, Instruction inst2, int cc){
		if(inst1.getStatus() == inst2.getStatus() + 1 && !inst1.getStall() && inst1.getStatus() != Instruction.END){
			inst2.setHazard("Structural Hazard");
			hazardCounter[cc][3]++;
			return true;
		}
		return false;
	}


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

	public static void clearScreen() {  
    	System.out.print("\033[H\033[2J");  
    	System.out.flush();  
   	}  

   	public int getStalls(){
   		return this.stallCounter;
   	}
}