package kuro.amaktet.asset;

//LWJGL imports
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

//local imports;
import kuro.amaktet.res.TextureResource;

public class Tile {
	//drawing fields
	private TextureResource textureResource;
	//game fields
	private String key;
	private int tx;
	private int ty;
	private int twidth;
	private int theight;

	//constructor
	public Tile( String key, int tx, int ty,
			int twidth, int theight,
			TextureResource textureResource){
		this.key = key;
		this.tx = tx;
		this.ty = ty;
		this.twidth = twidth;
		this.theight = theight;
		this.textureResource = textureResource;}

	//public functions
	public void bind( int rx, int ry){
		Texture atlas = textureResource.getTexture();
		int atlas_width = atlas.getTextureWidth();
		int atlas_height = atlas.getTextureHeight();
		float x = twidth * ( tx + rx);
		float y = twidth * ( ty + ry);
			if( atlas_width != 0.0)
				x /= (float) atlas_width;
			if( atlas_height != 0.0)
				y /= (float) atlas_height;
		GL11.glTexCoord2f( x, y);}

	//get accessor functions
	public String getName(){
		return key;}
	public TextureResource getTextureResource(){
		return textureResource;}
	public Texture getTexture(){
		return textureResource.getTexture();}
	public String toString(){
		return String.format( "%s: %d, %d, %d, %d, %s",
			key, tx, ty, twidth, theight,
			textureResource.getResolvedPath());}
}