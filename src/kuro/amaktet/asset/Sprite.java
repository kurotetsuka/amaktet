package kuro.amaktet.asset;

//standard library imports
import java.awt.Insets;
import java.util.Collection;
import java.util.HashMap;
import java.util.NoSuchElementException;

//json simple imports
import org.json.simple.*;
//json simpler imports
import kuro.json.JSONAdapter;

//LWJGL imports
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

//local imports
import kuro.amaktet.res.*;

public class Sprite implements ResourceContainer {
	//static fields
	private static final String spriteFolder =
		"res/sprite/";

	//private fields
	private String name;
	private ResourceList resources;
	private TextureResource glowResource;
	private TextureResource textureResource;
	private JSONResource configResource;
	private HashMap<String, Frame> map;
	private boolean debug = false;

	//property fields
	private String title;
	private int fwidth;
	private int fheight;
	private int cell_binding_x;
	private int cell_binding_y;
	private int cell_binding_width;
	private int cell_binding_height;
	private Insets insets;

	//constructor
	public Sprite( String name){
		super();
		//load fields
		this.name = name;
		map = new HashMap< String, Frame>();
		String relativeName = spriteFolder + name;
		//setup resources
		glowResource = new TextureResource( relativeName + ".glo.png");
		textureResource = new TextureResource( relativeName + ".tex.png");
		configResource = new JSONResource( relativeName + ".json");
		resources = new ResourceList();
		resources.add( glowResource);
		resources.add( textureResource);
		resources.add( configResource);
		//initialize properties
		this.title = null;
		fwidth = -1;
		fheight = -1;
		cell_binding_x = -1;
		cell_binding_y = -1;
		cell_binding_width = -1;
		cell_binding_height = -1;
		insets = null;}

	//tile map access functions
	public boolean contains( String key){
		return map.containsKey( key);}
	public Frame get( String key){
		return map.get( key);}
	public Collection<Frame> getFrames(){
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
				String.format( "Sprite %s config not resolved\n", name));
		if( ! configResource.isLoaded())
			throw new RuntimeException(
				String.format( "Sprite %s config not loaded\n", name));

		//load confiuration objects
		Object config_object = configResource.getRoot();
		JSONAdapter config = new JSONAdapter( config_object);

		//get basic properties
		String request = null;
		try{
			//get data
			String title = config.getString( request = "title");
			int fwidth = config.getInteger( request = "fwidth");
			int fheight = config.getInteger( request = "fheight");
			int cell_binding_x = config.derefInteger(
				request = "cell_binding.x");
			int cell_binding_y = config.derefInteger(
				request = "cell_binding.y");
			int cell_binding_width = config.derefInteger(
				request = "cell_binding.width");
			int cell_binding_height = config.derefInteger(
				request = "cell_binding.height");

			//set fields
			this.title = title;
			this.fwidth = fwidth;
			this.fheight = fheight;
			this.cell_binding_x = cell_binding_x;
			this.cell_binding_y = cell_binding_y;
			this.cell_binding_width = cell_binding_width;
			this.cell_binding_height = cell_binding_height;}
		catch( NoSuchElementException exception){
			throw new RuntimeException(
				String.format(
					"Error in sprite %s config: missing %s field",
					name, request), exception);}
		catch( ClassCastException exception){
			throw new RuntimeException(
				String.format(
					"Error in sprite %s config: field %s of wrong type",
					name, request), exception);}

		//debug properties
		if( debug){
			System.out.printf( "title: %s\n", title);
			System.out.printf( "fwidth: %d\n", fwidth);
			System.out.printf( "fheight: %d\n", fheight);
			System.out.printf(
				"cell_binding_x: %d\n", cell_binding_x);
			System.out.printf(
				"cell_binding_y: %d\n", cell_binding_y);
			System.out.printf(
				"cell_binding_width: %d\n", cell_binding_width);
			System.out.printf(
				"cell_binding_height: %d\n", cell_binding_height);}

