package instantiation;

public class Execute{

	private int value;
	private boolean setZF;
	private boolean setOF;
	private boolean setNF;
	

	public Execute(){
		this.setZF = false;
		this.setOF = false;
		this.setNF = false;
		this.value = 100;

	}

	public void process(){
		// use cir
		String operand = Initialization.CIR;
		int op1 = Initialization.decode.getOp1Val();
		int op2 = Initialization.decode.getOp2Val();
		switch(operand){
			case "ADD": this.value = performAdd(op1, op2);
						this.setOF = isOverflow(this.value);
						break;
			case "SUB": this.value = performSub(op1,op2);
						this.setOF = isOverflow(this.value);
						break;
			case "LOAD": this.value = performLoad(op2);
						this.setOF = isOverflow(this.value);
						break;
			case "CMP": this.value = performSub(op1,op2);
						this.setZF = isZero(this.value);
						this.setNF = isNegative(this.value);
						break;
		}
	}

	public int performAdd(int op1, int op2){
		return op1 + op2;
	}

	public int performSub(int op1, int op2){
		return op1 - op2;
	}

	public int performLoad(int op2){
		return op2;
	}

	public boolean isZero(int value){
		return (value == 0 ? true:false);
	}


	public boolean isOverflow(int value){
		if(value > 99){
			this.value = 99;
			return true;
		} else if(value < -99){
			this.value = -99;
			return true;
		}
		return false;
	}

	public boolean isNegative(int value){
		return (value < 0 ? true:false);
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
}


