package kuro.amaktet.resource.event;

public interface ResourceListener extends java.util.EventListener{
	public void resourceOutdated( ResourceEvent event);
	public void resourceUpdated( ResourceEvent event);
}
