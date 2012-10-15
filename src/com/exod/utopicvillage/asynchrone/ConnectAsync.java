/**
 * @author Roussel Guillaume
 * @licence CC-BY-NC
 */
package com.exod.utopicvillage.asynchrone;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.exod.utopicvillage.activity.ConnectActivity;
import com.exod.utopicvillage.application.UtopicVillageApplication;
import com.exod.utopicvillage.entity.User;
import com.exod.utopicvillage.util.JSONParser;

public class ConnectAsync extends AsyncTask<Void, Integer, Integer> {

	ConnectActivity activity;
	String email,password;
	String result;
	
	public ConnectAsync(ConnectActivity activity,String email, String password){
		this.activity = activity;
		this.email = email;
		this.password = password;
	}
	
	@Override
	protected Integer doInBackground(Void... params) {
		String resultWebServ = CallRestWeb.callWebService(activity,email+"/"+password+"/testConnect");
		if(resultWebServ==null){
			return 0;
		}else if("nook".equals(resultWebServ)){
			return 2;
		}else{
			try {
				JSONObject jsonObject = new JSONObject(resultWebServ);
				activity.utopicVillageApplication.getStorage().setUser((User) JSONParser.toEntity(jsonObject.getJSONObject("user"), new User()));
			} catch (Exception e) {
				Log.d("error","error lors du parsing "+e);
				((UtopicVillageApplication)activity.getApplication()).catchErrorServer();
				return 0;
			}
			return 1;
		}
	}
	
	@Override
	protected void onPreExecute() {
		activity.displaySpinner();
		super.onPreExecute();
	}
	
	@Override
    protected void onPostExecute(Integer result) {
		activity.callbackAsync(result);
    }
}
