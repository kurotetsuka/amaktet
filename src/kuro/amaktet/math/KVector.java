package kuro.amaktet.math;

public class KVector extends org.lwjgl.util.vector.Vector2f {
	//constructors
	public KVector(){
		super();}
	public KVector( KVector other){
		this.x = other.x;
		this.y = other.y;}
	public KVector( float x, float y){
		super( x, y);}

	//methods
	public KVector add( KVector other){
		this.x += other.x;
		this.y += other.y;
		return this;}
	public static KVector add( KVector one, KVector other){
		return new KVector( one).add( other);}

	public KVector sub( KVector other){
		this.x -= other.x;
		this.y -= other.y;
		return this;}
	public static KVector sub( KVector one, KVector other){
		return new KVector( one).sub( other);}

	public KVector mult( KVector other){
		this.x *= other.x;
		this.y *= other.y;
		return this;}
	public static KVector mult( KVector one, KVector other){
		return new KVector( one).mult( other);}

	public KVector div( KVector other){
		this.x /= other.x;
		this.y /= other.y;
		return this;}
	public static KVector div( KVector one, KVector other){
		return new KVector( one).div( other);}
}