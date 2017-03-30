import java.util.ArrayList;
import java.util.HashSet;

public class Instruction{
 	private static HashSet<String> supportedTypes = new HashSet<String>(4);
	private String instructionType;
	private String param1;
	private String param2;

	public Instruction(String inst){
		fillSupportedTypes();
		String newInst = inst.replaceAll("[,\\s\\n]+", " ");
		String [] parser = newInst.split(" ");
		if(parser.length != 3) { 
			System.out.println("Parsing error!");
			System.exit(0);
		}
		if(!Main.registers.containsKey(parser[1])){
			System.out.println("Register error!");
			System.exit(0);	
		}
		if(!supportedTypes.contains(parser[0])){
			System.out.println("Cannot find symbol: " + parser[0]);
			System.exit(0);	
		}
		this.instructionType = parser[0];
		this.param1 = parser[1];
		this.param2 = parser[2];
	}

	private void fillSupportedTypes(){
		supportedTypes.add("CMP");
		supportedTypes.add("LOAD");
		supportedTypes.add("SUB");
		supportedTypes.add("ADD");
	}

	public void printInstruction(){
		System.out.println(instructionType + "  "  + param1 +  "  " +param2);	
	}

}