package com.exod.utopicvillage.asynchrone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.exod.utopicvillage.activity.MapForHelpActivity;
import com.exod.utopicvillage.entity.Help;
import com.exod.utopicvillage.entity.User;

public class GetNearAskingHelpAsync extends AsyncTask<Void,Integer,Collection<Help>>{
	
	Help help;
	MapForHelpActivity activity;
	
	public GetNearAskingHelpAsync(MapForHelpActivity activity){
		this.activity = activity;
	}
	
	@Override
	protected Collection<Help> doInBackground(Void... params) {
		User user = activity.utopicVillageApplication.getStorage().getUser();
		Collection<Help> colHelpAsking = new ArrayList<Help>();
		
		String resultWebServ = CallRestWeb.callWebService(user.getId()+"/"+user.getLatitude()+"/"+user.getLongitude()+"/getNearAskingHelp");
		SimpleDateFormat spdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			JSONArray jsonArray = new JSONArray(resultWebServ);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonHelp	= jsonArray.getJSONObject(i);
				Help help = new Help();
				help.setId(jsonHelp.getInt("id"));
				help.setReproducible(false);
				help.setAmount(jsonHelp.getInt("amount"));
				try {
					help.setDate(spdate.parse(jsonHelp.getJSONObject("date").getString("date")));
				} catch (ParseException e) {
					Log.d("TAG","error lors du parsing de la date "+e);
				}
				help.setDescritpion(jsonHelp.getString("description"));
				JSONObject jsonUser = jsonHelp.getJSONObject("user");
				User userWhoAsk = new User();
				userWhoAsk.setId(jsonUser.getInt("id"));
				userWhoAsk.setAmount(jsonUser.getInt("amount"));
				userWhoAsk.setFirstname(jsonUser.getString("firstname"));
				userWhoAsk.setName(jsonUser.getString("name"));
				userWhoAsk.setLatitude(jsonUser.getDouble("latitude"));
				userWhoAsk.setLongitude(jsonUser.getDouble("longitude"));
				
				help.setUser(userWhoAsk);
				colHelpAsking.add(help);
				
				//on les ajoutes dans la hasttable de l'application
				//TODO refactoring la colleciton n'est plus utile
				activity.utopicVillageApplication.getStorage().addHelpToHashtable(help);
			}
		}catch (JSONException e) {
			return null;
		}
		return colHelpAsking;
	}
	
	@Override
	protected void onPreExecute() {
		activity.displaySpinner();
		super.onPreExecute();
	}
	
	@Override
	protected void onPostExecute(Collection<Help> result) {
		activity.displayData();
		activity.displayPin(result);
	}
}
