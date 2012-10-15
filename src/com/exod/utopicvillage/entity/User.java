/**
 * @author Roussel Guillaume
 * @licence CC-BY-NC
 */
package com.exod.utopicvillage.entity;

import java.util.Date;

import android.location.Location;
import android.widget.ImageView;

import com.exod.utopicvillage.R;

public class User extends Entity{
	//classe de descritpion d'un user
	private int id;
	private String name;
	private String firstname;
	private int amount;
	private ImageView avatar;
	private double latitude;
	private double longitude;
	private String commentaire;
	private Date birthdate;
	private String email;
	private String password;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public ImageView getAvatar() {
		if(avatar==null){
			avatar.setImageResource(R.drawable.ic_launcher);
		}
		return avatar;
	}
	public void setAvatar(ImageView avatar) {
		this.avatar = avatar;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setLocation(Location location){
		this.latitude = location.getLatitude();
		this.longitude = location.getLongitude();
	}
	
}
