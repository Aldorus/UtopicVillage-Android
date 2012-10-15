/**
 * @author Roussel Guillaume
 * @licence CC-BY-NC
 */
package com.exod.utopicvillage.application;

//this class is a container for all errors who can product be the application
public class ErrorMessage {
	private String message;

	//custructor
	public ErrorMessage(String message){
		this.message=message;
	}
	
	//GET/SET
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
