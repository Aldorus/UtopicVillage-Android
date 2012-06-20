package com.exod.utopicvillage.asynchrone;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.exod.utopicvillage.activity.FichePlayerActivity;
import com.exod.utopicvillage.entity.User;
import com.exod.utopicvillage.util.ParsingUtil;

public class GetUserAsync extends AsyncTask<Integer, Integer, User> {
	FichePlayerActivity activity;
	public GetUserAsync(FichePlayerActivity activity) {
		this.activity=activity;
	}
	
	@Override
	protected User doInBackground(Integer... params) {
		String result = null;
		result = CallRestWeb.callWebService(activity,params[0]+"/getInfoUser");
		try {
			JSONObject jsonObject = (JSONObject) new JSONObject(result);
			return ParsingUtil.toUser(jsonObject);
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
