package kuro.amaktet.res;

import java.util.Collection;

public interface ResourceContainer
		extends Iterable<Resource> {
	public Collection<Resource> getResources();
}