package kuro.amaktet.util;
// standard library imports
import java.io.OutputStream;

public class NullOutputStream extends OutputStream {
	public void write( int b){}
}