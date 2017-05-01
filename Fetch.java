package instantiation;
public class Fetch{




	public Fetch(){}


	public void process(Instruction toFetch){
		Initialization.MAR = Initialization.PC;
		Initialization.MBR = Initialization.instructions.get(toFetch.getAddress());
		Initialization.PC++;
	}



}