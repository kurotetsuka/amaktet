package kuro.amaktet;

// standard library imports
import java.awt.Point;
import java.util.HashMap;

// local imports
import kuro.amaktet.asset.*;
import kuro.amaktet.game.*;
import kuro.amaktet.res.*;
import kuro.amaktet.util.*;

public class Game {
	// public members
	public Cursor cursor;
	public Map map;
	public View view;

	// resources
	public TextureResource gridTextureResource;
	public TextureResource backgroundTextureResource;
	public ResourceList ui_resources;
	public HashMap<String, Sprite> sprites;
	public HashMap<String, Tileset> tilesets;
	public HashMap<String, Tile> tiles;

	// sprites
	private Sprite ally_sniper;

	// tilesets
	private Tileset volcano;

	// private members
	private ResourceManager resourceManager;
	private Timer timer;
	private Tile nullTile;

	// constructors
	public Game(){
		// load ui resources
		ui_resources = new ResourceList();
		cursor = new Cursor();
		backgroundTextureResource = new TextureResource(
			"res/ui/bg.png");
		gridTextureResource = new TextureResource(
			"res/ui/grid.png");
		ui_resources.add( cursor.textureResource);
		ui_resources.add( backgroundTextureResource);
		ui_resources.add( gridTextureResource);
		ui_resources.setNeeded( true);

		// load sprites
		sprites = new HashMap<String, Sprite>();

		// load tilesets
		tilesets = new HashMap<String, Tileset>();
		tiles = new HashMap<String, Tile>();

		// load members
		resourceManager = null;
		view = new View();
		nullTile = null;}

	// public methods
	public void update(){
		view.timeStep( timer.dtime);}

	public void linkResources(){
		// ui elements
		resourceManager.addResources( ui_resources);
		// tilesets
		for( Tileset tileset : tilesets.values()){
			resourceManager.addResource( tileset);
			resourceManager.addResources( tileset);}
		// sprites
		for( Sprite sprite : sprites.values()){
			resourceManager.addResource( sprite);
			resourceManager.addResources( sprite);}}

	public void loadSprites(){
		ally_sniper = new Sprite( "ally/sniper");
		sprites.put( ally_sniper.getName(), ally_sniper);
		for( Sprite sprite : sprites.values()){
			sprite.setDynamic( true);
			sprite.setNeeded( true);}}

	public void loadTilesets(){
		volcano = new Tileset( "volcano");
		tilesets.put( volcano.getName(), volcano);
		for( Tileset tileset : tilesets.values()){
			tileset.setDynamic( true);
			tileset.setNeeded( true);}}

	public void loadTileData(){
		for( Tileset tileset : tilesets.values()){
			for( Tile tile : tileset.getTiles())
				tiles.put( tile.getName(), tile);}
		nullTile = tiles.get( "null");}

	public void refreshResources(){
		resourceManager.load();}

	// get accessor methods
	public Sprite getSprite( String name){
		return sprites.get( name);}
	public Tileset getTileset( String name){
		return tilesets.get( name);}

	public Tile getTile( String name){
		Tile result = tiles.get( name);
		return ( result != null) ? result : nullTile;}

	// set accessor methods
	public void setResourceManager( ResourceManager resourceManager){
		this.resourceManager = resourceManager;}

	public void setTimer( Timer timer){
		this.timer = timer;}
}