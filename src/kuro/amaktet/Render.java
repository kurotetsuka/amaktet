package kuro.amaktet;

//standard Library imports
import java.awt.Point;
import java.awt.Canvas;
import java.awt.Dimension;
import java.util.Collection;
import java.util.Vector;

//lwjgl imports
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

//local imports
import kuro.amaktet.asset.*;
import kuro.amaktet.game.*;

public class Render {
	//Public members
	public Object loadLock;

	//Private members
	private Game game;
	private Cursor cursor;
	private View view;

	//Constructors
	public Render( Game game){
		//initialize public members
		loadLock = new Object();
		//initialize private members
		this.game = game;
		this.cursor = game.cursor;
		this.view = game.view;}

	//public methods
	public void linkToCanvas( Canvas canvas){
		try{
			Display.setParent( canvas);}
		catch( LWJGLException exception){
			exception.printStackTrace();}}

	public void load(){
		synchronized( loadLock){
			try{
				Display.create();}
			catch( LWJGLException exception) {
				exception.printStackTrace();}
			//initialize opengl
			resize();
			GL11.glEnable( GL11.GL_BLEND);
			GL11.glEnable( GL11.GL_TEXTURE_2D);
			GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
			GL11.glBlendFunc(
				GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);}}

	public void unload(){
		synchronized( loadLock){
			Display.destroy();}}

	public boolean isLoaded(){
		boolean result;
		synchronized( loadLock){
			result = Display.isCreated();}
		return result;}

	public void resize(){
		updateDimensions();
		updateView();}

	public Dimension getRenderDimension(){
		updateDimensions();
		return view.dimension;}

