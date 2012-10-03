package com.exod.utopicvillage.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;

public class Help extends Entity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private boolean reproducible;
	private String description;
	private int amount;
	private User user;
	private Date date;
	private Hashtable<Integer,User>hashVolunteer;
	private User participant;
		
	public Help(){
		hashVolunteer = new Hashtable<Integer, User>();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isReproducible() {
		return reproducible;
	}
	public void setReproducible(boolean reproducible) {
		this.reproducible = reproducible;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Hashtable<Integer, User> getHashVolunteer() {
		return hashVolunteer;
	}
	public void setHashVolunteer(Hashtable<Integer, User> hashVolunteer) {
		this.hashVolunteer = hashVolunteer;
	}
	public User getParticipant() {
		return participant;
	}
	public void setParticipant(User participant) {
		this.participant = participant;
	}
	public void cleanVolunteer(){
		this.hashVolunteer = new Hashtable<Integer, User>();
	}
}
