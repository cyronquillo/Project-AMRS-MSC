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
			System.out.println("In instruction: " + inst); 
			System.out.println("\tParsing error!");
			System.exit(0);
		}
		if(!Initialization.registers.containsKey(parser[1])){
			System.out.println("In instruction: " + inst); 
			System.out.println("\tFirst operand must contain a correct register: found " + parser[1]);
			System.exit(0);	
		}
		if(!supportedTypes.contains(parser[0])){
			System.out.println("In instruction: " + inst); 
			System.out.println("\tCannot find symbol: " + parser[0]);
			System.exit(0);	
		}
		if(!isNumeric(parser[2]) && !Initialization.registers.containsKey(parser[2])){
			System.out.println("In instruction: " + inst); 
			System.out.println("\tCannot find symbol: " + parser[2]);
			System.exit(0);	
		}
		// if(isNumeric(parser[2])){
		// 	int num = Integer.parseInt(parser[2]);
		// 	if(num > 99 || num < -99){
		// 		System.out.println("Immediate value out of bounds: " + parser[2]);
		// 		System.exit(0);	
		// 	}
		// }
		this.status = START;
		this.stalled = false;
		this.instructionType = parser[0];
		this.param1 = parser[1];
		this.param2 = parser[2];
	}

	public Instruction(Instruction copy){
		this.instructionType = copy.instructionType;
		this.param1 = copy.param1;
		this.param2 = copy.param2;
		this.status = copy.status;
		this.stalled = copy.stalled;
	}

	public static boolean isNumeric(String str){
		return str.matches("-?\\d+");  //match a number with optional '-' and decimal.
	}
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