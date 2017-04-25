import instantiation.Initialization;
import instantiation.Decode;
public class Main{

	public static void main(String[] args){
				
		Initialization start = new Initialization("input/input.txt");
		Decode decoder = new Decode();

		int size = start.instructions.size();
		while(size!=0) {
			decoder.decode(start,--size);
			decoder.printDecoded();
		}

	}
}