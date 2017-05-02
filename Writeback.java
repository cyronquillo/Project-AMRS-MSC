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
		
		// System.out.println(value);

		if(value==100){
			changeFlags();
		}
		else{
			changeAll();
		}
	}

	private void changeFlags(){
		Initialization.ZF = setZF;
		Initialization.OF = setOF;
		Initialization.NF = setNF;
	}

	private void changeAll(){
		changeFlags();

		Initialization.registers.replace(destination, value);

	}

}