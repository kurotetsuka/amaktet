package kuro.amaktet.math;

// java imports
import java.util.Vector;

public class Direction {
	// constant directions
	// null direction
	public final static Direction ZERO = new Direction( Data.ZERO);
	// x,y-plane directions
	public final static Direction EAST = new Direction( Data.EAST);
	public final static Direction NORTH = new Direction( Data.NORTH);
	public final static Direction WEST = new Direction( Data.WEST);
	public final static Direction SOUTH = new Direction( Data.SOUTH);
	// z directions
	public final static Direction UP = new Direction( Data.UP);
	public final static Direction DOWN = new Direction( Data.DOWN);

	// information fields
	private final static int COUNT = 7;

	// actual data
	private Data data;

	// constructors
	public Direction(){
		this( ZERO);}
	public Direction( Data data){
		this.data = data;}
	public Direction( Direction other){
		this.data = other.data;}

	// public override methods
	@Override
	public boolean equals( Object other_o){
		if( !( other_o instanceof Direction))
			return false;
		else
			try {
				Direction other = (Direction) other_o;
				return this.data == other.data;}
			catch( Exception exception){
				return false;}}
	// public static methods
	public static int count(){
		return COUNT;}
	public static Vector<Direction> getDirections(){
		Vector< Direction> directions = new Vector<Direction>();
		directions.add( ZERO);
		directions.add( EAST);
		directions.add( NORTH);
		directions.add( WEST);
		directions.add( SOUTH);
		directions.add( UP);
		directions.add( DOWN);
		return directions;}

	// public methods
	public Data getData(){
		return data;}
	public Direction inverse(){
		return new Direction( this).invert();}
	public Direction invert(){
		switch( this.data){
			case EAST:
				this.data = Data.WEST;
				break;
			case NORTH:
				this.data = Data.SOUTH;
				break;
			case WEST:
				this.data = Data.EAST;
				break;
			case SOUTH:
				this.data = Data.NORTH;
				break;
			case UP:
				this.data = Data.DOWN;
				break;
			case DOWN:
				this.data = Data.UP;
				break;
			case ZERO:
			default:
				break;}
		return this;}

	public enum Data {
		ZERO, EAST, NORTH, WEST, SOUTH, UP, DOWN}
}