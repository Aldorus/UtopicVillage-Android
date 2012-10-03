package com.exod.utopicvillage.asynchrone;

import android.os.AsyncTask;
import android.util.Log;

import com.exod.utopicvillage.activity.RegisterActivity;

public class RegisterAsync extends AsyncTask<String, Integer, String>{
	RegisterActivity activity;
	String email;
	String password;
	
	public RegisterAsync(RegisterActivity activity) {
		this.activity = activity;
	}
	
	@Override
	protected String doInBackground(String... params) {
		// @Route("/{password}/{birthdate}/{email}/{name}/{firstname}/{description}/insertUser",name="insertUser")
		String result = CallRestWeb.callWebService(activity,params[0]+"/"+params[1]+"/"+params[2]+"/"+params[3]+"/"+params[4]+"/"+params[5]+" /insertUser");
		if(result==null){
			Log.d("error","error server");
			//((UtopicVillageApplication)activity.getApplication()).catchErrorServer();
			return null;
		}else if("ok".equals(result)){
			email = params[2];
			password = params[0];
			return result;
		}else{
			//error with the information, we have a specific message from the server
			return result;
		}
	}
	
	@Override
	protected void onPreExecute() {
		activity.displaySpinner();
		super.onPreExecute();
	}

	@Override
    protected void onPostExecute(String result) {
		activity.displayData();
		activity.callbackRegister(result,email,password);
    }
}
