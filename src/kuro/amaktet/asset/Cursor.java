package kuro.amaktet.asset;

//standard library imports
import java.awt.Dimension;
//LWJGL imports
import org.newdawn.slick.opengl.Texture;
//local imports;
import kuro.amaktet.math.KVector;
import kuro.amaktet.res.TextureResource;

public class Cursor {
	//public members
	public KVector position;
	public Dimension dimension;
	public Dimension halfDimension;
	//private members
	// set to private when resource lists are implemented properly
	public TextureResource textureResource;

	//constructors
	public Cursor(){
		position = new KVector();
		textureResource = new TextureResource(
			"res/ui/cursor.png");
		textureResource.setNeeded( true);
		dimension = new Dimension( 40, 40);
		halfDimension = new Dimension(
			dimension.width / 2, dimension.height / 2);}

	//functions
	public Texture getTexture(){
		return textureResource.getTexture();}
}
