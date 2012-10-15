/**
 * @author Roussel Guillaume
 * @licence CC-BY-NC
 */
package com.exod.utopicvillage.asynchrone;

import android.os.AsyncTask;

import com.exod.utopicvillage.activity.UpdatePasswordActivity;

public class UpdatePlayerPasswordAsync extends AsyncTask<String, Integer, Boolean>{
	UpdatePasswordActivity activity;
	public UpdatePlayerPasswordAsync(UpdatePasswordActivity activity) {
		this.activity = activity;
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
		String result = CallRestWeb.callWebService(activity,params[0]+"/"+params[1]+"/updatePassword");
		if(result!=null){
			//on recharge l'utilisateur
			activity.utopicVillageApplication.getStorage().getUser().setPassword(params[1]);
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	protected void onPreExecute() {
		activity.displaySpinner();
		super.onPreExecute();
	}

	@Override
    protected void onPostExecute(Boolean result) {
		activity.backWebServ();
    }
}
