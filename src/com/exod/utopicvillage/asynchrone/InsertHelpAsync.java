package com.exod.utopicvillage.asynchrone;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.exod.utopicvillage.activity.HelpMeActivity;
import com.exod.utopicvillage.entity.Help;

public class InsertHelpAsync extends AsyncTask<Help,Integer,Boolean>{
	
	Help help;
	HelpMeActivity activity;
	
	public InsertHelpAsync(Help help,HelpMeActivity activity){
		this.help=help;
		this.activity = activity;
	}
	
	@Override
	protected Boolean doInBackground(Help... params) {
		JSONObject jsonObject;
		String status=null;
		try {
			String resultWebServ = null;
			resultWebServ = CallRestWeb.callWebService(activity,help.getUser().getId()+"/"+help.getAmount()+"/"+help.getDescritpion()+"/"+help.isReproducible()+"/insertHelp");
			if(resultWebServ==null){
				return false;
			}
			jsonObject = new JSONObject(resultWebServ);
			
			status = jsonObject.getString("status");
		} catch (JSONException e) {
			return false;
		} 
		if("ok".equals(status)){
			return true;
		}
		
		return false;
	}
	
	@Override
	protected void onPreExecute() {
		activity.displaySpinner();
		super.onPreExecute();
	}
	
	@Override
    protected void onPostExecute(Boolean result) {
		activity.callbackAsync(result);
    }

}
