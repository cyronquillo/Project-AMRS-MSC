package instantiation;
import java.util.LinkedList;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.HashMap;
import java.io.File;


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
	public static boolean fileErr; // flag to terminate program when syntax errors are met
	public static ClockCycle clcy; 

	/*STAGES PROCESSORS*/
	public static Fetch fetch;
	public static Decode decode;
	public static Execute execute;
	public static Memory memory;
	public static Writeback writeback;

	/*ARRAYS FOR THE TABLE VALUES IN GUI*/
	public String[][] data;
	public String[][] dataReg1;
	public String[][] dataReg2;
	public String[][] dataInst;
	public String[][] dataFlags;
	public String[][] dataHazards;

	/*value for the Text Area in GUI*/
	public static String textAreaData = "";

	public Initialization(File file){
		readFile(file); //file reading
	}

	public static void readFile(File file){
		String line;
		initialize();
		try{
			fileErr = false;
			BufferedReader input = new BufferedReader (new FileReader (file));
			if((line = input.readLine()) == null){
				fileErr = true;
				return;
			}
			int counter = 0;
			do{	// reads every instruction in the given file
				Instruction inst = new Instruction(line.toUpperCase(), counter++);
				instructions.add(inst);
			}while((line= input.readLine())!= null);
		}
		catch(Exception ex){
			fileErr = true;
		}
		if(file.getName().equals(".")) clcy.buildClockCycles();
		else if (fileErr == true) readFile(new File(".")); // sets the value of file to '.' when error to clear tables
		else clcy.buildClockCycles();
	}
	
	public static void storeTextArea(File file){ // store the value in the Text Area to a file
		textAreaData = "";
		String line;
		try{
			BufferedReader input = new BufferedReader (new FileReader (file));
			if((line = input.readLine()) == null){
				return;
			}
			do{	
				textAreaData += line.toUpperCase() + "\n";
			}while((line= input.readLine())!= null);
		}
		catch(Exception ex){}
		readFile(file); // then reads the file to run the instructions in the text area
	}

	public static void initialize(){ // resets all values of necessary registers and variables
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
		fileErr = false;
		clcy = new ClockCycle();
		PC = 0;
		OF = false; // setting flags to default value
		ZF = false;
		NF = false;
		MAR = -1;
		CIR = "null";
		fetch = new Fetch();
		decode = new Decode();
		execute = new Execute();
		memory = new Memory();
		writeback = new Writeback();
		instructions = new ArrayList<Instruction>();
	}



	public void populateValues(){
		int i;
		dataReg1 = new String[16][2];
		dataReg2 = new String[16][2];
		
		for(i=0;i<16;i++){	//populates first register table
			dataReg1[i][0] = "R"+(i+1);
			dataReg1[i][1] = Integer.toString(registers.get("R"+(i+1)));
		}

		for(i=0;i<16;i++){	// populates second register table
			dataReg2[i][0] = "R"+(i+17);
			dataReg2[i][1] = Integer.toString(registers.get("R"+(i+17)));
		}

		dataInst = new String[instructions.size()][2];
		
		for(i=0;i<instructions.size();i++){ // populates instruction table
			dataInst[i][0] = Integer.toString(instructions.get(i).getAddress());
			dataInst[i][1] = instructions.get(i).getInstruction();
		}

		//populates flags table
		dataFlags = new String[3][2];
		dataFlags[0][0] = "Overflow Flag";
		dataFlags[1][0] = "Zero Flag";
		dataFlags[2][0] = "Negative Flag";
		if(OF) dataFlags[0][1] = "1";
		else dataFlags[0][1] = "0";
		if(ZF) dataFlags[1][1] = "1";
		else dataFlags[1][1] = "0";
		if(NF) dataFlags[2][1] = "1";
		else dataFlags[2][1] = "0";

		// populates hazards table
		dataHazards = new String[4][2];
		dataHazards[0][0] = "WAW";
		dataHazards[0][1] = "" + clcy.getWAWCount();
		dataHazards[1][0] = "RAW";
		dataHazards[1][1] = "" + clcy.getRAWCount() ;
		dataHazards[2][0] = "WAR";
		dataHazards[2][1] = "" + clcy.getWARCount();
		dataHazards[3][0] = "Structural";
		dataHazards[3][1] = "" + clcy.getStructHazard();
	}

	

	public void populateTable(){ // populates the values for the pipelining design
		int x = instructions.size();
		int y = clcy.clockcycle.size();
		data = new String[x][y+1];
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
	}

	/*==========getters==========*/
	public String getTextAreaData(){
		return this.textAreaData;
	}
}