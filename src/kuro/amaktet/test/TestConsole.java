package kuro.amaktet.test;

// standard library imports


// local imports
import kuro.amaktet.ConsoleManager;
import kuro.amaktet.util.Console;
import kuro.amaktet.util.event.CommandEvent;
import kuro.amaktet.util.event.CommandListener;

public class TestConsole implements CommandListener{
	public static void main( String[] args){
		new TestConsole( args);}

	private ConsoleManager manager;

	public TestConsole( String[] args){
		manager = new ConsoleManager();
		manager.setStandardConsoleEnabled( true);
		manager.addCommandListener( this);}

	public void commandReceived( CommandEvent event){
		manager.printf(
			"event: %s\ncommand: %s\n",
			event, event.getCommand());}
}