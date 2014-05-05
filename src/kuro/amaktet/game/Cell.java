package kuro.amaktet.game;

//standard library imports
import java.util.EnumMap;
import java.util.Vector;
//local imports
import kuro.amaktet.asset.Tile;
import kuro.amaktet.math.Direction;

public class Cell {
	//Public Fields
	protected Tile tile;
	protected Entity entity;

	//Private Fields
	protected EnumMap<Direction.Data, Cell> neighbours;

	//Constuctors
	public Cell( Tile tile){
		//public fields
		this.tile = tile;
		this.entity = null;

		//private fields
		this.neighbours = new EnumMap<Direction.Data, Cell>(
			Direction.Data.class);}

	//get accessors
	public Cell getNeighbour( Direction direction){
		if( direction.equals( Direction.ZERO))
			throw new RuntimeException(
				"Direction.Zero is not a valid value for neighbour direction");
		return neighbours.get( direction.getData());}
	public Tile getTile(){
		return tile;}
	public Entity getEntity(){
		return entity;}

	//set accessors
	public void setNeighbour( Direction direction, Cell other){
		if( direction.equals( Direction.ZERO))
			throw new RuntimeException(
				"Direction.Zero is not a valid value for neighbour direction");
		this.neighbours.put( direction.getData(), other);
		other.neighbours.put( direction.inverse().getData(), this);}
	public void setTile( Tile tile){
		this.tile = tile;}
	public void setEntity( Entity entity){
		this.entity = entity;}
};