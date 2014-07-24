package kuro.amaktet.game;

//standard library imports
import java.util.Vector;

//local imports
import kuro.amaktet.math.Dimension3D;
import kuro.amaktet.res.JSONResource;

public class Map {
	//Public Fields
	public Vector<Cell> cells;
	public Cell[][][] grid;
	public Dimension3D size;

	//private fields
	private JSONResource dataResource;
	

	//Constructors
	public Map(){
		cells = new Vector<Cell>();
		grid = new Cell[0][0][0];
		size = new Dimension3D();}
	public Map( Dimension3D size){
		setSize( size);}

	//Public Methods

	//get accessor methods
	public Dimension3D getSize(){
		return size;}

	//set acessor methods
	public void setSize(Dimension3D size){
		cells.clear();
		grid = new Cell[ size.width][size.depth][ size.height];
		this.size.setSize( size);
		relink();}
	public void setCell( int x, int y, Cell cell){}

	//Private utility methods
	public void relink(){}
}
