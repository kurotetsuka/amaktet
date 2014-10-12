package kuro.amaktet;

//standard library imports
import java.util.*;

//local imports
import kuro.amaktet.util.*;

public class Benchmarker {
	//private members
	private Timer timer;
	//benchmark tables
	private HashMap<String,Double> table;

	//constructor
	public Benchmarker(){
		timer = new Timer();}

	//public methods
	public void start( String key){}
	public void stop( String key){}
		
}