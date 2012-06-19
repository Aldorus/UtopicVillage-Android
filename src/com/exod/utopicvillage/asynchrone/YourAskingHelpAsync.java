package com.exod.utopicvillage.asynchrone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.exod.utopicvillage.activity.YourAskingHelpActivity;
import com.exod.utopicvillage.entity.Help;
import com.exod.utopicvillage.entity.User;

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
		String resultWebServ = CallRestWeb.callWebService(user.getId()+"/askingHelp");
		
		try {
			JSONObject jsonObject = (JSONObject) new JSONObject(resultWebServ);
			if(!"ok".equals(jsonObject.getString("status"))){
				return null;
			}
			String stringDateBrut = jsonObject.getJSONObject("date").getString("date");
			SimpleDateFormat spdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date dateBrut=null;
			try {
				dateBrut = spdate.parse(stringDateBrut);
			} catch (ParseException e) {
				Log.d("TAG","error lors du parsing de la date "+e);
			}
			help.setDate(dateBrut);
			help.setId(jsonObject.getInt("id"));
			help.setAmount(jsonObject.getInt("amount"));
			help.setDescritpion(jsonObject.getString("description"));
			help.setReproducible(false);
			help.setUser(user);
			
			//Set du participant si il existe
			//TODO
			if(!jsonObject.isNull("participant")){
				JSONObject jsonParticipant = jsonObject.getJSONObject("participant");
				if(jsonParticipant!=null){
					User participant = new User();
					participant.setId(jsonParticipant.getInt("id"));
					participant.setAmount(jsonParticipant.getInt("amount"));
					participant.setCommentaire(jsonParticipant.getString("commentaire"));
					participant.setFirstname(jsonParticipant.getString("firstname"));
					participant.setName(jsonParticipant.getString("name"));
					participant.setLatitude(jsonParticipant.getDouble("latitude"));
					participant.setLongitude(jsonParticipant.getDouble("longitude"));
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
