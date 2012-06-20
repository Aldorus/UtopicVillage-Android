package com.exod.utopicvillage.asynchrone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.exod.utopicvillage.activity.YourAskingHelpActivity;
import com.exod.utopicvillage.entity.Help;
import com.exod.utopicvillage.entity.User;

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
			String result = CallRestWeb.callWebService(askingHelp.getId()+"/getVolunteer");
			try {
				JSONArray jsonArray = new JSONArray(result);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonUser = jsonArray.getJSONObject(i);
					User volunteer = new User();
					volunteer.setId(jsonUser.getInt("id"));
					volunteer.setAmount(jsonUser.getInt("amount"));
					volunteer.setCommentaire(jsonUser.getString("commentaire")+"");
					volunteer.setFirstname(jsonUser.getString("firstname"));
					volunteer.setName(jsonUser.getString("name"));
					volunteer.setLatitude(jsonUser.getDouble("latitude"));
					volunteer.setLongitude(jsonUser.getDouble("longitude"));
					askingHelp.getHashVolunteer().put(new Integer((int) volunteer.getId()), volunteer);
				}
				
			} catch (JSONException e) {
				Log.d("Error","error lors du parsin JONS "+e);
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
