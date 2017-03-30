import java.util.ArrayList;
public class Instruction{

	private String instructionType;
	private String param1;
	private String param2;

	public Instruction(String inst){
		String newInst = inst.replaceAll("[,\\s]+", " ");
		String [] parser = newInst.split(" ");
		if(parser.length != 3) { return;}
		System.out.println(parser.length);
		this.instructionType = parser[0];
		this.param1 = parser[1];
		this.param2 = parser[2];
		// System.out.println(instructionType + "  "  + param1 +  "  " +param2);
	}



}