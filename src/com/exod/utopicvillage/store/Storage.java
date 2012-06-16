package com.exod.utopicvillage.store;

import java.util.Hashtable;

import android.R.integer;
import android.app.Application;

import com.exod.utopicvillage.entity.Help;
import com.exod.utopicvillage.entity.User;


//classe de stockage 
public class Storage extends Application{
	User user;
	Help askingHelp;
	Hashtable<String, Help> anotherHelp;
	
	public Storage(){
		user = new User();
		anotherHelp = new Hashtable<String, Help>();
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Help getAskingHelp() {
		return askingHelp;
	}

	public void setAskingHelp(Help askingHelp) {
		this.askingHelp = askingHelp;
	}

	public Hashtable<String, Help> getAnotherHelp() {
		return anotherHelp;
	}

	public void setAnotherHelp(Hashtable<String, Help> anotherHelp) {
		this.anotherHelp = anotherHelp;
	}
	
	public void addHelpToHashtable(Help help){
		anotherHelp.put(help.getId()+"", help);
	}
	
	
	
}
