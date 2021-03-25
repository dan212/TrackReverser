package gcode_reverser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class Reverser {

	
	
	public static void main(String[] args) {
		Vector<CommandBlock> blocks = new Vector<CommandBlock>();
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter file name \n");
		String fileName = sc.nextLine();
		String outFileName = fileName.substring(0, fileName.length()-6) + "_reverse.gcode";
		BufferedWriter writer;
		BufferedReader reader;
		try {
			writer = new BufferedWriter(new FileWriter(outFileName));
			reader = new BufferedReader(new FileReader(fileName));
			String line = reader.readLine();
			while (line != null) {
				if (line.contains(";TYPE:WALL-OUTER")) {
					CommandBlock commb = new CommandBlock(line + "LAYER NUMBER "+ Integer.toString(blocks.size()));
					commb.commands.add(line);
					line = reader.readLine();
					if (line == null) break;
					while(line.charAt(0) != ';') {
						if(line.contains("E")) {
							line = line.replaceFirst("E(\\S+)", "E0");
						}
						commb.commands.add(line);
						line = reader.readLine();
						if (line == null) break;
					}
					blocks.add(commb);
				}else {
					CommandBlock commb = new CommandBlock("GENERAL BLOCK");
					commb.commands.add(line);
					line = reader.readLine();
					while(!line.contains(";TYPE:WALL-OUTER")) {
						commb.commands.add(line);
						line = reader.readLine();
						if (line == null) break;
					}
					blocks.add(commb);
				}
			}
			reader.close();
			CommandBlock stBlock = new CommandBlock(blocks.firstElement());
			CommandBlock lsBlock = new CommandBlock(blocks.lastElement());
			blocks.removeElementAt(0);
			blocks.removeElementAt(blocks.size()-1);
			for (String st : stBlock.commands) {
				writer.write(st);
				writer.newLine();
				System.out.print(st + '\n');
			}
			for (int i = blocks.size()-1; i >= 0; i--) {
				for (String st : blocks.elementAt(i).commands) {
					writer.write(st);
					writer.newLine();
					System.out.print(st + '\n');
				} 
			}
			for (String st : lsBlock.commands) {
				writer.write(st);
				writer.newLine();
				System.out.print(st + '\n');
			}
			writer.close();
			System.out.print("Job's done");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
