package com.exod.utopicvillage.asynchrone;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.os.AsyncTask;
import android.util.Log;

import com.exod.utopicvillage.alert.PromptForgottenPasswordAlert;
import com.exod.utopicvillage.application.UtopicVillageApplication;

public class NewPasswordAsync extends AsyncTask<String, Integer, Boolean>{
	PromptForgottenPasswordAlert activity;
	
	public NewPasswordAsync(PromptForgottenPasswordAlert activity){
		this.activity = activity;
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
		String email = params[0];
		String result="";
		try {
			result = CallRestWeb.callWebService((UtopicVillageApplication)activity.getApplication(),URLEncoder.encode(email, "UTF-8")+"/forgottenPassword");
		} catch (UnsupportedEncodingException e) {
			return false;
		}
		if("ok".equals(result)){
			return true;
		}
		return false;
	}
		
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		//callback method
		activity.displayMessage(result);
	}
}
