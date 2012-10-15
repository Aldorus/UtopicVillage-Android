/**
 * @author Roussel Guillaume
 * @licence CC-BY-NC
 */
package com.exod.utopicvillage.asynchrone;

import android.os.AsyncTask;

import com.exod.utopicvillage.activity.FichePlayerActivity;

public class ReportPlayerAsync extends AsyncTask<Integer, Integer, Boolean>{
	FichePlayerActivity activity;
	public ReportPlayerAsync(FichePlayerActivity activity){
		this.activity = activity;
	}
	
	@Override
	protected Boolean doInBackground(Integer... idPlayers) {
		CallRestWeb.callWebService(activity,idPlayers[0]+"/reportPlayer");
		return true;	
	}
	
	@Override
    protected void onPostExecute(Boolean result) {
		activity.callbackReport();
    }
}
