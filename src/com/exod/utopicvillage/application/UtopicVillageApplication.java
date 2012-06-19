package com.exod.utopicvillage.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.exod.utopicvillage.activity.MasterActivity;
import com.exod.utopicvillage.store.Storage;
import com.exod.utopicvillage.thread.SearchNotificationPayementThread;
import com.exod.utopicvillage.transaction.WebServiceRest;

public class UtopicVillageApplication extends Application {
	Storage storage;
	WebServiceRest webService;
	Collection<Activity> pileActivity;
	int orientation;
	
	
	public UtopicVillageApplication() {
		storage = new Storage();
		webService = new WebServiceRest(this);
		pileActivity = new ArrayList<Activity>();
		// on lance les thread
		// Message
		// TODO
	
		// notification
		//search notifications
		if(storage.getUser()!=null){
			SearchNotificationPayementThread thread = new SearchNotificationPayementThread(this);
			thread.startMethod(storage.getUser());
		}
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
	
	// gestion de la pile de navigation
	public void addActivityToPile(MasterActivity activity) {
		// gestion de l'empilement
		// cas d'exception pour les activity du menu
		Log.d("ActivityName", activity.getLocalClassName());
		if ("activity.YourAskingHelpActivity".equals(activity
				.getLocalClassName())
				|| "activity.MapForHelp".equals(activity.getLocalClassName())
				|| "activity.MonProfilActivity".equals(activity
						.getLocalClassName())) {
			for (Iterator<Activity> iterator = pileActivity.iterator(); iterator
					.hasNext();) {
				Activity activityL = (Activity) iterator.next();
				// on tue toute les activity
				activityL.finish();
			}
		}
		// on ajoute la nouvelle
		pileActivity.add(activity);
	}
}
