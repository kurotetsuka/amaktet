package kuro.amaktet;

//standard library imports
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Vector;

//local imports
import kuro.amaktet.resource.*;
import kuro.amaktet.resource.event.*;

public class ResourceManager implements ResourceListener {
	//private fields
	private Vector<Path> paths;
	private ResourceList resources;
	private boolean debug = false;

	//constructor
	public ResourceManager(){
		paths = new Vector<Path>();
		//find possible paths
		Path classloc = new File( getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).toPath();
		Path pwd = new File(
			System.getProperty("user.dir")).toPath();
		//add paths
		paths.add( pwd);
		paths.add( classloc.getParent());
		if( debug)
			for( Path path : paths)
				System.out.println( path);

		//other fields
		resources = new ResourceList();}

	//functions
	public void addResourcePath( Path path){
		paths.add( path);}

	public void addResource( Resource resource){
		resources.add( resource);}
	public void addResources( Iterable<Resource> container){
		for( Resource resource : container)
			this.addResource( resource);}

	public File locate( String filename){
		for( Path path : paths){
			File attempt = path.resolve( filename).toFile();
			if( attempt.exists()) return attempt;}
		return null;}

	public void resolve( Resource resource)
			throws FileNotFoundException {
		//try to locate file
		String relative_path = resource.getRelativePath();
		File resolved_path = locate( relative_path);
		//did we find it?
		if( resolved_path == null)
			throw new FileNotFoundException(
				String.format(
					"File %s is not relative to any known resource paths.",
					relative_path));
		else
			resource.setResolvedPath( resolved_path);}


	public void resolve( Iterable<Resource> resources){
		for( Resource resource : resources)
			try{ resolve( resource);}
			catch( FileNotFoundException exception){}}

	public void load(){
		for( Resource resource : resources){
			if( resource.isNeeded()){
				if( ! resource.isResolved())
					try{ resolve( resource);}
					catch( FileNotFoundException exception){
						throw new RuntimeException(
							"Loading of needed resource failed.", exception);}
				if( ! resource.isLoaded())
					try{ resource.load();}
					catch( ResourceLoadException exception){
						throw new RuntimeException(
							"Loading of needed resource failed.", exception);}
				else if( resource.isDynamic() && resource.isOutdated())
					try{ resource.reload( true);}
					catch( ResourceLoadException exception){
						throw new RuntimeException(
							"Reloading of needed dynamic resource failed.",
							exception);}}
			else
				if( resource.isLoaded())
					resource.unload();}}

	//resource listener functions
	public void resourceOutdated( ResourceEvent event){}
	public void resourceUpdated( ResourceEvent event){}
}