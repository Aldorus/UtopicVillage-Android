package com.exod.utopicvillage.asynchrone;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.exod.utopicvillage.activity.FichePlayerActivity;
import com.exod.utopicvillage.entity.User;

public class GetUserAsync extends AsyncTask<Integer, Integer, User> {
	FichePlayerActivity activity;
	public GetUserAsync(FichePlayerActivity activity) {
		this.activity=activity;
	}
	
	@Override
	protected User doInBackground(Integer... params) {
		String result = CallRestWeb.callWebService(params[0]+"/getInfoUser");
		try {
			JSONObject jsonObject = (JSONObject) new JSONObject(result);
			User user = new User();
			user.setId(jsonObject.getInt("id"));
			user.setAmount(jsonObject.getInt("amount"));
			user.setCommentaire(jsonObject.getString("commentaire"));
			user.setFirstname(jsonObject.getString("firstname"));
			user.setName(jsonObject.getString("name"));
			user.setLatitude(jsonObject.getDouble("latitude"));
			user.setLongitude(jsonObject.getDouble("longitude"));
			return user;
		} catch (JSONException e) {
			Log.d("TAG","error lors du parsing JSON asking help "+e);
			return null;
		}
	}

	@Override
	protected void onPreExecute() {
		activity.displaySpinner();
		super.onPreExecute();
	}
	
	@Override
	protected void onPostExecute(User result) {
		activity.displayData();
		activity.displayInformation(result);
	}
}
