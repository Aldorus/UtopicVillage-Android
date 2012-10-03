package com.exod.utopicvillage.entity;

public interface ElementListInterface {
	
	//this inteface ise an inteface for the entity of type list
	//the method on this interface must be implemented for use the ListAdaptater
	public String getLabel();
	public void setLabel(String label);
	public int getId();
	public void setId(int id);
	public String toString();
	public ElementListInterface getDefault();
}
