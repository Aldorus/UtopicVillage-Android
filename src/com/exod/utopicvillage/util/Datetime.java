/**
 * @author Roussel Guillaume
 * @licence CC-BY-NC
 */
package com.exod.utopicvillage.util;

import java.util.Date;

public class Datetime extends Date{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1286022915805658206L;

	//this class is used for by the FormUtil class
	//with that we can differentiate the date and the datetime format
	public Datetime(){
		super();
	}
	
	public Datetime(Date date){
		super(date.getTime());
	}
}
