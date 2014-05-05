package kuro.amaktet.resource;

//standard library imports
import java.io.File;
import java.util.Scanner;

public class TextResource extends Resource {
	//fields
	protected String data;
	private String data_backup;

	//constructor
	public TextResource( String relative_path){
		super( relative_path);
		data = null;
		data_backup = null;}

	//overload methods
	@Override
	public void load(){
		super.load();
		File file = this.getResolvedPath();
		data = "";
		try {
			Scanner scanner = new Scanner( file);
			while( scanner.hasNextLine())
				data += scanner.nextLine() + "\n";
			setLoaded( true);}
		catch( java.io.FileNotFoundException exception){
			data = null;
			throw new ResourceLoadException(
				String.format( "File could not be found: %s",
					file.getPath()),
				exception);}}
			
	@Override
	public void unload(){
		data = null;
		super.unload();}

	@Override
	public void backup(){
		data_backup = new String( data);}
	@Override
	public void restore(){
		data = data_backup;
		data_backup = null;}
	@Override
	public void clearBackup(){
		data_backup = null;}

	//accessor methods
	public String getData(){
		return data;}
}