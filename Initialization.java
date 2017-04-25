package instantiation;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.HashMap;

public class Initialization{
	public static HashMap<String,Integer> registers = new HashMap<String,Integer>();
	public static LinkedList<Instruction> instructions = new LinkedList<Instruction>();
	public Initialization(String file){
		initialize(); // registers
		readFile(file); //file reading
	}

	public static void readFile(String file){
		try{
			BufferedReader input = new BufferedReader (new FileReader (file));
			String line;
			while((line= input.readLine())!= null){
				Instruction inst = new Instruction(line.toUpperCase());
				instructions.push(inst);
				inst.printInstruction();
			}
		}
		catch(Exception ex){}
	}
	public static void initialize(){
		registers.put("R1",0);
		registers.put("R2",0);
		registers.put("R3",0);
		registers.put("R4",0);
		registers.put("R5",0);
		registers.put("R6",0);
		registers.put("R7",0);
		registers.put("R8",0);
		registers.put("R9",0);
		registers.put("R10",0);
		registers.put("R11",0);
		registers.put("R12",0);
		registers.put("R13",0);
		registers.put("R14",0);
		registers.put("R15",0);
		registers.put("R16",0);
		registers.put("R17",0);
		registers.put("R18",0);
		registers.put("R19",0);
		registers.put("R20",0);
		registers.put("R21",0);
		registers.put("R22",0);
		registers.put("R23",0);
		registers.put("R24",0);
		registers.put("R25",0);
		registers.put("R26",0);
		registers.put("R27",0);
		registers.put("R28",0);
		registers.put("R29",0);
		registers.put("R30",0);
		registers.put("R31",0);
		registers.put("R32",0);
		registers.put("PC",0);
		registers.put("MAR",0);
		registers.put("MBR",0);
		registers.put("OF",0);
		registers.put("NF",0);
		registers.put("ZF",0);
	}

	public static void store(String reg, int value){
		registers.replace(reg,value);
	}

	public static void print(String reg){
		System.out.println(registers.get(reg));
	}

}