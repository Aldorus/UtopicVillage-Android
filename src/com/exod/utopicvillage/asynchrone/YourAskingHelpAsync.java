package com.exod.utopicvillage.asynchrone;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.exod.utopicvillage.activity.YourAskingHelpActivity;
import com.exod.utopicvillage.entity.Help;
import com.exod.utopicvillage.entity.User;
import com.exod.utopicvillage.util.ParsingUtil;

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
			JSONObject jsonObject = (JSONObject) new JSONObject(resultWebServ);
			if(!"ok".equals(jsonObject.getString("status"))){
				return null;
			}
			help = ParsingUtil.toHelp(jsonObject);
			help.setUser(user);
			
			//Set du participant si il existe
			//TODO
			if(!jsonObject.isNull("participant")){
				JSONObject jsonParticipant = jsonObject.getJSONObject("participant");
				if(jsonParticipant!=null){
					User participant = ParsingUtil.toUser(jsonParticipant);
					help.setParticipant(participant);
				}
			}
		} catch (JSONException e) {
			Log.d("TAG","error lors du parsing JSON asking help "+e);
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
		activity.displayData();
		activity.displayInfoHelp(result);
	}
}
