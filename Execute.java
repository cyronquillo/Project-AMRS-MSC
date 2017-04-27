package instantiation;

public class Execute{

	public Execute(){

	}

	public void execution(int operand1, int operand2, String operation, String destination){
		
		int first_operand;
		int second_operand;
		String my_operation;
		String my_destination;

		first_operand = operand1;
		second_operand = operand2;
		operation = operation;
		destination = destination;


		switch(operation){
			case "LOAD": System.out.println("Load "+operand2+" to "+destination);
			break;
			case "ADD": System.out.println(destination+" = "+operand1+" + "+operand2);
			break;
			case "SUB": System.out.println(destination+" = "+operand1+" -"+operand2);
			break;
			case "CMP": //compaer
			break;
		}
	}

}
