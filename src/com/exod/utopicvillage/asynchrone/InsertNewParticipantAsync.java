package com.exod.utopicvillage.asynchrone;

import java.util.Hashtable;

import android.os.AsyncTask;

import com.exod.utopicvillage.activity.YourAskingHelpActivity;
import com.exod.utopicvillage.entity.Help;
import com.exod.utopicvillage.entity.User;

public class InsertNewParticipantAsync extends AsyncTask<Integer, Integer, Boolean> {
	YourAskingHelpActivity activity;
	public InsertNewParticipantAsync(YourAskingHelpActivity activity){
		this.activity = activity;
	}
	@Override
	protected Boolean doInBackground(Integer... idUsers) {
		CallRestWeb.callWebService(activity,activity.utopicVillageApplication.getStorage().getAskingHelp().getId()+"/"+idUsers[0]+"/insertParticipant");
		Help asking = activity.utopicVillageApplication.getStorage().getAskingHelp();
		Hashtable<Integer, User>hashVol = asking.getHashVolunteer();
		
		User participant = hashVol.get(idUsers[0]);
		//suppression des elements de l'application
		activity.utopicVillageApplication.getStorage().getAskingHelp().cleanVolunteer();
		//stockage du nouvel element
		activity.utopicVillageApplication.getStorage().getAskingHelp().setParticipant(participant);
		
		return true;
	}
	
	@Override
	protected void onPreExecute() {
		activity.displaySpinner();
		super.onPreExecute();
	}
	
	@Override
    protected void onPostExecute(Boolean result) {
		activity.insertedParticipant();
    }
}
