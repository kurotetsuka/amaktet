package kuro.amaktet.test;

// standard Library imports
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// lwjgl imports
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Mouse;

public class CursorTest extends JFrame {
	public static void main( String[] args){
		new CursorTest();}
	public CursorTest(){
		this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE);

		Container frame = getContentPane();
		Canvas canvas = new Canvas();
		Dimension dimension = new Dimension( 800, 600);
		canvas.setMinimumSize( dimension);
		canvas.setPreferredSize( dimension);
		frame.add( canvas);
		this.pack();
		this.setLocationRelativeTo(null);

		// init lwjgl
		try{
			Display.setParent( canvas);
			Display.create();}
		catch( LWJGLException exception){
			exception.printStackTrace();}
		this.setVisible(true);
		canvas.setCursor( new Cursor( Cursor.WAIT_CURSOR));


		/**/
		int[] window_event_codes = new int[]{ 
				WindowEvent.WINDOW_LOST_FOCUS,
				WindowEvent.WINDOW_GAINED_FOCUS};
		for( int code : window_event_codes){
			WindowEvent event = 
				new WindowEvent( this, code);
			Toolkit.getDefaultToolkit().
				getSystemEventQueue().postEvent( event);}
		/**/

		//canvas.setCursor( new Cursor( Cursor.WAIT_CURSOR));
		//canvas.setCursor( new Cursor( Cursor.DEFAULT_CURSOR));

		System.out.println("Done");}
}