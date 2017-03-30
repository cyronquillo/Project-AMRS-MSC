import java.util.ArrayList;

public class Instruction{
 
	private String instructionType;
	private String param1;
	private String param2;

	public Instruction(String inst){
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
		this.instructionType = parser[0];
		this.param1 = parser[1];
		this.param2 = parser[2];
	}

	public void printInstruction(){
		System.out.println(instructionType + "  "  + param1 +  "  " +param2);	
	}

}