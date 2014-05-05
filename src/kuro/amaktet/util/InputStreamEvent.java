package kuro.amaktet.util;

//standard library imports
import java.util.EventObject;

public class InputStreamEvent extends EventObject {
	//fields
	private byte[] data;
	private Type type;

	//constructors
	public InputStreamEvent(
			Object source, byte[] data, Type type){
		super( source);
		this.data = data;
		this.type = type;}

	//accessor methods
	public byte[] getData(){
		return data;}
	public String getDataString(){
		return new String( data);}
	public Type getType(){
		return type;}

	//subclasses
	public enum Type {
		INPUT_RECEIVED}
}