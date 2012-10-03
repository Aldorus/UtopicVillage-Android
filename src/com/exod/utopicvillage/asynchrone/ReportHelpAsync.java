package com.exod.utopicvillage.asynchrone;

import android.os.AsyncTask;

import com.exod.utopicvillage.activity.DetailHelpActivity;

public class ReportHelpAsync extends AsyncTask<Integer, Integer, Boolean>{
	DetailHelpActivity activity;
	public ReportHelpAsync(DetailHelpActivity activity){
		this.activity = activity;
	}
	
	@Override
	protected Boolean doInBackground(Integer... idHelps) {
		CallRestWeb.callWebService(activity,idHelps[0]+"/reportHelp");
		return true;	
	}
	
	@Override
    protected void onPostExecute(Boolean result) {
		activity.callBackReport();
    }

}
