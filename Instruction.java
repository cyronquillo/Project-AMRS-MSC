package instantiation;
import java.util.ArrayList;
import java.util.HashSet;
public class Instruction{
	/*CONSTANTS FOR EASE OF READABILITY*/
	public static final int START = 0;
	public static final int FETCH = 1;
	public static final int DECODE = 2;
	public static final int EXECUTE = 3;
	public static final int MEMORY = 4;
	public static final int WRITEBACK = 5;
	public static final int END = 6;

 	private static HashSet<String> supportedTypes = new HashSet<String>(4); // contains the different operations
	
	private String instructionType;
	private String param1;
	private String param2;
	private String hazard;
	private int status;
	private int address;
	private boolean stalled;

	public Instruction(String inst, int address){
		fillSupportedTypes(); 
		this.address = address;
		String newInst = inst.replaceAll("[,\\s\\n]+", " ");
		String [] parser = newInst.split(" ");
		/*SYNTAX ANALYSIS STARTS HERE*/
		// when the instruction given lacks parameters or an operation
		if(parser.length != 3) { 
			System.out.println("In instruction: " + inst); 
			System.out.println("\tParsing error!");
			Initialization.fileErr = true;
			return ;
		}
		// when the instruction does not exist in the supported types
		if(!supportedTypes.contains(parser[0])){
			System.out.println("In instruction: " + inst); 
			System.out.println("\tCannot find symbol: " + parser[0]);
			Initialization.fileErr = true;
			return ;
		}
		// when the first operand does not contain a register
		if(!Initialization.registers.containsKey(parser[1])){ 
			System.out.println("In instruction: " + inst); 
			System.out.println("\tFirst operand must contain a correct register: found " + parser[1]);
			Initialization.fileErr = true;
			return ;
		}
		//when the operation is a LOAD but the second operand is not an immediate value
		if(parser[0].equals("LOAD") && Initialization.registers.containsKey(parser[2])){
			System.out.println("In instruction: " + inst); 
			System.out.println("\tSecond operand must be an immediate value: found " + parser[2]);
			Initialization.fileErr = true;
			return ;
		}
		// when the operation is not a LOAD but the second operand is not a valid register
		if(!Initialization.registers.containsKey(parser[2]) && !parser[0].equals("LOAD")){
			System.out.println("In instruction: " + inst); 
			System.out.println("\tSecond operand must contain a correct register: found " + parser[2]);
			Initialization.fileErr = true;
			return ;
		}
		// when the second operand is neither 
		if(!isNumeric(parser[2]) && !Initialization.registers.containsKey(parser[2])){
			System.out.println("In instruction: " + inst); 
			System.out.println("\tCannot find symbol: " + parser[2]);
			Initialization.fileErr = true;
			return ;
		}

		this.status = START;
		this.stalled = false;
		this.instructionType = parser[0];
		this.param1 = parser[1];
		this.param2 = parser[2];
	}

	// constructor for replicating an instruction's attributes
	public Instruction(Instruction copy){
		this.instructionType = copy.instructionType;
		this.param1 = copy.param1;
		this.param2 = copy.param2;
		this.status = copy.status;
		this.stalled = copy.stalled;
		this.address = copy.address;
	}

	//function to check if the given string is numeric or not
	public static boolean isNumeric(String str){
		return str.matches("-?\\d+");  //match a number with optional '-' and decimal.
	}

	// currently supported operations
	private void fillSupportedTypes(){
		supportedTypes.add("CMP");
		supportedTypes.add("LOAD");
		supportedTypes.add("SUB");
		supportedTypes.add("ADD");
	}

	// instruction is performed based on its current stage and whether it is stalled or not
	public void perform(){
		// use all the details of the instruction
		if(this.stalled == true) return;
		switch(this.status){
			case FETCH:
				Initialization.fetch.process(this);
				break;
			case DECODE:
				Initialization.decode.process(this);
				break;
			case EXECUTE:
				Initialization.execute.process();
				break;
			case MEMORY:
				Initialization.memory.process();
				break;
			case WRITEBACK:
				Initialization.writeback.process();
				break;
			default:
				System.out.println(" ERR");

		}
	}
	/*===========setters=============*/
	public void setStatus(int status){
		this.status = status;
	}
	public void setHazard(String hazard){
		this.hazard = hazard;
	}
	public void setStall(boolean toStall){
		this.stalled = toStall;
	}
	/*============getters=============*/
	public int getStatus(){
		return this.status;
	}
	public String getHazard(){
		return this.hazard;
	}
	public String getParam1(){
		return this.param1; 
	}

	public String getParam2(){
		return this.param2; 
	}

	public String getInstructionType(){
		return this.instructionType; 
	}

	public boolean getStall(){
		return this.stalled;
	}
	public int getAddress(){
		return this.address;
	}

	// used for the table
	public String getStage(){
		if(this.stalled == true) return "S";
		switch(this.status){
			case FETCH:
				return "F";
			case DECODE:
				return "D";
			case EXECUTE:
				return "E";
				
			case MEMORY:
				return "M";
				
			case WRITEBACK:
				return "W";
			default:
				return "";

		}
	}
	public String getInstruction(){
		return instructionType + " " + param1 +", " + param2;
	}
}