package com.exod.utopicvillage.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import android.app.Activity;
import android.app.Application;

import com.exod.utopicvillage.activity.MasterActivity;
import com.exod.utopicvillage.store.Storage;
import com.exod.utopicvillage.thread.SearchNotificationPayementThread;
import com.exod.utopicvillage.transaction.WebServiceRest;

public class UtopicVillageApplication extends Application {
	Storage storage;
	WebServiceRest webService;
	Collection<Activity> pileActivity;
	int orientation;
	SearchNotificationPayementThread threadNotif;
	
	public UtopicVillageApplication() {
		storage = new Storage();
		webService = new WebServiceRest(this);
		pileActivity = new ArrayList<Activity>();
		// on lance les thread
		// Message
		// TODO
	
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

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}
	
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
	
	
	// gestion de la pile de navigation
	public void addActivityToPile(MasterActivity activity) {
		
		Boolean changementConfig=false;
		// cas d'exception pour les activity du menu
		if ("activity.YourAskingHelpActivity".equals(activity.getLocalClassName())
				|| "activity.MapForHelp".equals(activity.getLocalClassName())
				|| "activity.MonProfilActivity".equals(activity.getLocalClassName())
		){
			
			for (Iterator<Activity> iterator = pileActivity.iterator(); iterator.hasNext();) {
				Activity activityL = (Activity) iterator.next();
				// on tue toute les activity, sauf celle courante
				//si il s'agit d'un changement d'orentation le onCreate est rapplé mais l'activity ne se restart pas
				//il faut donc dans ce cas ne pas la rajouter une nouvelle fois à la pile
				//valable pour n'importe quelle changment de configuration de l'écran
				if(!activityL.getLocalClassName().equals(activity.getLocalClassName())){
					activityL.finish();
				}else{
					changementConfig=true;
				}
			}
		}
		// on ajoute la nouvelle
		if(!changementConfig){
			pileActivity.add(activity);
		}
	}
	
	public void stop(){
		for (Iterator<Activity> iterator = pileActivity.iterator(); iterator.hasNext();) {
			Activity activityL = (Activity) iterator.next();
			activityL.finish();
		}
	}
}
