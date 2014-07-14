package kuro.amaktet;

//local imports
import kuro.amaktet.util.Timer;

public class GameExecutionThread extends Thread {
	//Private members
	private Controller controller;
	private Game game;
	private Gui gui;
	private Render render;
	private Tick tick;
	private Timer timer;

	//Constructor
	public GameExecutionThread(
			Controller controller,
			Game game,
			Gui gui,
			Render render,
			Timer timer){
		this.controller = controller;
		this.game = game;
		this.gui = gui;
		this.render = render;
		this.timer = timer;
		tick = new Tick();}

	public void run(){
		while( gui.isVisible()){
			javax.swing.SwingUtilities.invokeLater( tick);
			try{ Thread.sleep( 30);}
			catch( InterruptedException exception){}}}

	//Private Classes
	private class Tick implements Runnable {
		public void run(){
			if( render.isLoaded()){
				timer.update();
				controller.update();
				game.update();
				render.draw();}}
	}
}