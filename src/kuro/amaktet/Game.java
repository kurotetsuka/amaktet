package kuro.amaktet;

//standard library imports
import java.awt.Point;
import java.util.HashMap;

//local imports
import kuro.amaktet.asset.*;
import kuro.amaktet.game.*;
import kuro.amaktet.resource.*;
import kuro.amaktet.util.*;

public class Game {
	//Public members
	public Cursor cursor;
	public Map map;
	public View view;

	//resources
	public TextureResource gridTextureResource;
	public TextureResource backgroundTextureResource;
	public HashMap<String, Sprite> sprites;
	public HashMap<String, TileSet> tilesets;
	public HashMap<String, Tile> tiles;

	//sprites
	private Sprite ally_sniper;
	private Sprite testSprite;

	//tilesets
	private TileSet volcano;

	//Private members
	private ResourceManager resourceManager;
	private Timer timer;
	private Tile nullTile;

	//Constructors
	public Game(){
		//load resources
		backgroundTextureResource = new TextureResource(
			"resource/ui/bg.png");
		gridTextureResource = new TextureResource(
			"resource/ui/grid.png");
		backgroundTextureResource.setNeeded( true);
		gridTextureResource.setNeeded( true);

		//load sprites
		sprites = new HashMap<String, Sprite>();
		ally_sniper = new Sprite( "ally/sniper");
		testSprite = new Sprite( "test");
		sprites.put( ally_sniper.getName(), ally_sniper);
		sprites.put( testSprite.getName(), testSprite);
		ally_sniper.setNeeded( true);
		testSprite.setNeeded( true);

		//load tilesets
		tilesets = new HashMap<String, TileSet>();
		tiles = new HashMap<String, Tile>();
		volcano = new TileSet( "volcano");
		tilesets.put( volcano.getName(), volcano);
		volcano.setNeeded( true);

		//load members
		cursor = new Cursor();
		resourceManager = null;
		view = new View();
		nullTile = null;}

	//Public methods
	public void update(){
		view.timeStep( timer.dtime);}

	public void linkPersistantResources(){
		resourceManager.addResource( backgroundTextureResource);
		resourceManager.addResource( gridTextureResource);
		resourceManager.addResource( cursor.textureResource);
		resourceManager.addResources( volcano);
		resourceManager.addResources( ally_sniper);
		resourceManager.addResources( testSprite);}

	public void loadTileSets(){
		for( TileSet tileset : tilesets.values()){
			tileset.load();
			for( Tile tile : tileset.getTiles())
				tiles.put( tile.getName(), tile);}
		nullTile = tiles.get( "null");}

	public void loadSprites(){
		for( Sprite sprite : sprites.values())
			sprite.load();}

	//get accessor methods
	public Sprite getSprite( String name){
		return sprites.get( name);}
	public TileSet getTileSet( String name){
		return tilesets.get( name);}

	public Tile getTile( String name){
		Tile result = tiles.get( name);
		return ( result != null) ? result : nullTile;}

	//set ccessor methods
	public void setResourceManager( ResourceManager resourceManager){
		this.resourceManager = resourceManager;}

	public void setTimer( Timer timer){
		this.timer = timer;}
}