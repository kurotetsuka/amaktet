package kuro.amaktet.res;

// standard library imports
import java.io.File;
import java.util.Vector;

// local imports
import kuro.amaktet.res.event.*;

public abstract class Resource {
	// Public Fields
	private String relative_path;
	private File resolved_path;
	// private fields
	private Vector<ResourceListener> listeners;
	private String extension;
	private String extension_full;
	private boolean dynamic;
	private boolean needed;
	private boolean loaded;
	private long loadedTime;

	// Constructors
	protected Resource( String relative_path){
		this.relative_path = relative_path;
		this.listeners = new Vector<ResourceListener>();
		this.extension = getExtension( relative_path);
		this.extension_full = getFullExtension( relative_path);
		this.resolved_path = null;
		this.dynamic = false;
		this.needed = false;
		this.loaded = false;
		this.loadedTime = 0;}

	// public methods
	public void load(){
		if( ! this.isResolved())
			throw new ResourceLoadException(
				"Tried to load resource before it has been resolved");}

	public void unload(){
		setLoaded( false);}

	// public abstract methods
	public void reload(){
		this.reload( false);}
	public void reload( boolean conservative){
		if( conservative) backup();
		unload();
		load();
		if( conservative){
			if( ! this.isLoaded())
				restore();
			else
				clearBackup();}}

	public abstract void backup();
	public abstract void restore();
	public abstract void clearBackup();

	// event methods
	public void addResourceListener( ResourceListener listener){
		listeners.add( listener);}
	public void invokeResourceEvent( ResourceEvent.Type type){
		// check if there's nothing to do
		if( listeners.size() == 0) return;
		ResourceEvent event = new ResourceEvent( this, type);
		for( ResourceListener listener : listeners)
			switch( type){
				case OUTDATED:
					listener.resourceOutdated( event);
					break;}}

	// main accessor functions
	public String getRelativePath(){
		return relative_path;}

	public File getResolvedPath(){
		return resolved_path;}
	public void setResolvedPath( File file){
		if( this.isLoaded() && file == null)
			throw new ResourceLoadException(
				"Cannot change resolved path while resource is loaded");
		resolved_path = file;}

	// other accessor functions
	public boolean isDynamic(){
		return dynamic;}
	public void setDynamic( boolean dynamic){
		this.dynamic = dynamic;}

	public boolean isLoaded(){
		return loaded;}
	protected void setLoaded( boolean loaded){
		this.loaded = loaded;
		this.loadedTime = loaded ?
			resolved_path.lastModified() : 0;}

	public boolean isNeeded(){
		return needed;}
	public void setNeeded( boolean needed){
		this.needed = needed;}

	public boolean isOutdated(){
		if( ! loaded) return false;
		return loadedTime != resolved_path.lastModified();}

	public boolean isResolved(){
		return null != resolved_path;}

	public String getExtension(){
		return extension;}
	public String getFullExtension(){
		return extension_full;}

	// private utility functions
	private String getExtension( String path){
		int dot_i = path.lastIndexOf( '.');
		return dot_i >= 0 ?
			path.substring( dot_i + 1) :
			null;}
	private String getFullExtension( String path){
		int slash_i = path.lastIndexOf( '/');
		int dot_i = path.indexOf( '.', slash_i);
		return dot_i >= 0 ?
			path.substring( dot_i + 1) :
			null;}
}