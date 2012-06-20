package com.exod.utopicvillage.asynchrone;

import android.os.AsyncTask;

import com.exod.utopicvillage.activity.RegisterActivity;

public class RegisterAsync extends AsyncTask<String, Integer, Boolean>{
	RegisterActivity activity;
	String email;
	String password;
	
	public RegisterAsync(RegisterActivity activity) {
		this.activity = activity;
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
		// @Route("/{password}/{birthdate}/{email}/{name}/{firstname}/{description}/insertUser",name="insertUser")
		CallRestWeb.callWebService(activity,params[0]+"/"+params[1]+"/"+params[2]+"/"+params[3]+"/"+params[4]+"/"+params[5]+"/insertUser");
		
		email = params[2];
		password = params[0];
		return true;
	}
	
	@Override
	protected void onPreExecute() {
		activity.displaySpinner();
		super.onPreExecute();
	}

	@Override
    protected void onPostExecute(Boolean result) {
		activity.callbackRegister(result,email,password);
    }
}
