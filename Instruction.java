package instantiation;
import java.util.ArrayList;
import java.util.HashSet;
public class Instruction{
	public static final int START = 0;
	public static final int FETCH = 1;
	public static final int DECODE = 2;
	public static final int EXECUTE = 3;
	public static final int MEMORY = 4;
	public static final int WRITEBACK = 5;
	// public static final int END = 4;
	public static final int END = 6;

 	private static HashSet<String> supportedTypes = new HashSet<String>(4);
	private String instructionType;
	private String param1;
	private String param2;
	private int status;
	private boolean stalled;
	public Instruction(String inst){
		fillSupportedTypes();
		String newInst = inst.replaceAll("[,\\s\\n]+", " ");
		String [] parser = newInst.split(" ");
		if(parser.length != 3) { 
			System.out.println("Parsing error!");
			System.exit(0);
		}
		if(!Initialization.registers.containsKey(parser[1])){
			System.out.println("Register error!");
			System.exit(0);	
		}
		if(!supportedTypes.contains(parser[0])){
			System.out.println("Cannot find symbol: " + parser[0]);
			System.exit(0);	
		}
		this.status = START;
		this.stalled = false;
		this.instructionType = parser[0];
		this.param1 = parser[1];
		this.param2 = parser[2];
	}

	// public Instruction(Instruction copy){
	// 	this.instructionType = copy.instructionType;
	// 	this.param1 = copy.param1;
	// 	this.param2 = copy.param2;
	// 	this.status = copy.status;
	// 	this.stalled = copy.status;
	// }

	private void fillSupportedTypes(){
		supportedTypes.add("CMP");
		supportedTypes.add("LOAD");
		supportedTypes.add("SUB");
		supportedTypes.add("ADD");
	}

	public int getStatus(){
		return this.status;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public void printStatus(){
		if(this.status != END){
			System.out.print("\t" +instructionType + "  "  + param1 +  "  " +param2 + ": " );	
			if(this.stalled == true){
				System.out.println("STALL");
				return;
			}
			switch(this.status){
				case FETCH:
					System.out.println(" FETCH");
					break;
				case DECODE:
					System.out.println(" DECODE");
					break;
				case EXECUTE:
					System.out.println(" EXECUTE");
					break;
				case MEMORY:
					System.out.println(" MEMORY");
					break;
				case WRITEBACK:
					System.out.println(" WRITEBACK");
					break;
				default:
					System.out.println(" ERR");
			}
		}
	}

	public void setStall(boolean toStall){
		this.stalled = toStall;
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

	public void printInstruction(){
		System.out.println(instructionType + "  "  + param1 +  "  " +param2);	
	}

}