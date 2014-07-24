package kuro.amaktet.res;

//json simple imports
import org.json.simple.*;
import org.json.simple.parser.*;

public class JSONResource extends TextResource {
	Object root;
	Object root_backup;

	//constructors
	public JSONResource( String relative_path){
		super( relative_path);
		root = null;}

	//overload methods
	@Override
	public void load(){
		super.load();
		try {
			root = new JSONParser().parse( data);}
		catch( ParseException exception){
			setLoaded( false);
			throw new ResourceLoadException(
				"Parsing JSON file failed.", exception);}}

	@Override
	public void unload(){
		root = null;
		super.unload();}

	@Override
	public void backup(){
		super.backup();
		root_backup = root;}
	@Override
	public void restore(){
		root = root_backup;
		root_backup = null;
		super.restore();}
	@Override
	public void clearBackup(){
		root_backup = null;
		super.clearBackup();}

	//accessor methods
	public Object getRoot(){
		return root;}
}