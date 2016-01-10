package kuro.amaktet.util.event;

// standard library imports
import java.util.Arrays;
import java.util.EventObject;

public class CommandEvent extends EventObject {
	// fields
	private String command;
	private String line;
	private String[] args;

	// constructor
	public CommandEvent( Object source, String line){
		super( source);
		this.line = line;
		String[] split = line.split( " ");
		command = split.length > 0 ?
			split[0] : null;
		args = split.length > 1 ?
			Arrays.copyOfRange( split, 1, split.length) :
			new String[0];}

	// accessors
	public String getCommand(){
		return command;}
	public String getLine(){
		return line;}
	public String[] getArgs(){
		return args;}
	public String toString(){
		return String.format(
			"%s :: %s", super.toString(), this.line);}
}