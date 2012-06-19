package com.exod.utopicvillage.asynchrone;

import com.exod.utopicvillage.activity.YourAskingHelpActivity;

import android.os.AsyncTask;

public class PayerAskingHelpAsync extends AsyncTask<Void, Integer, Void>{
	YourAskingHelpActivity activity;
	
	public PayerAskingHelpAsync(YourAskingHelpActivity activity) {
		this.activity = activity;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		CallRestWeb.callWebService(activity.utopicVillageApplication.getStorage().getAskingHelp().getId()+"/pay");
		return null;
	}

	@Override
	protected void onPreExecute() {
		activity.displaySpinner();
		super.onPreExecute();
	}

	@Override
    protected void onPostExecute(Void result) {
		activity.callBackPayement();
    }
}
