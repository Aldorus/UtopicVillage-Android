/**
 * @author Roussel Guillaume
 * @licence CC-BY-NC
 */
package com.exod.utopicvillage.asynchrone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.exod.utopicvillage.activity.YourAskingHelpActivity;
import com.exod.utopicvillage.entity.Help;
import com.exod.utopicvillage.entity.User;
import com.exod.utopicvillage.util.ParsingUtil;

public class GetVolunteerAsync extends AsyncTask<Void, Integer, Boolean>{
	YourAskingHelpActivity activity;
	
	public GetVolunteerAsync(YourAskingHelpActivity activity){
		this.activity = activity;
	}
	
	@Override
	protected Boolean doInBackground(Void... params) {
		//recuperation de la demande d'aide
		Help askingHelp = activity.utopicVillageApplication.getStorage().getAskingHelp();
		if(askingHelp!=null){
			String result = null;
			result = CallRestWeb.callWebService(activity,askingHelp.getId()+"/getVolunteer");
			if(result!=null){
				try {
					JSONObject jsonObject = new JSONObject(result);
					JSONArray jsonArray = jsonObject.getJSONArray("volunteers");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonUser = jsonArray.getJSONObject(i);
						User volunteer = ParsingUtil.toUser(jsonUser);
						askingHelp.getHashVolunteer().put(new Integer((int) volunteer.getId()), volunteer);
					}
				} catch (JSONException e) {
					Log.d("Error","error lors du parsing JSON "+e);
				}
			}else{
				return null;
			}
		}
		return true;
	}
	@Override
	protected void onPreExecute() {
		activity.displaySpinner();
		super.onPreExecute();
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		activity.displayData();
		activity.setListeOfVolunteer();
	}
}
