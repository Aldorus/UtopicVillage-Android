/**
 * @author Roussel Guillaume
 * @licence CC-BY-NC
 */
package com.exod.utopicvillage.asynchrone;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.exod.utopicvillage.activity.MapForHelpActivity;
import com.exod.utopicvillage.entity.Help;
import com.exod.utopicvillage.entity.User;
import com.exod.utopicvillage.util.ParsingUtil;

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
		
		String resultWebServ = null;
		resultWebServ = CallRestWeb.callWebService(activity,user.getId()+"/"+user.getLatitude()+"/"+user.getLongitude()+"/getNearAskingHelp");
		
		try {
			JSONObject jsonObject = new JSONObject(resultWebServ);
			JSONArray jsonArray = jsonObject.getJSONArray("helps");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonHelp	= jsonArray.getJSONObject(i);
				Help help = ParsingUtil.toHelp(jsonHelp);
				
				JSONObject jsonUser = jsonHelp.getJSONObject("user");
				User userWhoAsk = ParsingUtil.toUser(jsonUser);
				
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
