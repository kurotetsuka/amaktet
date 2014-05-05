package kuro.amaktet.math;

//standard library imports
import java.awt.Dimension;

public class Dimension3D extends Dimension {
	public int depth;

	//constructors
	public Dimension3D(){
		width = 0;
		depth = 0;
		height = 0;}
	public Dimension3D( Dimension other){
		setSize( other);}
	public Dimension3D( Dimension3D other){
		setSize( other);}
	public Dimension3D( int width, int depth, int height){
		this.width = width;
		this.depth = depth;
		this.height = height;}
	public Dimension3D( int width, int height){
		this.width = width;
		this.height = height;
		this.depth = 0;}

	//overload methods
	@Override
	public void setSize( Dimension other){
		if( other instanceof Dimension3D)
			setSize( (Dimension3D) other);
		else {
			super.setSize( other);
			depth = 0;}}
	public void setSize( Dimension3D other){
		this.width = other.width;
		this.depth = other.depth;
		this.height = other.height;}
	@Override
	public void setSize( double width, double height){
		this.width = (int) Math.round( width);
		this.height = (int) Math.round( height);
		this.depth = 0;}
	@Override
	public void setSize( int width, int height){
		this.width = width;
		this.height = height;
		this.depth = 0;}
	public void setSize( int width, int depth, int height){
		this.width = width;
		this.depth = depth;
		this.height = height;}
}