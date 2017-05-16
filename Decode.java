
package instantiation;
public class Decode{

	private String op1Var;		
	//String that holds the register name of the first operand
	private int op1Val;					
	//Int variable that holds the value of the register of the first operand
	private int op2;	
	//Int variable that holds the value of the register of the second operand																				

	public Decode(){}
	
	//Method used for decoding previous fetched instruction
	//Parameter: instruction
	public void process(Instruction toDecode){
		this.op1Var = toDecode.getParam1();
		//assigns First Register Name to class variable
		this.op1Val = getRegisterValue(toDecode.getParam1());
		//assigns First Register Value to class variable

		if(isRegister(toDecode.getParam2())) this.op2 = getRegisterValue(toDecode.getParam2());
		//if second operand is a register
		//get the value of that register and assign that value
		else this.op2 = Integer.parseInt(toDecode.getParam2());	
		//if second operand is an immediate value
		//parse the string, and immediately assgin the value

		Initialization.CIR = toDecode.getInstructionType();
		//assign the instruction type to the CIR
	}
	//returns true if operand is a register
	//parameter: String operand
	public boolean isRegister(String op){
		return Initialization.registers.containsKey(op);
	}
	//returns the value of the register
	//parameter: String operand
	public int getRegisterValue(String op){
		return Initialization.registers.get(op);
	}

	public void printResult(){
		System.out.println("Operation: " + Initialization.CIR);
		System.out.println("Operand 1: " + op1Val);
		System.out.println("Operand 2: " + op2);
	}

	public int getOp1Val(){
		return this.op1Val;
	}

	public int getOp2Val(){
		return this.op2;
	}

	public String getDest(){
		return this.op1Var;
	}


}
