package com.exod.utopicvillage.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.activity.ConnectActivity;
import com.exod.utopicvillage.activity.MasterActivity;
import com.exod.utopicvillage.activity.RegisterActivity;
import com.exod.utopicvillage.activity.YourAskingHelpActivity;
import com.exod.utopicvillage.alert.AlertErrorActivity;
import com.exod.utopicvillage.alert.AlertErrorWS;
import com.exod.utopicvillage.store.Storage;
import com.exod.utopicvillage.thread.SearchNotificationParticpantThread;
import com.exod.utopicvillage.thread.SearchNotificationPayementThread;
import com.exod.utopicvillage.transaction.WebServiceRest;

public class UtopicVillageApplication extends Application {
	Storage storage;
	WebServiceRest webService;
	
	SearchNotificationPayementThread threadNotif;
	SearchNotificationParticpantThread threadNotifParticipant;
	public boolean errorServer;
	private List<ErrorMessage> colError;
	private List<Activity> bufferActivity;
	
	private boolean askGPS;
	
	//NOT CONNECTED MODE
	private StorageRequest storageRequest;
	
	//INIT
	public UtopicVillageApplication() {
		init();
	}
	
	public void init(){
		storage = new Storage();
		webService = new WebServiceRest(this);
		bufferActivity = new ArrayList<Activity>();
		colError = new ArrayList<ErrorMessage>();
		askGPS = true;
	}

	//GET -SET 
	public boolean isAskGPS() {
		return askGPS;
	}
	public void setAskGPS(boolean askGPS) {
		this.askGPS = askGPS;
	}
	public Storage getStorage() {
		return storage;
	}
	public void setStorage(Storage storage) {
		this.storage = storage;
	}
	public WebServiceRest getWebService() {
		return webService;
	}
	public void setWebService(WebServiceRest webService) {
		this.webService = webService;
	}
	public Collection<ErrorMessage> getColError() {
 		return colError;
	}
	public void setColError(List<ErrorMessage> colError) {
		this.colError = colError;
	}
	public StorageRequest getStorageRequest() {
		return storageRequest;
	}
	public void setStorageRequest(StorageRequest storageRequest) {
		this.storageRequest = storageRequest;
	}
	public List<Activity> getBufferActivity() {
		return bufferActivity;
	}
	public void setBufferActivity(List<Activity> bufferActivity) {
		this.bufferActivity = bufferActivity;
	}

	/*Methodes */
	public void startServiceNotification(){
		// notification
		//search notifications
		if(storage.getUser()!=null){
			threadNotif = new SearchNotificationPayementThread(this);
			threadNotif.reactivThread();
			threadNotif.startMethod(storage.getUser());
		}
	}
	public void stopServiceNotification(){
		if(threadNotif!=null){
			threadNotif.stopMethod();
		}
	}
	public void startServiceNotificationParticipant(){
		// notification
		//search notifications
		if(storage.getUser()!=null){
			threadNotifParticipant = new SearchNotificationParticpantThread(this);
			threadNotifParticipant.reactivThread();
			threadNotifParticipant.startMethod(storage.getUser());
		}
	}
	public void stopServiceNotificationParticipant(){
		if(threadNotifParticipant!=null){
			threadNotifParticipant.stopMethod();
		}
	}
	
	
	// gestion de la pile de navigation
	public void addActivityToPile(MasterActivity activity) {
		// cas d'exception pour les activity du menu
		if ("activity.YourAskingHelpActivity".equals(activity.getLocalClassName())
				|| "activity.MapForHelpActivity".equals(activity.getLocalClassName())
				|| "activity.MonProfilActivity".equals(activity.getLocalClassName())
		){
			List<Activity>copyBuffer = new ArrayList<Activity>(bufferActivity);
			for (Iterator<Activity> iterator = copyBuffer.iterator(); iterator.hasNext();) {
				Activity activityL = (Activity) iterator.next();
				if(!activity.getLocalClassName().equals(activityL.getLocalClassName())){
					activityL.finish();
				}
			}
		}
		bufferActivity.add(activity);
	}
	
	public void removeActivityToPile(MasterActivity activity){
		bufferActivity.remove(activity);
	}
	
	public void stop(){
		for (Iterator<Activity> iterator = bufferActivity.iterator(); iterator.hasNext();) {
			Activity activityL = (Activity) iterator.next();
			activityL.finish();
		}
		bufferActivity = new ArrayList<Activity>();
	}
	
	/********************************
	 * METHODES
	 *******************************/
	
	//this method is called after the method FormUtil.formToEntity()
	//when there are required elements
	//show an alert view the traducted message error
	public void alertAboutError(MasterActivity activity){
		if(!this.colError.isEmpty() && this.colError.size()>0){
			Intent intent = new Intent(this,AlertErrorActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		    startActivity(intent);
		}
	}

	public void addErrorMessage(ErrorMessage error){
		this.colError.add(error);
	}

	//this method it used when there an UNKNOW error server
	synchronized public void catchErrorServer(){
		catchErrorServer(getResources().getString(R.string.server_error));
	}
	
	//this method is called when the server have an 500 or 404 error
	//it close the last activity ( who created the error )
	//and it display a dialog box with the message
	synchronized public void catchErrorServer(String message){
		//if there are a server error
		//we reset the application
		//on close the last activity
		if(bufferActivity.size()>0){
			Activity activityL = bufferActivity.get(bufferActivity.size()-1);
			if(!YourAskingHelpActivity.class.getCanonicalName().equals(activityL.getClass().getCanonicalName()) && 
					!ConnectActivity.class.getCanonicalName().equals(activityL.getClass().getCanonicalName()) && 
					!RegisterActivity.class.getCanonicalName().equals(activityL.getClass().getCanonicalName())){
				activityL.finish();
				bufferActivity.remove(activityL);
			}
		}
		//add the dialog box with the label of the error
		Intent intent = new Intent(this,AlertErrorWS.class);
		intent.putExtra("message", message+"");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    startActivity(intent);
	}
}
