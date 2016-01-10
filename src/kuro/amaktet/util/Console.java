package kuro.amaktet.util;

// standard library imports
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.PriorityQueue;
import java.util.Vector;

// local imports
import kuro.amaktet.util.event.CommandEvent;
import kuro.amaktet.util.event.CommandListener;

public class Console {
	// fields
	private InputStream input;
	private OutputStream output;
	private PriorityQueue<String> input_queue;
	private PriorityQueue<String> output_queue;

	private InputThread input_thread;
	private Object input_thread_lock;

	private OutputThread output_thread;
	private Object output_thread_lock;

	private CommandConsumerThread consumer_thread;
	private Vector<CommandListener> listeners;

	public Console( InputStream input, OutputStream output){
		input_queue = new PriorityQueue<String>();
		output_queue = new PriorityQueue<String>();
		// null checks!
		this.input = input;
		this.output = output;
		input_thread = null;
		output_thread = null;
		input_thread_lock = new Object();
		output_thread_lock = new Object();
		listeners = new Vector<CommandListener>();
		consumer_thread = new CommandConsumerThread();}

	// adds
	public void setInputStream( InputStream input){
		synchronized( input_thread_lock){
			if( input_thread != null)
				throw new java.util.ConcurrentModificationException(
					"Cannot change the input source while input thread is running");
			this.input = input;}}
	public void setOutputStream( OutputStream output){
		synchronized( output_thread_lock){
			if( output_thread != null)
				throw new java.util.ConcurrentModificationException(
					"Cannot change the output sink while output thread is running");
			this.output = output;}}
	public InputStream getInputStream(){
		return this.input;}
	public OutputStream getOutputStream(){
		return this.output;}

	// prints
	// print
	public void print( Object object){
		print( object.toString());}
	public void print( String string){
		output_queue.add( string);}
	// println
	public void println(){
		print( "\n");}
	public void println( Object object){
		println( object.toString());}
	public void println( String string){
		print( string);
		println();}
	// printf
	public void printf( String string, Object... args){
		if( args.length == 0 )
			print( String.format( string));
		else
			print( String.format( string, args));}

	// adds/removes
	public void addCommandListener( CommandListener listener){
		listeners.add( listener);}
	public void removeCommandListener( CommandListener listener){
		listeners.remove( listener);}

	// event invokations
	private void invokeCommandReceived( String command){
		CommandEvent event = new CommandEvent( this, command);
		for( CommandListener listener : listeners )
			try {
				listener.commandReceived( event);}
			catch( Exception exception){
				exception.printStackTrace();}}

	// other public methods
	public void start(){
		// stop consumer thread
		consumer_thread.start();
		// stop input thread
		synchronized( input_thread_lock){
			input_thread = new InputThread( input);
			input_thread.start();}
		// stop output thread
		synchronized( output_thread_lock){
			output_thread = new OutputThread( output);
			output_thread.start();}}

	public void stop(){
		// stop consumer thread
		consumer_thread.requestStop();
		// stop input thread
		synchronized( input_thread_lock){
			if( input_thread != null)
				input_thread.close();}
		// stop output thread
		synchronized( output_thread_lock){
			if( output_thread != null)
				output_thread.close();}}

	// confirms
	private void confirmInputStopped(){
		// synchronized for protection
		synchronized( input_thread_lock){
			input_thread = null;}}
	private void confirmOutputStopped(){
		// synchronized for protection
		synchronized( output_thread_lock){
			output_thread = null;}}

	// subclasses
	public class CommandConsumerThread extends Thread {
		private int pollingDelay;
		public boolean stop_requested;

		public CommandConsumerThread(){
			stop_requested = false;
			pollingDelay = 200;}

		public void run(){
			while( ! stop_requested){
				// turn queued input into a command
				String command = input_queue.poll();
				if( command != null)
					invokeCommandReceived( command);
				// sleep a little
				try{ Thread.sleep( pollingDelay);}
				catch( InterruptedException exception){}}}

		public void requestStop(){
			stop_requested = true;}
	}

	private class InputThread extends Thread {
		// fields
		private BufferedReader source;
		private boolean close_requested;
		private String line;
		private int pollingDelay;

		// constructor
		public InputThread( InputStream source){
			this.source = new BufferedReader(
				new InputStreamReader( source));
			this.line = new String();
			this.close_requested = false;
			this.pollingDelay = 1;}

		// functions
		public void run(){
			try{
				while( ! close_requested){
					// wait a little
					try{ Thread.sleep( pollingDelay);}
					catch( InterruptedException exception){}
					try {
						// read any input
						while( source.ready()){
							// read a char
							int char_int = source.read();
							// catch end of file?
							if( char_int < 0){
								System.out.println("End of file?");
								break;}
							char ch = (char) char_int;
							// catch end of line
							if( ch == '\n'){
								input_queue.add( line);
								line = new String();}
							else
								line += ch;}}
					catch( IOException exception){
						System.err.println( exception.getStackTrace());
						break;}}
				// try to close
				try{ source.close();}
				catch( IOException exception){
					System.err.printf(
						"Error closing input in Console %s input thread:\n%s",
						Console.this.toString(), exception.getStackTrace());}}
			// catch any really bad shit
			catch( Exception exception){
				System.err.printf(
					"Error in Console %s input thread:\n%s",
					Console.this.toString(), exception.getStackTrace());;}
			finally{
				confirmInputStopped();}}

		public void close(){
			close_requested = true;}
	}

	private class OutputThread extends Thread {
		// fields
		private BufferedWriter output;
		private boolean close_requested;
		private int pollingDelay;

		// constructor
		public OutputThread( OutputStream output){
			this.output = new BufferedWriter(
				new OutputStreamWriter( output));
			this.close_requested = false;
			this.pollingDelay = 1;}

		// functions
		public void run(){
			try{
				while( ! close_requested){
					// sleep for some time
					try{ Thread.sleep( pollingDelay);}
					catch( InterruptedException exception){}
					try {
						// write any queued output
						while( ! output_queue.isEmpty()){
							String string = output_queue.poll();
							if( string == null) continue;
							output.write( string, 0, string.length());}
						output.flush();}
					// catch the bad shit
					catch( IOException exception){
						System.err.println( exception.getStackTrace());
						break;}}
				// try to close output stream
				try{ output.close();}
				catch( IOException exception){
					System.err.printf(
						"Error closing output in Console %s output thread:\n%s",
						Console.this.toString(), exception.getStackTrace());}}
			// catch any really bad shit
			catch( Exception exception){
				System.err.printf(
					"Error in Console %s output thread:\n%s",
					Console.this.toString(), exception.getStackTrace());;}
			// confirm that we're done
			finally{
				confirmOutputStopped();}}

		public void close(){
			close_requested = true;}
	}
}
