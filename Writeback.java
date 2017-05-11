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
		value = Initialization.memory.getValue();
		setZF = Initialization.memory.getZF();
		setOF = Initialization.memory.getOF();
		setNF = Initialization.memory.getNF();
		destination = Initialization.memory.getDestination();
		
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