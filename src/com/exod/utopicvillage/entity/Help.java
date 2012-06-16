package com.exod.utopicvillage.entity;

import java.util.Date;

public class Help {
	private int id;
	private boolean reproducible;
	private String descritpion;
	private int amount;
	private User user;
	private Date date;
	
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
	public String getDescritpion() {
		return descritpion;
	}
	public void setDescritpion(String descritpion) {
		this.descritpion = descritpion;
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
	
	
}
