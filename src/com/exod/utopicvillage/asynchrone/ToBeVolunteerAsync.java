package com.exod.utopicvillage.asynchrone;

import android.os.AsyncTask;

import com.exod.utopicvillage.activity.DetailHelpActivity;
import com.exod.utopicvillage.entity.Help;
import com.exod.utopicvillage.entity.User;

public class ToBeVolunteerAsync extends AsyncTask<Void,Integer,Void> {
	DetailHelpActivity activity;
	Help help;
	public ToBeVolunteerAsync(DetailHelpActivity activity,Help help){
		this.activity = activity;
		this.help =help;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		User user = activity.utopicVillageApplication.getStorage().getUser();
		CallRestWeb.callWebService(user.getId()+"/"+help.getId()+"/insertNewVolunteer");
		return null;
	}
	
	@Override
    protected void onPostExecute(Void result) {
		activity.callbackVolunteer();
    }

}
