package com.exod.utopicvillage.asynchrone;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.exod.utopicvillage.activity.YourAskingHelpActivity;
import com.exod.utopicvillage.application.UtopicVillageApplication;
import com.exod.utopicvillage.entity.Help;
import com.exod.utopicvillage.entity.User;
import com.exod.utopicvillage.util.JSONParser;

public class YourAskingHelpAsync extends AsyncTask<Void,Integer,Help>{
	
	Help help;
	YourAskingHelpActivity activity;
	
	public YourAskingHelpAsync(YourAskingHelpActivity activity){
		this.activity = activity;
	}
	
	@Override
	protected Help doInBackground(Void... params) {
		User user = activity.utopicVillageApplication.getStorage().getUser();
		Help help = new Help();
		String resultWebServ = null;
		resultWebServ = CallRestWeb.callWebService(activity, user.getId()+"/askingHelp");
		
		try {
			if("nook".equals(resultWebServ)){
				return null;
			}else{
				JSONObject jsonObject = (JSONObject) new JSONObject(resultWebServ);
				help = (Help)JSONParser.toEntity(jsonObject.getJSONObject("help"), new Help());
				help.setUser(user);
			
				//Set du participant si il existe
				//TODO
				if(!jsonObject.isNull("participant")){
					JSONObject jsonParticipant = jsonObject.getJSONObject("participant");
					if(jsonParticipant!=null){
						User participant = (User)JSONParser.toEntity(jsonParticipant, new User());
						help.setParticipant(participant);
					}
				}
			}
		} catch (Exception e) {
			Log.d("TAG","error lors du parsing JSON asking help "+e);
			((UtopicVillageApplication)activity.getApplication()).catchErrorServer();
			return null;
		}
		
		return help;
	}
	
	@Override
	protected void onPreExecute() {
		activity.displaySpinner();
		super.onPreExecute();
	}
	
	@Override
	protected void onPostExecute(Help result) {
		activity.displayInfoHelp(result);
		activity.displayData();
	}
}
