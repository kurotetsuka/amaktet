package kuro.amaktet.util;

// standard library imports
import java.util.EventListener;

public interface InputStreamListener extends EventListener{
	public void inputReceived( InputStreamEvent event);
}