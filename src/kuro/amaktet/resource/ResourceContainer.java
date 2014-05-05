package kuro.amaktet.resource;

import java.util.Collection;

public interface ResourceContainer
		extends Iterable<Resource> {
	public Collection<Resource> getResources();
	public void setNeeded( boolean needed);
}