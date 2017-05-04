package instantiation;
import java.util.LinkedList;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.HashMap;


public class Initialization{
	public static Integer PC;
		/*since PC holds the address of the instruction to be executed, 
		PC will be holding the index of the instruction based on the 
		instructions arraylist*/
	public static Integer MAR;
		/*MAR will be holding the address of the current instruction
		being executed, so MAR will be holding an index of the 
		instruction based on the instructions arraylist*/
	public static Instruction MBR;
		/*MBR will be holding the current instruction being executed
		so MAR will serve as a pointer of the instruction being executed*/
	public static boolean OF;
		/*OF is the Overflow Flag that changes its value during Execution
		Stage. 
		0 = false
		1 = true
		*/
	public static boolean ZF;
		/* ZF is the zeroflag that changes in the Execution stage of CMP
		Instruction. 
		0 = false = when the 2 values being compared are not equal
		1 = true = when 2 values being compared are equal
		*/
	public static boolean NF;
		/* NF is the negative flag that changes in the Execution stage of 
		CMP Instruction. 
		0 = false = param1 > param2
		1 == true = param1 < param2
		*/
	public static String CIR;
		/* CIR, which is the current instruction register, contains the 
		information specifying the instruction being performed. 
		*/


	public static HashMap<String,Integer> registers = new HashMap<String,Integer>();
	public static ArrayList<Instruction> instructions = new ArrayList<Instruction>();
	// public static ArrayList<ArrayList<Instruction>> clcy.clockcycle= new ArrayList<ArrayList<Instruction>>();
	public static boolean err = false; // flag to terminate program when syntax errors are met
	public static ClockCycle clcy = new ClockCycle();
	/*STAGES*/
	public static Fetch fetch = new Fetch();
	public static Decode decode = new Decode();
	public static Execute execute = new Execute();
	public static Memory memory = new Memory();
	public static Writeback writeback = new Writeback();
	/*END OF STAGES*/	

	public String[][] data;
	public Initialization(String file){
		
		PC = 0;
		OF = false; // setting flags to default value
		ZF = false;
		NF = false;


		initialize(); // registers
		readFile(file); //file reading

		clcy.buildClockCycles();
		// clcy.showClockCycle();
	}

	public static void readFile(String file){
		try{
			BufferedReader input = new BufferedReader (new FileReader (file));
			String line;
			int counter = 0;
			while((line= input.readLine())!= null){
				Instruction inst = new Instruction(line.toUpperCase(), counter++);
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
	}



	

	
	

	public void populateTable(){
		int x = instructions.size();
		int y = clcy.clockcycle.size();
		data = new String[x][y+1];
		System.out.println(clcy.clockcycle.size());
		for(int i = 0; i < x; i++){
			for(int j = 0; j < y; j++){
				data[i][j] = " ";
			}
		}
		for(int i = 0; i < x; i++){
			data[i][0] = instructions.get(i).getInstruction();
		}
		for(int i = 0; i < y-1; i++){
			for(int j = 0; j < clcy.clockcycle.get(i).size(); j++){
				data[clcy.clockcycle.get(i).get(j).getAddress()][i+1] = clcy.clockcycle.get(i).get(j).getStage();
			}
		}

		for(int i = 0; i < x; i++){
			for(int j = 0; j < y; j++){
				System.out.print(data[i][j] +" ");
			}
			System.out.println();
		}
	}
}