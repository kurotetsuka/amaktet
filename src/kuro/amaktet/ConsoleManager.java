package kuro.amaktet;

//standard library imports
import java.util.Vector;

//local imports
import kuro.amaktet.util.Console;
import kuro.amaktet.util.event.CommandEvent;
import kuro.amaktet.util.event.CommandListener;

public class ConsoleManager implements CommandListener{
	//fields
	private Console standard_console;
	private Vector<Console> consoles;
	private Vector<CommandListener> listeners;

	//constructors
	public ConsoleManager(){
		consoles = new Vector<Console>();
		listeners = new Vector<CommandListener>();
		standard_console = null;}

	//public functions
	public void setStandardConsoleEnabled( boolean enabled){
		if( enabled){
			if( standard_console == null){
				standard_console = new Console(
					System.in, System.out);
				addConsole( standard_console);
				standard_console.start();}}
		else{
			removeConsole( standard_console);
			standard_console.stop();
			standard_console = null;}}

	//adds
	public void addCommandListener( CommandListener listener){
		listeners.add( listener);}
	public void addConsole( Console console){
		consoles.add( console);
		console.addCommandListener( this);}
	//removes
	public void removeCommandListener( CommandListener listener){
		listeners.remove( listener);}
	public void removeConsole( Console console){
		consoles.remove( console);
		console.removeCommandListener( this);}

	//prints
	// print
	public void print( Object object){
		for( Console console : consoles)
			console.print( object);}
	public void print( String string){
		for( Console console : consoles)
			console.print( string);}
	// println
	public void println( Object object){
		for( Console console : consoles)
			console.println( object);}
	public void println( String string){
		for( Console console : consoles)
			console.println( string);}
	// printf
	public void printf( String string, Object... args){
		for( Console console : consoles)
			console.printf( string, args);}

	//command listener handles
	public void commandReceived( CommandEvent event){
		for( CommandListener listener : listeners)
			listener.commandReceived( event);}
}
