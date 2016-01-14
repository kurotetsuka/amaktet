package kuro.amaktet;

// standard library imports
import java.io.File;
import java.io.PrintStream;
import javax.swing.JFrame;

// local imports
import kuro.amaktet.util.*;

public class GameLoader {
	// Public members
	public Configuration config;
	public Controller controller;
	public Game game;
	public Gui gui;
	public Render render;
	public ResourceManager resourceManager;
	public Timer timer;
	// Private members
	private LoaderGui loaderGui;

	// Constructors
	public GameLoader(String[] args){
		// load configuration
		config = new Configuration();
		File config_meta = new File( "config/meta.ini");
		if( config_meta.exists())
			try{
				config.open( config_meta);}
			catch( java.io.IOException exception){
				exception.printStackTrace();}
		config.load();
		config.applyGuiSettings();

		// suppress slick-util debug output
		org.newdawn.slick.util.DefaultLogSystem.out =
			new PrintStream( new NullOutputStream());

		// load loader gui
		loaderGui = new LoaderGui();

		// load game
		game = new Game();

		// load gui
		gui = new Gui();
		gui.load();

		// load render
		render = new Render( game);
		render.linkToCanvas( gui.gamePanel.canvas);
		render.load();

		// load controller
		controller = new Controller();
		controller.connectTo( game);
		controller.connectTo( gui);
		controller.connectTo( render);
		//controller.update();

		// load resource manager
		resourceManager = new ResourceManager();
		game.setResourceManager( resourceManager);

		// load timer
		timer = new Timer();
		game.setTimer( timer);

		// link and load persistant resources
		game.loadTilesets();
		game.loadSprites();
		game.linkResources();
		resourceManager.load();
		game.loadTileData();

		//show window
		gui.setVisible( true);

		// start game execution
		GameExecutionThread execution = new GameExecutionThread(
			controller, game, gui, render, timer);
		execution.start();}

	// Private classes
	private class LoaderGui extends JFrame {}
}