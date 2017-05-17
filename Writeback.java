package instantiation;

public class Writeback{

	private int value;
	private boolean setZF;
	private boolean setOF;
	private boolean setNF;
	private String destination;

	public Writeback(){
	}

	public void process(){
		setZF = Initialization.memory.getZF();								//get value of zero flag from memory
		setOF = Initialization.memory.getOF();								//get value of overflow flag from memory
		setNF = Initialization.memory.getNF();								//get value of negative flag from memory
		value = Initialization.memory.getValue();							//get value of executed instruction from memory
		destination = Initialization.memory.getDestination();				//get register destination from memory
		
		if(value==100){				//value==100 means that cmp is used
			changeFlags();			//so write to flag only
		}
		else{
			changeAll();			//else write also to right registers
		}
	}

	private void changeFlags(){			//changes value of flags 
		Initialization.ZF = setZF;
		Initialization.OF = setOF;
		Initialization.NF = setNF;
	}

	private void changeAll(){
		changeFlags();					

		Initialization.registers.replace(destination, value);		//replaces the value of register with the correct executed value

	}

}