package instantiation;
public class Fetch{




	public Fetch(){}


	public void process(Instruction toFetch){
		Initialization.MAR = Initialization.PC; // changes the value of MAR to the current value of PC
		Initialization.MBR = Initialization.instructions.get(toFetch.getAddress()); // gets  the value of MBR corresponding to MAR
		Initialization.PC++; // increments the value of PC
	}



}