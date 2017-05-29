package assem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class assemble {

	private static final int ARGS_BIT_COUNT = 5;

	private static final Map<String, String> ops;
	static {
		ops = new HashMap<String, String>();
		ops.put("BZ", "000");
		ops.put("BN", "001");
		ops.put("ST", "010");
		ops.put("RL", "011");
		ops.put("LI", "100");
		ops.put("LM", "101");
		ops.put("AD", "110");
		ops.put("SB", "111");
	}

	public static void main(String[] args) throws IOException {
		PrintWriter f0 = new PrintWriter(new FileWriter("output.hex"));
		Scanner Scanscan = new Scanner(System.in);
		System.out.print("Input filename:");
		String filename = Scanscan.nextLine();
		File inputFile = new File(filename);
		Scanner reader = new Scanner(inputFile);
		ArrayList<String> binaryList = new ArrayList<String>();
		while(reader.hasNextLine()){
			String myString = reader.nextLine();
			String[] parsed = myString.split("\\s");
			System.out.println("Opcode: " + parsed[0]);
			System.out.println("Args: " + parsed[1]);
			int rLocation = Integer.parseInt(parsed[1]);
			System.out.println("Parsed args: " + Integer.toBinaryString(rLocation));

			if (ops.containsKey(parsed[0])) {
				String op = ops.get(parsed[0]);
				String arg = convertArgs(Integer.toBinaryString(rLocation));
				binaryList.add(op + arg);
			}
		}
		StringBuilder sb = new StringBuilder();

		for (String binary : binaryList) {
			System.out.println(binary);
			int decimal = Integer.parseInt(binary,2);
			String hexStr = Integer.toString(decimal,16);
			if (hexStr.length() == 1) {
				hexStr = "0" + hexStr;
			}
			sb.append(hexStr);
			sb.append(" ");
		}

		System.out.print("hex: " + sb.toString());
		f0.print("v2.0 raw\n");
		f0.print(sb.toString());

		f0.close();
	}

	public static String convertArgs(String str){
		int missing = ARGS_BIT_COUNT - str.length();
		for(int i = 0; i < missing; i++) {
			str = "0" + str;
		}
		return str;
	}



}