	//draw methods
	public void draw(){
		updateView();
		//Clear the screen and depth buffer
		GL11.glClear(
			GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		//draw background
		drawBackground();

		//draw the game view
		enableViewTransfomation();

		//draw test tile
		//drawTestTile();

		//draw test sprite
		Sprite sniper = game.getSprite("ally/sniper");
		drawSpriteTest( sniper);

		//draw grid
		//drawGrid( 16, 16);
		//draw cursor
		//drawCursor();
		//clean up
		disableViewTransformation();

		Display.update();}

	public void drawSpriteTest( Sprite sprite){
		//draw test sprite
		Vector<Sprite.Frame> frames = new Vector<Sprite.Frame>();
		frames.add( sprite.get( "male.normal.1"));
		frames.add( sprite.get( "male.normal.2"));
		frames.add( sprite.get( "male.normal.3"));
		frames.add( null);
		frames.add( sprite.get( "female.normal.1"));
		frames.add( sprite.get( "female.normal.2"));
		frames.add( sprite.get( "female.normal.3"));
		frames.add( null);
		frames.add( sprite.get( "male.focus.1"));
		frames.add( sprite.get( "male.focus.2"));
		frames.add( sprite.get( "male.focus.3"));
		frames.add( null);
		frames.add( sprite.get( "female.focus.1"));
		frames.add( sprite.get( "female.focus.2"));
		frames.add( sprite.get( "female.focus.3"));
		frames.add( null);
		frames.add( sprite.get( "male.walking.west.1"));
		frames.add( sprite.get( "male.walking.west.2"));
		frames.add( sprite.get( "male.walking.west.3"));
		frames.add( sprite.get( "male.walking.west.4"));
		frames.add( sprite.get( "female.walking.west.1"));
		frames.add( sprite.get( "female.walking.west.2"));
		frames.add( sprite.get( "female.walking.west.3"));
		frames.add( sprite.get( "female.walking.west.4"));
		frames.add( sprite.get( "male.walking.south.1"));
		frames.add( sprite.get( "male.walking.south.2"));
		frames.add( sprite.get( "male.walking.south.3"));
		frames.add( sprite.get( "male.walking.south.4"));
		frames.add( sprite.get( "female.walking.south.1"));
		frames.add( sprite.get( "female.walking.south.2"));
		frames.add( sprite.get( "female.walking.south.3"));
		frames.add( sprite.get( "female.walking.south.4"));
		frames.add( sprite.get( "male.walking.north.1"));
		frames.add( sprite.get( "male.walking.north.2"));
		frames.add( sprite.get( "male.walking.north.3"));
		frames.add( sprite.get( "male.walking.north.4"));
		frames.add( sprite.get( "female.walking.north.1"));
		frames.add( sprite.get( "female.walking.north.2"));
		frames.add( sprite.get( "female.walking.north.3"));
		frames.add( sprite.get( "female.walking.north.4"));
		frames.add( sprite.get( "male.walking.east.1"));
		frames.add( sprite.get( "male.walking.east.2"));
		frames.add( sprite.get( "male.walking.east.3"));
		frames.add( sprite.get( "male.walking.east.4"));
		frames.add( sprite.get( "female.walking.east.1"));
		frames.add( sprite.get( "female.walking.east.2"));
		frames.add( sprite.get( "female.walking.east.3"));
		frames.add( sprite.get( "female.walking.east.4"));

		float cell_width = 640;
		float cell_height = 640;
		int grid_width = 8;
		int grid_height = 6;
		boolean stop = false;
		GL11.glPushMatrix();
		GL11.glTranslatef( -240, 240, 0);
		GL11.glScaled(
			(float) 1 / 8,
			(float) 1 / 8, 1);
		for( int y = 0; y < grid_height; y++){
			for( int x = 0; x < grid_width; x++){
				int index = y * grid_width + x;
				if( index >= frames.size()){
					stop = true;
					break;}
				Sprite.Frame frame = frames.get( index);
				GL11.glPushMatrix();
				GL11.glTranslatef( x * cell_width, - y * cell_height, 0);
				drawGrid( 1, 1);
				if( frame != null)
					drawSpriteFrame( frame);
				GL11.glPopMatrix();}
			if( stop) break;}
		GL11.glPopMatrix();}
	public void drawBackground(){
		game.backgroundTextureResource.getTexture().bind();
		float halfWidth = view.halfDimension.width;
		float halfHeight = view.halfDimension.height;
		float texWidth = halfWidth / 4f;
		float texHeight = halfHeight / 4f;
		GL11.glBegin( GL11.GL_QUADS);
		GL11.glTexCoord2f( 0, texHeight);
		GL11.glVertex3f( - halfWidth, - halfHeight, Layer.Background);
		GL11.glTexCoord2f( texWidth, texHeight);
		GL11.glVertex3f( + halfWidth, - halfHeight, Layer.Background);
		GL11.glTexCoord2f( texWidth, 0);
		GL11.glVertex3f( + halfWidth, + halfHeight, Layer.Background);
		GL11.glTexCoord2f( 0, 0);
		GL11.glVertex3f( - halfWidth, + halfHeight, Layer.Background);
		GL11.glEnd();}
	public void drawCursor(){
		cursor.getTexture().bind();
		//draw quad
		GL11.glBegin( GL11.GL_QUADS);
		GL11.glTexCoord2f(0,0);
		GL11.glVertex3f(
			cursor.position.x - cursor.halfDimension.width,
			cursor.position.y - cursor.halfDimension.height,
			Layer.Cursor);
		GL11.glTexCoord2f(1,0);
		GL11.glVertex3f(
			cursor.position.x + cursor.halfDimension.width,
			cursor.position.y - cursor.halfDimension.height,
			Layer.Cursor);
		GL11.glTexCoord2f(1,1);
		GL11.glVertex3f(
			cursor.position.x + cursor.halfDimension.width,
			cursor.position.y + cursor.halfDimension.height,
			Layer.Cursor);
		GL11.glTexCoord2f(0,1);
		GL11.glVertex3f(
			cursor.position.x - cursor.halfDimension.width,
			cursor.position.y + cursor.halfDimension.height,
			Layer.Cursor);
		GL11.glEnd();}
	public void drawGrid( int grid_width, int grid_height){
		game.gridTextureResource.getTexture().bind();
		GL11.glBegin( GL11.GL_QUADS);
		GL11.glTexCoord2f( 0, 0);
		GL11.glVertex3f( -320.0f, -320.0f, Layer.Grid);
		GL11.glTexCoord2f( grid_width, 0);
		GL11.glVertex3f( +320.0f, -320.0f, Layer.Grid);
		GL11.glTexCoord2f( grid_width, grid_height);
		GL11.glVertex3f( +320.0f, +320.0f, Layer.Grid);
		GL11.glTexCoord2f( 0, grid_height);
		GL11.glVertex3f( -320.0f, +320.0f, Layer.Grid);
		GL11.glEnd();}
	public void drawTestTile(){
		Tile tile;
		tile = game.getTile( "chest_open");
		//tile = game.getTile( "chest_closed");
		//tile = game.getTile( "null");
		//tile = game.getTile( "stairs");
		//tile = game.getTile( "asdf");
		if( tile == null) return;
		tile.getTexture().bind();
		GL11.glBegin( GL11.GL_QUADS);
		tile.bind( 0, 1);
		GL11.glVertex3f( -320.0f, -320.0f, Layer.Tile);
		tile.bind( 1, 1);
		GL11.glVertex3f( +320.0f, -320.0f, Layer.Tile);
		tile.bind( 1, 0);
		GL11.glVertex3f( +320.0f, +320.0f, Layer.Tile);
		tile.bind( 0, 0);
		GL11.glVertex3f( -320.0f, +320.0f, Layer.Tile);
		GL11.glEnd();}
	public void drawSpriteFrame( Sprite.Frame frame){
		frame.getTexture().bind();
		GL11.glBegin( GL11.GL_QUADS);
		frame.bind( 0, 1);
		GL11.glVertex3f( -320.0f, -320.0f, Layer.Tile);
		frame.bind( 1, 1);
		GL11.glVertex3f( +320.0f, -320.0f, Layer.Tile);
		frame.bind( 1, 0);
		GL11.glVertex3f( +320.0f, +320.0f, Layer.Tile);
		frame.bind( 0, 0);
		GL11.glVertex3f( -320.0f, +320.0f, Layer.Tile);
		GL11.glEnd();}

	//Private Methods
	private void updateDimensions(){
		Canvas parent = Display.getParent();
		view.updateDimensions( 
			new Dimension( parent.getWidth(), parent.getHeight()));}
	private void updateView(){
		GL11.glViewport( 0, 0,
			view.dimension.width, view.dimension.height);
		GL11.glMatrixMode( GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(
			-view.halfDimension.width, view.halfDimension.width,
			-view.halfDimension.height, view.halfDimension.height,
			Layer.Top, Layer.Bottom);
		GL11.glMatrixMode( GL11.GL_MODELVIEW);}
	private void enableViewTransfomation(){
		GL11.glPushMatrix();
		GL11.glScaled( view.zoom_level, view.zoom_level, 1.0);
		GL11.glTranslatef( -view.position.x, -view.position.y, 0);}
	private void disableViewTransformation(){
		GL11.glPopMatrix();}

	//Sub-classes
	public static final class Layer {
		public static final float Bottom = -1.0f;
		public static final float Background = -0.1f;
		public static final float Environment = 0.0f;
		public static final float Tile = 0.1f;
		public static final float Grid = 0.15f;
		public static final float Object = 0.2f;
		public static final float Entity = 0.3f;
		public static final float Ceiling = 0.4f;
		public static final float Fog = 0.5f;
		public static final float Cursor = 0.6f;
		public static final float Interface = 0.7f;
		public static final float Mouse = 0.8f;
		public static final float Top= 1.0f;
	}
}