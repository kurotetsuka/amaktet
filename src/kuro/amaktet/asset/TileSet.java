package kuro.amaktet.asset;

//standard library imports
import java.util.Collection;
import java.util.HashMap;
import java.util.NoSuchElementException;

//json simple imports
import org.json.simple.*;
//json simpler imports
import kuro.json.JSONAdapter;

//local imports
import kuro.amaktet.resource.*;

public class TileSet implements ResourceContainer {
	//static fields
	private static final String tilesetFolder =
		"resource/tileset/";

	//private fields
	private String name;
	private ResourceList resources;
	private TextureResource textureResource;
	private JSONResource configResource;
	private HashMap<String, Tile> map;
	private boolean debug = false;

	//property fields
	private String title;
	private int twidth;
	private int theight;

	//constructor
	public TileSet( String name){
		super();
		//load fields
		this.name = name;
		map = new HashMap< String, Tile>();
		String relativeName = tilesetFolder + name;
		//setup resources
		textureResource = new TextureResource( relativeName + ".png");
		configResource = new JSONResource( relativeName + ".json");
		resources = new ResourceList();
		resources.add( textureResource);
		resources.add( configResource);
		//initialize properties
		this.title = null;
		this.twidth = 0;
		this.theight = 0;}

	//tile map access functions
	public boolean contains( String key){
		return map.containsKey( key);}
	public Tile get( String key){
		return map.get( key);}
	public Collection<Tile> getTiles(){
		return map.values();}

	//accessor functions
	public String getName(){
		return this.name;}

	//ResourceContainer functions
	public Collection<Resource> getResources(){
		return resources;}
	public java.util.Iterator<Resource> iterator(){
		return resources.iterator();}
	public void setNeeded( boolean needed){
		resources.setNeeded( needed);}

	//utility functions
	public void load(){
		if( ! configResource.isResolved())
			throw new RuntimeException(
				String.format( "TileSet %s config not resolved\n", name));
		if( ! configResource.isLoaded())
			throw new RuntimeException(
				String.format( "TileSet %s config not loaded\n", name));

		//load confiuration objects
		Object config = configResource.getRoot();
		JSONAdapter config_adapter = new JSONAdapter( config);

		//get basic properties
		String request = null;
		try{
			String title = config_adapter.getString( request = "title");
			int twidth = config_adapter.getInteger( request = "twidth");
			int theight = config_adapter.getInteger( request = "theight");
			this.title = title;
			this.twidth = twidth;
			this.theight = theight;}
		catch( NoSuchElementException exception){
			throw new RuntimeException(
				String.format(
					"Error in tileset %s config: missing %s field",
					name, request), exception);}
		catch( ClassCastException exception){
			throw new RuntimeException(
				String.format(
					"Error in tileset %s config: field %s of wrong type",
					name, request), exception);}

		//debug properties
		if( debug){
			System.out.printf( "title: %s\n", this.title);
			System.out.printf( "twidth: %d\n", this.twidth);
			System.out.printf( "theight: %d\n", this.theight);}

		//get tiles adapter
		JSONAdapter tiles = config_adapter.get( "tiles");
		if( ! tiles.isJSONObject())
			throw new RuntimeException(
				"TilesSet load failed - tiles field is not a json object");

		//load tiles
		for( Object key : tiles.getJSONObject().keySet()){
			//get the tile name
			if( ! ( key instanceof String)){
				System.out.printf(
					"Loading of a tile binding from tileset %s failed:\n",
					name);
				System.out.printf(
					"\tkey '%s' was not String, was %s\n",
					key, key.getClass().getName());
				continue;}
			String tile_name = (String) key;
			
			//parse the tile's properties
			JSONAdapter tile_adapter = tiles.get( tile_name);
			try {
				int tx = tile_adapter.getInteger( 0);
				int ty = tile_adapter.getInteger( 1);
				//create tile
				Tile tile = new Tile( tile_name, tx, ty,
					this.twidth, this.theight, textureResource);
				if( debug)
					System.out.printf( "tile %s\n", tile);
				//add tile
				map.put( tile_name, tile);}
			catch( NoSuchElementException exception){
				System.out.printf(
					"Loading tile %s of tileset %s failed\n",
					tile_name, name);}
			catch( ClassCastException exception){
				System.out.printf(
					"Loading tile %s of tileset %s failed\n",
					tile_name, name);}}}
}