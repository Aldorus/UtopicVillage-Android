package com.exod.utopicvillage.thread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.exod.utopicvillage.asynchrone.CallRestWeb;
import com.exod.utopicvillage.entity.User;
import com.exod.utopicvillage.util.NotificationHelper;

public class SearchNotificationPayementThread {
	Context mContexte;
	public SearchNotificationPayementThread(Context mContexte) {
		this.mContexte = mContexte;
	}
	
	public void startMethod(final User user){
		new Thread(new Runnable() {
			@Override
			public void run() {
				searchNotification(user);
				try {
					Thread.sleep(60);//60secondes
					startMethod(user);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	
	protected void searchNotification(User user) {
		String result = CallRestWeb.callWebService(user.getId()+"/getPayementNotification");
		try {
			JSONArray jsonArray = new JSONArray(result);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonUser = jsonArray.getJSONObject(i);
				NotificationHelper notification = new NotificationHelper(mContexte);
				notification.createNotification(jsonUser.getString("name")+" "+jsonUser.getString("firstname"));
			}
		}catch (JSONException e) {
			Log.d("Error","error lors du parsin JONS "+e);
		}
	}
}
