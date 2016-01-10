package kuro.amaktet.util.event;

// standard library imports
import java.util.EventListener;

public interface CommandListener extends EventListener {
	// handles
	public void commandReceived( CommandEvent event);
}