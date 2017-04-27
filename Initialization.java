package instantiation;
import java.util.LinkedList;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class Initialization{
	public static HashMap<String,Integer> registers = new HashMap<String,Integer>();
	public static ArrayList<Instruction> instructions = new ArrayList<Instruction>();
	public static ArrayList<ArrayList<Instruction>> clockcycle= new ArrayList<ArrayList<Instruction>>();
	public static boolean err = false;
	public Initialization(String file){
		initialize(); // registers
		readFile(file); //file reading

		buildClockCycles();
		showClockCycle();
	}

	public static void readFile(String file){
		try{
			BufferedReader input = new BufferedReader (new FileReader (file));
			String line;
			while((line= input.readLine())!= null){
				Instruction inst = new Instruction(line.toUpperCase());
				instructions.add(inst);
			}
		}
		catch(Exception ex){}
		if(err == true) System.exit(0);
	}

	public static void initialize(){
		registers.put("R1",0);
		registers.put("R2",0);
		registers.put("R3",0);
		registers.put("R4",0);
		registers.put("R5",0);
		registers.put("R6",0);
		registers.put("R7",0);
		registers.put("R8",0);
		registers.put("R9",0);
		registers.put("R10",0);
		registers.put("R11",0);
		registers.put("R12",0);
		registers.put("R13",0);
		registers.put("R14",0);
		registers.put("R15",0);
		registers.put("R16",0);
		registers.put("R17",0);
		registers.put("R18",0);
		registers.put("R19",0);
		registers.put("R20",0);
		registers.put("R21",0);
		registers.put("R22",0);
		registers.put("R23",0);
		registers.put("R24",0);
		registers.put("R25",0);
		registers.put("R26",0);
		registers.put("R27",0);
		registers.put("R28",0);
		registers.put("R29",0);
		registers.put("R30",0);
		registers.put("R31",0);
		registers.put("R32",0);
		registers.put("PC",0);
		registers.put("MAR",0);
		registers.put("MBR",0);
		registers.put("OF",0);
		registers.put("NF",0);
		registers.put("ZF",0);
	}

	public void buildClockCycles(){
		int clock = 0;
		int limit;

		do{
			clockcycle.add(new ArrayList<Instruction>());
			if(clock!= 0){
				for(int i = 0; i < clockcycle.get(clock-1).size(); i++){
					if(clockcycle.get(clock-1).get(i).getStatus() < Instruction.END){
						clockcycle.get(clock).add(new Instruction(clockcycle.get(clock-1).get(i)));
					}
				}
				
			}
			if(clock < instructions.size()){
				clockcycle.get(clock).add(new Instruction(instructions.get(clock)));
			}

			for(int i = 0; i <  clockcycle.get(clock).size(); i++){
				clockcycle.get(clock).get(i).setStall(false);
				for(int j = 0; j < i; j++){
					if(clockcycle.get(clock).get(i).getStatus() == Instruction.START) break;
					if( checkRAW(clockcycle.get(clock).get(j), clockcycle.get(clock).get(i)) || 
							checkWAR(clockcycle.get(clock).get(j), clockcycle.get(clock).get(i)) || 
								checkWAW(clockcycle.get(clock).get(j), clockcycle.get(clock).get(i)) || 
								checkDuplicateStage(clockcycle.get(clock).get(j), clockcycle.get(clock).get(i))) {
						clockcycle.get(clock).get(i).setStall(true);
						break;
					}

				}
				if(!clockcycle.get(clock).get(i).getStall()){
					clockcycle.get(clock).get(i).setStatus(
						clockcycle.get(clock).get(i).getStatus() + 1 
					);

				}				
			}
			// if(clockcycle.get(clock).get(clockcycle.get(clock).size()-1).getStatus() != Instruction.END) System.out.println("Clock Cycle " + clock + ": ");
			// for(int i = 0; i < clockcycle.get(clock).size(); i++){
			// 	clockcycle.get(clock).get(i).printStatus();
			// }

			clock++;
		} while(clockcycle.get(clock-1).get(clockcycle.get(clock-1).size()-1).getStatus() != Instruction.END);
		clockcycle.remove(clockcycle.size()-1); // extra clock cycle with no instruction content
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
		for(int j = 0; j < clockcycle.get(index).size(); j++){
			clockcycle.get(index).get(j).perform();
		}
	}
	public void showClockCycle(){
		Scanner reader = new Scanner(System.in);
		int choice = 0;
		int cc = 0;
		do{

			if(cc + 1 <= clockcycle.size()){
				outputClockCycleSummary(cc);
				if(cc != 0){
					performInstructions(cc);
				}
			} 
			do{
				if(cc + 1 <= clockcycle.size()) System.out.println("[1] Next Clock Cycle");
				if(cc + 1 > clockcycle.size()) System.out.println("Done!");
				System.out.println("[0] Exit");
				System.out.print("Choice: ");
				choice = reader.nextInt();
			}while(choice < 0 || choice > 1);

			switch(choice){
				case 1: 
						cc= cc+ 1;
						clearScreen();
						break;
				case 0: 
						System.out.println("Exiting...");
						break;
				default:
						System.out.println("Invalid Input");
			}

		} while(choice != 0);
	}
	public static void store(String reg, int value){
		registers.replace(reg,value);
	}

	public static void print(String reg){
		System.out.println(registers.get(reg));
	}

	public boolean checkWAW(Instruction inst1, Instruction inst2){
		if(inst1.getParam1().equals(inst2.getParam1()) && inst1.getStatus() != Instruction.END) {
			return true;
		}
		return false;
	}
	public boolean checkWAR(Instruction inst1, Instruction inst2){
		if(inst1.getParam2().equals(inst2.getParam1()) && inst1.getStatus() != Instruction.END){
			return true;
		}
		
		return false;
	}
	public boolean checkRAW(Instruction inst1, Instruction inst2){
		if(inst1.getParam1().equals(inst2.getParam2()) && inst1.getStatus() != Instruction.END){
			return true;
		}
		return false;
	}
	public boolean checkDuplicateStage(Instruction inst1, Instruction inst2){
		if(inst1.getStatus() == inst2.getStatus() + 1 && !inst1.getStall() && inst1.getStatus() != Instruction.END){
			return true;
		}
		return false;
	}

	public static void clearScreen() {  
    	System.out.print("\033[H\033[2J");  
    	System.out.flush();  
   	}  
	
}