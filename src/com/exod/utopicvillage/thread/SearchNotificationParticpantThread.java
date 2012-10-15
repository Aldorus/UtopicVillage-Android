/**
 * @author Roussel Guillaume
 * @licence CC-BY-NC
 */
package com.exod.utopicvillage.thread;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.application.UtopicVillageApplication;
import com.exod.utopicvillage.asynchrone.CallRestWeb;
import com.exod.utopicvillage.entity.User;
import com.exod.utopicvillage.util.NotificationHelper;

public class SearchNotificationParticpantThread {
	UtopicVillageApplication application;
	
	boolean stopThread; 
	public SearchNotificationParticpantThread(UtopicVillageApplication application) {
		this.application = application;
		stopThread=false;
	}
	public void reactivThread(){
		stopThread=false;
	}
	
	public void startMethod(final User user){
		new Thread(new Runnable() {
			@Override
			public void run() {
				if(!stopThread){
					try {
						searchIfParticipant(user);
					} catch (ClientProtocolException e1) {
					} catch (IOException e1) {
					}
					try {
						
						Thread.sleep(6000);//60secondes
						startMethod(user);
					}catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	public void stopMethod(){
		stopThread = true;
	}
	
	protected void searchIfParticipant(User user) throws ClientProtocolException, IOException {
		String result = CallRestWeb.callWebService(application, user.getId()+"/getParticipantNotification");
		try {
			if(result!=null){
				JSONArray jsonArray = new JSONArray(result);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonUser = jsonArray.getJSONObject(i);
					NotificationHelper notification = new NotificationHelper(application);
					notification.createNotification(jsonUser.getString("name")+" "+jsonUser.getString("firstname"), application.getResources().getString(R.string.you_re_chosen));
				}
			}
		}catch (JSONException e) {
			Log.d("Error","error lors du parsin JONS "+e);
		}
	}
}
