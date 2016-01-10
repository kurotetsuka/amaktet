package kuro.amaktet;

// standard library imports
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// lwjgl imports
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.LWJGLException;

// local imports
// import kuro.amaktet.asset.Tileset;

public class Controller implements
		WindowListener, ComponentListener, ActionListener{
	// private Members
	private Game game;
	private Gui gui;
	private Render render;

	// private fields
	private boolean debug = false;
	private boolean mouseDragging = false;

	// constructors
	public Controller(){
		game = null;
		gui = null;
		render = null;}

	// public methods
	public void connectTo( Game game){
		this.game = game;}

	public void connectTo( Gui gui){
		this.gui = gui;
		// window event registration
		gui.addWindowListener( this);
		// component event registration
		gui.addComponentListener( this);
		gui.panel.addComponentListener( this);
		gui.gamePanel.addComponentListener( this);
		gui.gamePanel.canvas.addComponentListener( this);
		gui.gamePanel.canvas.requestFocus();
		gui.testPanel.button.addActionListener( this);}
	public void connectTo( Render render){
		this.render = render;}

	public void update(){
		mouseUpdate();
		keyboardUpdate();}

	// Event handles
	// Action handles
	public void actionPerformed( ActionEvent event){
		String command = event.getActionCommand();
		switch( command){
			case "gui.test.button":
				gui.gamePanel.switchTo();
				gui.gamePanel.canvas.requestFocus();
				break;
			default: break;}}

	// Component handles
	public void componentHidden( ComponentEvent event){}
	public void componentMoved( ComponentEvent event){}
	public void componentResized( ComponentEvent event){
		render.resize();}
	public void componentShown( ComponentEvent event){}

	// Window handles
	public void windowActivated( WindowEvent event){}
	public void windowClosed( WindowEvent event){}
	public void windowClosing( WindowEvent event){
		if( debug) System.out.println("Window closing");
		render.unload();
		if( debug) System.out.println("Renderer unloaded");}
	public void windowDeactivated( WindowEvent event){}
	public void windowDeiconified( WindowEvent event){}
	public void windowIconified( WindowEvent event){}
	public void windowOpened( WindowEvent event){}

	// Private methods
	// Mouse handle
	private void mouseUpdate(){
		Point mouse_position = new Point(
			Mouse.getX(), Mouse.getY());
		Point mouse_velocity = new Point(
			Mouse.getDX(), Mouse.getDY());
		boolean mouse0_down = Mouse.isButtonDown( 0);
		boolean mouse1_down = Mouse.isButtonDown( 1);
		boolean mouse2_down = Mouse.isButtonDown( 2);
		// set cursor if dragging
		if( mouse0_down && ! mouseDragging){
			gui.setCursor( new Cursor( Cursor.MOVE_CURSOR));}
		else if( ! mouse0_down && mouseDragging){
			gui.setCursor( Cursor.getDefaultCursor());}
		// update cursor location
		game.cursor.position = game.view.relativize( mouse_position);
		// mouse draging
		if( mouseDragging)
			game.view.move(
				- mouse_velocity.x, - mouse_velocity.y);
		mouseDragging = mouse0_down;
		// check for mouse wheel changes
		int scrollTicks = Mouse.getDWheel() / 120;
		if( scrollTicks != 0)
			// zoom in on cursor
			game.view.zoom( scrollTicks, mouse_position);}

	// LWJGL Keyboard handle
	private void keyboardUpdate(){
		while( Keyboard.next()){
			int key = Keyboard.getEventKey();
			char key_char = Keyboard.getEventCharacter();
			boolean key_down = Keyboard.getEventKeyState();
			boolean repeat = Keyboard.isRepeatEvent();
			if( debug)
				System.out.printf( "Key Pressed: %d, %b, %b\n",
					key, key_down, repeat);
			switch( key){
				// movement
				case Keyboard.KEY_W:{
					if( key_down)
						game.view.stepMove( 0, + 1);
					break;}
				case Keyboard.KEY_S:{
					if( key_down)
						game.view.stepMove( 0, - 1);
					break;}
				case Keyboard.KEY_A:{
					if( key_down)
						game.view.stepMove( - 1, 0);
					break;}
				case Keyboard.KEY_D:{
					if( key_down)
						game.view.stepMove( + 1, 0);
					break;}
				// reload all resources
				case Keyboard.KEY_R:{
					if( key_down)
						game.refreshResources();
					break;}
				// switch to options panel
				case Keyboard.KEY_Z:{
					gui.testPanel.switchTo();
					break;}
				// exit
				case Keyboard.KEY_Q:
				case Keyboard.KEY_ESCAPE:{
					gui.close();
					break;}
				// go fullscreen
				case Keyboard.KEY_F11:{
					if( key_down)
						gui.setFullscreen( ! gui.getFullscreen());
					break;}
				// zoom
				case 201:{ // page up
					if( key_down)
						game.view.zoom( 1);
					break;}
				case 209:{ // page down
					if( key_down)
						game.view.zoom( - 1);
					break;}
				// debug
				case Keyboard.KEY_C:{ // enter
					if( key_down)
						debug = ! debug;
					break;}}}}
}