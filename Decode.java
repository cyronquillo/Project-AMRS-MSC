
package instantiation;
public class Decode{

	private String op1Var;
	private int op1Val;
	private int op2;

	public Decode(){}

	public void process(Instruction toDecode){
		this.op1Var = toDecode.getParam1();
		this.op1Val = getRegisterValue(toDecode.getParam1());

		if(isRegister(toDecode.getParam2())) this.op2 = getRegisterValue(toDecode.getParam2());
		else this.op2 = Integer.parseInt(toDecode.getParam2());

		Initialization.CIR = toDecode.getInstructionType();
		printResult();
	}

	public boolean isRegister(String op){
		return Initialization.registers.containsKey(op);
	}

	public int getRegisterValue(String op){
		return Initialization.registers.get(op);
	}

	public void printResult(){
		System.out.println("Operation: " + Initialization.CIR);
		System.out.println("Operand 1: " + op1Val);
		System.out.println("Operand 2: " + op2);
	}

}