package instantiation;

public class Decode{
	private String operation;
	private String dest;
	private int operand1;
	private int operand2;

	public Decode(){

	}

	public void decode(Initialization start, int index){
		String op1 = start.instructions.get(index).getParam1();
		String op2 = start.instructions.get(index).getParam2();

		this.operation = start.instructions.get(index).getInstType();
		this.dest = op1;

		if(!this.operation.equals("LOAD")) {
			this.operand1 =  getRegisterValue(start,op1);
		}

		if(isRegister(start,op2)) {
			this.operand2 =  getRegisterValue(start,op2);
		}
		else {
			this.operand2 = Integer.parseInt(op2);
		}
	}

	public boolean isRegister(Initialization start, String op){
		return start.registers.containsKey(op);
	}

	public int getRegisterValue(Initialization start, String op){
		return start.registers.get(op);
	}

	public void printDecoded(){
		System.out.println("\nOperation: " + this.operation);
		System.out.println("Destination: " + this.dest);
		System.out.println("Operand 1: " + this.operand1);
		System.out.println("Operand 2: " + this.operand2);

	}

	public int getOperand1(){
		return this.operand1;
	}

	public int getOperand2(){
		return this.operand2;
	}

	public String getOperation(){
		return this.operation;
	}

	public String getDestination(){
		return this.dest;
	}
}