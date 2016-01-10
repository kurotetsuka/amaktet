package kuro.amaktet.game;

// standard library imports
import java.awt.Dimension;
import java.awt.Point;
// local imports
import kuro.amaktet.math.KDimension;
import kuro.amaktet.math.KVector;

public class View {
	// public members
	public Dimension dimension;
	public Dimension halfDimension;
	public KVector position;
	public KVector position_target;
	public double zoom_level;
	public double zoom_target;
	public Point zoom_centre;
	// config parameters
	public double zoom_period;
	// private members


	// constructors
	public View(){
		dimension = new Dimension();
		halfDimension = new Dimension();
		position = new KVector( 0, 0);
		position_target = new KVector( 0, 0);
		zoom_level = 1.0;
		zoom_target = zoom_level;
		zoom_centre = new Point( 0, 0);
		// config defaults
		zoom_period = 0.1;}

	// public methods
	public void updateDimensions( Dimension dimension){
		this.dimension = dimension;
		this.halfDimension.width = dimension.width / 2;
		this.halfDimension.height = dimension.height / 2;}

	public KVector relativize( Point point){
		// apply viewport
		KVector result = new KVector(
			point.x - halfDimension.width,
			point.y - halfDimension.height);
		// apply zoom
		result.x /= zoom_level;
		result.y /= zoom_level;
		// apply translation
		result.x += position.x;
		result.y += position.y;
		// done
		return result;}

	public Point derelativize( KVector vector){
		// make copy
		KVector result = new KVector( vector);
		// unapply translation
		result.x -= position.x;
		result.y -= position.y;
		// unapply zoom
		result.x *= zoom_level;
		result.y *= zoom_level;
		// unapply viewport
		result.x += halfDimension.width;
		result.y += halfDimension.height;
		// round
		return new Point(
			Math.round( result.x), Math.round( result.y));}

	public void timeStep( double dtime){
		double dzoom = dtime / zoom_period;
		// step the zoom level
		if( zoom_level != zoom_target){
			KVector prezoom = relativize( zoom_centre);
			zoom_level = ( ( 1.0 - dzoom) * zoom_level)
				+ ( dzoom * zoom_target);
			KVector postzoom = relativize( zoom_centre);
			KVector diff = KVector.sub( prezoom, postzoom);
			position.add( diff);
			position_target.add( diff);
		}

		// step the position
		position.x = (float)((( 1.0 - dzoom) * position.x)
			+( dzoom * position_target.x));
		position.y = (float)((( 1.0 - dzoom) * position.y)
			+( dzoom * position_target.y));}

	public void zoom( int ticks, Point zoom_centre){
		this.zoom_centre = zoom_centre;
		zoom_target *= Math.pow( 1.1, ticks);}
	public void zoom( int ticks){
		zoom( ticks, new Point(
			halfDimension.width, halfDimension.height));}

	public void move(double dx, double dy){
		position_target.x += dx / zoom_level;
		position_target.y += dy / zoom_level;}
	public void stepMove(double dx, double dy){
		move(
			dx * 0.1 * dimension.width,
			dy * 0.1 * dimension.width);}

	// private methods

}