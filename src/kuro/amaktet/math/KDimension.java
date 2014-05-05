package kuro.amaktet.math;

public class KDimension extends java.awt.geom.Dimension2D {
	public float width;
	public float height;

	public KDimension(){
		this( 0, 0);}
	public KDimension( float width, float height){
		this.width = width;
		this.height = height;}

	public double getWidth(){
		return width;}
	public double getHeight(){
		return height;}

	public void setSize( float width, float height){
		this.width = width;
		this.height = height;}

	public void setSize( double width, double height){
		this.width = (float) width;
		this.height = (float) height;}

	public KDimension half(){
		return new KDimension(
			this.width / 2.0f,
			this.height / 2.0f);}
}