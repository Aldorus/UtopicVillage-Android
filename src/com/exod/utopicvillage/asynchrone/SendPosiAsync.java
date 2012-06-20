package com.exod.utopicvillage.asynchrone;

import android.os.AsyncTask;

import com.exod.utopicvillage.application.UtopicVillageApplication;
import com.exod.utopicvillage.entity.User;

public class SendPosiAsync extends AsyncTask<Double, Integer, Void>{
	UtopicVillageApplication application;
	public SendPosiAsync(UtopicVillageApplication application) {
		this.application= application;
	}
	
	@Override
	protected Void doInBackground(Double... params) {
		//on envoye les position de l'userà un webservice qui la sauvegarde
		User user = application.getStorage().getUser();
		CallRestWeb.callWebService(user.getId()+"/"+params[0]+"/"+params[1]+"/savePosition");
		//on suppose que le webservice retourne toujours le satatus ok
		//TODO
		return null;
	}

}
