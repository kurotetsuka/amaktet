package kuro.amaktet.util;

public class Timer {
	// fields
	public double time_old;
	public double time;
	public double dtime;
	
	// constructor
	public Timer(){
		time = getTime();
		time_old = time;
		dtime = 0;}
	
	// methods
	public void update(){
		time_old = time;
		time = getTime();
		dtime = time - time_old;}
	
	private double getTime(){
		return (double) System.currentTimeMillis() / 1000;}
}