package kuro.amaktet.util;

// standard library imports
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class InputStreamHandler extends Thread {
	// fields
	private Vector<InputStreamListener> listeners;
	BufferedInputStream input;
	private boolean stop_flag;
	private int wait_millis;

	// constructors
	public InputStreamHandler( InputStream stream){
		this.listeners = new Vector<InputStreamListener>();
		input = new BufferedInputStream( stream);
		wait_millis = 200;
		stop_flag = true;}

	// public functions
	public void addListener( InputStreamListener listener){
		listeners.add( listener);}

	// thread override functions
	@Override
	public void run(){
		while( ! stop_flag){
			// check available data
			int available = 0;
			try{ available = input.available();}
			catch( IOException exception){
				exception.printStackTrace();}
			if( available > 0){
				// if we have data available, read, then throw events
				byte[] bytes = new byte[ available];
				try{
					input.read( bytes, 0, available);
					throwEvent( bytes);}
				catch( IOException exception){
					exception.printStackTrace();}}
			else {
				// if we don't have data available, wait till we do
				try{ Thread.sleep( wait_millis);}
				catch( InterruptedException exception){}}}
		try{ input.close();}
		catch( IOException exception){}}

	// thread overrides
	@Override
	public void start(){
		stop_flag = false;
		super.start();}

	// public functions
	public void close(){
		stop_flag = true;}

	// private utility functions
	private void throwEvent( byte[] bytes){
		InputStreamEvent event = new InputStreamEvent(
			this, bytes, InputStreamEvent.Type.INPUT_RECEIVED);
		for( InputStreamListener listener : listeners)
			listener.inputReceived( event);}
}