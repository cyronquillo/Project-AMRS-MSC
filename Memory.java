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

		setZF = Initialization.execute.getZF();
		setOF = Initialization.execute.getOF();
		setNF = Initialization.execute.getNF();
		value = Initialization.execute.getValue();
		destination = Initialization.execute.getDestination();		

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