package kuro.amaktet.res;

//standard library imports
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

//lwjgl imports
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class TextureResource extends Resource {
	//private members
	private Texture texture;

	public TextureResource( String relative_path){
		super( relative_path);
		texture = null;}

	//overload methods
	@Override
	public void load(){
		super.load();
		//get file
		File image_file = this.getResolvedPath();
		try{
			//open stream
			InputStream stream =
				new FileInputStream( image_file);
			//load into opengl texture
			texture = TextureLoader.getTexture( getExtension(), stream);
			texture.bind();
			//set texture parameters
			GL11.glTexParameteri(
				GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
				GL11.GL_NEAREST);
			GL11.glTexParameteri(
				GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
				GL11.GL_NEAREST);
			//confirm that loading suceeded
			setLoaded( true);}
		//catch non-existent files
		catch( java.io.FileNotFoundException exception){
			throw new ResourceLoadException(
				String.format( "File could not be found: %s",
					image_file.getPath()),
				exception);}
		//catch other io exceptions
		catch( java.io.IOException exception){
			throw new ResourceLoadException(
				"Encountered an exception while reading file.",
				exception);}}

	@Override
	public void unload(){
		texture.release();
		super.unload();}

	public void backup(){}
	public void restore(){}
	public void clearBackup(){}

	//accessor methods
	public Texture getTexture(){
		return texture;}

}