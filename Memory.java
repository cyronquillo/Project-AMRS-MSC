package instantiation;

public class Memory {

	private int value;
	private boolean setZF;
	private boolean setOF;
	private boolean setNF;
	private String destination;

	public Memory(){
		this.setZF = false;
		this.setOF = false;
		this.setNF = false;
		this.value = 100;
	}

	public void process(){

		setZF = Initialization.execute.getZF();						//get value of zero flag from execute
		setOF = Initialization.execute.getOF();						//get value of overflow flag from execute
		setNF = Initialization.execute.getNF();						//get value of negative flag from execute
		value = Initialization.execute.getValue();					//get value of executed instruction
		destination = Initialization.execute.getDestination();		//get rgister destination

	}

	public int getValue(){
		return this.value;
	}	

	public boolean getZF(){
		return this.setZF;
	}

	public boolean getOF(){
		return this.setOF;
	}

	public boolean getNF(){
		return this.setNF;
	}

	public String getDestination(){
		return this.destination;
	}
}