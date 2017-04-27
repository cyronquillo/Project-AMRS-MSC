import instantiation.Initialization;
import instantiation.Decode;
import instantiation.Execute;

public class Main{

	public static void main(String[] args){
				
		Initialization start = new Initialization("input/input.txt");
		Decode decoder = new Decode();
		Execute executer = new Execute();
		int size = start.instructions.size();
		while(size!=0) {
			decoder.decode(start,--size);
			executer.execution(decoder.getOperand1(),decoder.getOperand2(),decoder.getOperation(),decoder.getDestination());
		
		}

	}
}