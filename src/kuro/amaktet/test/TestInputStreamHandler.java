package kuro.amaktet.test;

// local imports
import kuro.amaktet.util.*;

public class TestInputStreamHandler
		implements InputStreamListener {
	// fields
	static InputStreamHandler handler;

	// constructors
	public TestInputStreamHandler(){
		handler = new InputStreamHandler( System.in);
		handler.addListener( this);
		handler.start();
		try{ Thread.sleep( 2000);}
		catch( InterruptedException exception){}}

	// input stream listener functions
	public void inputReceived( InputStreamEvent event){
		String data = event.getDataString();
		System.out.printf(
			"Line entered: %s", data);
		if( data.equals( "exit"))
			handler.close();}

	// static functions
	public static void main( String[] args){
		new TestInputStreamHandler();}
}