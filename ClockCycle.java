package instantiation;
import java.util.ArrayList;
import java.util.Scanner;
public class ClockCycle{

	public ArrayList<ArrayList<Instruction>> clockcycle= new ArrayList<ArrayList<Instruction>>();
	private Integer stallCounter;

	public ClockCycle(){
		stallCounter = 0;
	}


	public void buildClockCycles(){
		int clock = 0;
		int limit;
		int stalled;
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
				for(int j = 0; j < i; j++){
					if(clockcycle.get(clock).get(i).getStatus() == Instruction.START) break;
					if( checkRAW(clockcycle.get(clock).get(j), clockcycle.get(clock).get(i)) || 
							checkWAR(clockcycle.get(clock).get(j), clockcycle.get(clock).get(i)) || 
								checkWAW(clockcycle.get(clock).get(j), clockcycle.get(clock).get(i)) || 
								checkDuplicateStage(clockcycle.get(clock).get(j), clockcycle.get(clock).get(i))) {
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
	public void showClockCycle(){
		Scanner reader = new Scanner(System.in);
		int choice = 0;
		int cc = 0;
		int prevCC = -1;
		do{

			if(cc + 1 <= clockcycle.size()){
				outputClockCycleSummary(cc);
				if(cc != 0 && cc != prevCC){
					performInstructions(cc);
				}
			} 
			do{
				prevCC = cc;
				if(cc + 1 > clockcycle.size()){
					System.out.println("Done!");
					System.out.println("Total Stalls:" + stallCounter);
					System.out.println("Total Clock Cycles: " +(cc-1));
				}
				if(cc + 1 <= clockcycle.size()) System.out.println("[1] Next Clock Cycle");
				System.out.println("[2] Display Registers");
				System.out.println("[3] Display Hazards");
				System.out.println("[0] Exit");
				System.out.print("Choice: ");
				choice = reader.nextInt();
			}while(choice < 0 || choice > 3);

			switch(choice){
				case 1: 
						cc= cc+ 1;
						clearScreen();
						break;
				case 0: 
						System.out.println("Exiting...");
						break;
				case 2:
						clearScreen();
						printRegisters();
						break;
				case 3:
						clearScreen();
						hazardsEncountered(cc);
						break;
				default:
						System.out.println("Invalid Input");
			}

		} while(choice != 0);
	}
	
	public static void store(String reg, int value){
		Initialization.registers.replace(reg,value);
	}

	public static void print(String reg){
		System.out.println(Initialization.registers.get(reg));
	}

	public boolean checkWAW(Instruction inst1, Instruction inst2){
		if(inst1.getParam1().equals(inst2.getParam1()) && inst1.getStatus() != Instruction.END) {
			inst2.setHazard("WAW");
			return true;
		}
		return false;
	}
	public boolean checkWAR(Instruction inst1, Instruction inst2){
		if(inst1.getParam2().equals(inst2.getParam1()) && inst1.getStatus() != Instruction.END){
			inst2.setHazard("WAR");
			return true;
		}
		
		return false;
	}
	public boolean checkRAW(Instruction inst1, Instruction inst2){
		if(inst1.getParam1().equals(inst2.getParam2()) && inst1.getStatus() != Instruction.END){
			inst2.setHazard("RAW");
			return true;
		}
		return false;
	}
	public boolean checkDuplicateStage(Instruction inst1, Instruction inst2){
		if(inst1.getStatus() == inst2.getStatus() + 1 && !inst1.getStall() && inst1.getStatus() != Instruction.END){
			inst2.setHazard("Stage on Use");
			return true;
		}
		return false;
	}

	public static void clearScreen() {  
    	System.out.print("\033[H\033[2J");  
    	System.out.flush();  
   	}  

   	public int getStalls(){
   		return this.stallCounter;
   	}
}