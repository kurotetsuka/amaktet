package kuro.amaktet.resource;

//standard library imports
import java.util.Vector;
import java.util.Collection;

public class ResourceList
		extends Vector<Resource>
		implements ResourceContainer {
	//fields
	public int loaded;

	//constructor
	public ResourceList(){
		this.loaded = 0;}

	//methods
	public void load(){
		for( Resource resource : this)
			if( ! resource.isLoaded())
				try {
					resource.load();
					if( resource.isLoaded())
						loaded++;}
				catch( ResourceLoadException exception){
					throw exception;}}

	public void unload(){
		for( Resource resource : this)
			if( resource.isLoaded())
				try {
					resource.unload();
					if( ! resource.isLoaded())
						loaded--;}
				catch( ResourceLoadException exception){
					throw exception;}}

	public boolean isResolved(){
		for( Resource resource : this)
			if( ! resource.isResolved())
				return false;
		return true;}

	//ResourceContainer functions
	public void setNeeded( boolean needed){
		for( Resource resource : this)
			resource.setNeeded( needed);}

	public Collection<Resource> getResources(){
		return this;}
}