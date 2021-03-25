package gcode_reverser;

import java.util.Vector;

public class CommandBlock {

	public Vector<String> commands;
	public String type;
	
	public CommandBlock(String t){
		type = t;
		commands = new Vector<String>();
	}
	
	public CommandBlock(CommandBlock c) {
		type = c.type;
		commands = new Vector<String>(c.commands);
	}
}
