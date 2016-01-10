package kuro.amaktet.res.event;

public class ResourceEvent extends java.util.EventObject {
	private Type type;

	public ResourceEvent( Object source, Type type){
		super( source);
		this.type = type;}

	// subclasses
	public enum Type {
		OUTDATED, UPDATED
	}
}