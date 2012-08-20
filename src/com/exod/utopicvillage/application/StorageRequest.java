package com.exod.utopicvillage.application;

import java.io.Serializable;
import java.util.Hashtable;

import android.net.Uri;

import com.exod.utopicvillage.asynchrone.GetStorageStateAsync;

public class StorageRequest implements Serializable{
	//this class is used for demo
	//the link to the project is in CallRestWeb in the method isOnline
	//if the application cannot be connected to the www we use the element in the hashTable
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Hashtable<String, String> hashTableRequest;
	
	//this class can be saved on the device
	//and if in constructor we get the serialized informations
	
	public StorageRequest(UtopicVillageApplication application){
		hashTableRequest = new Hashtable<String, String>();
		//we get the information from the device
		GetStorageStateAsync async = new GetStorageStateAsync();
		async.execute(application);
	}
	
	public void putResponse(String url, String response){
		hashTableRequest.put(getTheActionParameter(url), response);	
	}
	
	public String getResponse(String url){
		return hashTableRequest.get(getTheActionParameter(url));
	}
	
	public Hashtable<String, String>getHashTable(){
		return hashTableRequest;
	}
	
	public void setHashTable(Hashtable<String, String>hashTableRequest){
		this.hashTableRequest = hashTableRequest;
	}
	
	private String getTheActionParameter(String url){
		Uri urlParsed = Uri.parse(url);
		return urlParsed.getQueryParameter("action");		
	}
	
	
}
