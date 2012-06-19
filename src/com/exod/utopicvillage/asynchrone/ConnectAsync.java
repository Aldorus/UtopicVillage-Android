package com.exod.utopicvillage.asynchrone;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.exod.utopicvillage.activity.ConnectActivity;
import com.exod.utopicvillage.entity.User;

public class ConnectAsync extends AsyncTask<Void, Integer, Boolean> {

	ConnectActivity activity;
	String email,password;
	String result;
	
	public ConnectAsync(ConnectActivity activity,String email, String password){
		this.activity = activity;
		this.email = email;
		this.password = password;
	}
	
	@Override
	protected Boolean doInBackground(Void... params) {
		JSONObject jsonObject;
		String status;
		try {
			String resultWebServ = CallRestWeb.callWebService(email+"/"+password+"/testConnect");
			if(resultWebServ==null){
				return false;
			}
			jsonObject = new JSONObject(resultWebServ);
			
			status = jsonObject.getString("status");
		} catch (JSONException e) {
			return false;
		} 
		
		if("ok".equals(status)){
			//on set l'user 
			try {
				JSONObject jsonObjectUser = jsonObject.getJSONObject("user");
				User user = activity.utopicVillageApplication.getStorage().getUser();
				user.setId(jsonObjectUser.getInt("id"));
				user.setAmount(jsonObjectUser.getInt("amount"));
				user.setName(jsonObjectUser.getString("name"));
				user.setFirstname(jsonObjectUser.getString("firstname"));
				user.setLatitude(jsonObjectUser.getDouble("latitude"));
				user.setLongitude(jsonObjectUser.getDouble("longitude"));
				activity.utopicVillageApplication.getStorage().setUser(user);
			} catch (JSONException e) {
				Log.d("error","error lors du parsing "+e);
				return false;
			}
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