		//little bit of validation
		if( cell_binding_x < 0)
			throw new RuntimeException( String.format(
				"Error in sprite %s config: field cell_binding.x is negative",
				name));
		if( cell_binding_y < 0)
			throw new RuntimeException( String.format(
				"Error in sprite %s config: field cell_binding.y is negative",
				name));
		if( cell_binding_width < 0)
			throw new RuntimeException( String.format(
				"Error in sprite %s config: field cell_binding.width is negative",
				name));
		if( cell_binding_height < 0)
			throw new RuntimeException( String.format(
				"Error in sprite %s config: field cell_binding.height is negative",
				name));
		//interpret the insets
		int inset_left = cell_binding_x;
		int inset_bottom = cell_binding_y;
		int inset_right = fwidth - cell_binding_width - cell_binding_x;
		int inset_top = fheight - cell_binding_height - cell_binding_y;
		int pixels_x = inset_left + inset_right + cell_binding_width;
		int pixels_y = inset_bottom + inset_top + cell_binding_height;
		//more validation
		if( pixels_x > fwidth)
			throw new RuntimeException( String.format(
				"Error in sprite %s config: cell binding x and width are too big, they describe insets that exceed the sprite's frame width.",
				name));
		if( pixels_y > fheight)
			throw new RuntimeException( String.format(
				"Error in sprite %s config: cell binding y and height are too big, they describe a binding that exceed the sprite's frame height.",
				name));
		//create insets
		insets = new Insets(
			inset_top, inset_left, inset_bottom, inset_right);
		//debug insets
		if( debug) System.out.printf( "insets: %s\n", insets);

		//get frames adapter
		JSONAdapter frames = config.get( "frames");
		if( ! frames.isJSONObject())
			throw new RuntimeException(
				"TilesSet load failed - frames field is not a json object");

		//load frames
		for( Object key : frames.getJSONObject().keySet()){
			//get the frame name
			if( ! ( key instanceof String)){
				System.out.printf(
					"Loading of a frame binding from sprite %s failed:\n",
					name);
				System.out.printf(
					"\tkey '%s' was not String, was %s\n",
					key, key.getClass().getName());
				continue;}
			String frame_name = (String) key;
			
			//parse the frame's properties
			JSONAdapter frame_adapter = frames.get( frame_name);
			try {
				int tx = frame_adapter.getInteger( 0);
				int ty = frame_adapter.getInteger( 1);
				//create frame
				Frame frame = new Frame( frame_name, tx, ty,
					this.fwidth, this.fheight);
				if( debug)
					System.out.printf( "frame %s\n", frame);
				//add frame
				map.put( frame_name, frame);}
			catch( NoSuchElementException exception){
				System.out.printf(
					"Loading frame %s of sprite %s failed\n",
					frame_name, name);}
			catch( ClassCastException exception){
				System.out.printf(
					"Loading frame %s of sprite %s failed\n",
					frame_name, name);}}}

	public TextureResource getGlowResource(){
		return glowResource;}
	public Texture getGlowmap(){
		return glowResource.getTexture();}
	public TextureResource getTextureResource(){
		return textureResource;}
	public Texture getTexture(){
		return textureResource.getTexture();}

	//subclasses
	public class Frame {
		//game fields
		private String key;
		private int tx;
		private int ty;
		private int fwidth;
		private int fheight;

		//constructors
		protected Frame( String key, int tx, int ty,
				int fwidth, int fheight){
			this.key = key;
			this.tx = tx;
			this.ty = ty;
			this.fwidth = fwidth;
			this.fheight = fheight;}

		//public functions
		public void bind( int rx, int ry){
			Texture atlas = textureResource.getTexture();
			int atlas_width = atlas.getTextureWidth();
			int atlas_height = atlas.getTextureHeight();
			float x = fwidth * ( tx + rx);
			float y = fwidth * ( ty + ry);
			if( atlas_width != 0.0)
				x /= (float) atlas_width;
			if( atlas_height != 0.0)
				y /= (float) atlas_height;
			GL11.glTexCoord2f( x, y);}

		//get accessor functions
		public String getName(){
			return key;}
		public TextureResource getGlowResource(){
			return glowResource;}
		public Texture getGlowmap(){
			return glowResource.getTexture();}
		public TextureResource getTextureResource(){
			return textureResource;}
		public Texture getTexture(){
			return textureResource.getTexture();}
		public String toString(){
			return String.format( "%s: %d, %d, %d, %d",
				key, tx, ty, fwidth, fheight);}
	}
}