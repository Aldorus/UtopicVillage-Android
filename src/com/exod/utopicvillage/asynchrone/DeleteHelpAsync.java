package com.exod.utopicvillage.asynchrone;

import android.os.AsyncTask;

import com.exod.utopicvillage.activity.YourAskingHelpActivity;

public class DeleteHelpAsync extends AsyncTask<Integer, Integer, Void>{
	YourAskingHelpActivity activity;
	
	public DeleteHelpAsync(YourAskingHelpActivity activity) {
		this.activity = activity;
	}
	
	@Override
	protected Void doInBackground(Integer... params) {
		CallRestWeb.callWebService(activity,params[0]+"/deleteHelp");
		return null;
	}

	@Override
	protected void onPreExecute() {
		activity.displaySpinner();
		super.onPreExecute();
	}
	
	@Override
	protected void onPostExecute(Void result) {
//		activity.displayData();
		activity.callBackCancel();
	}
	
}
