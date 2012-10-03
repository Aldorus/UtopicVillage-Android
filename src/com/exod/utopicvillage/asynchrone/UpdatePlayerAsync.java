package com.exod.utopicvillage.asynchrone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.AsyncTask;
import android.util.Log;

import com.exod.utopicvillage.activity.ModifProfilActivity;

public class UpdatePlayerAsync extends AsyncTask<String, Integer, Boolean>{
	ModifProfilActivity activity;
	String email;
	String password;
	
	public UpdatePlayerAsync(ModifProfilActivity activity) {
		this.activity = activity;
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
		// @Route("/{idUser}/{birthdate}/{email}/{name}/{firstname}/{description}/insertUser",name="updateUser")
		CallRestWeb.callWebService(activity,params[0]+"/"+params[1]+"/"+params[2]+"/"+params[3]+"/"+params[4]+"/"+params[5]+"/updateUser");
		//on recharge l'utilisateur
		activity.utopicVillageApplication.getStorage().getUser().setCommentaire(params[5]);
		activity.utopicVillageApplication.getStorage().getUser().setFirstname(params[4]);
		activity.utopicVillageApplication.getStorage().getUser().setName(params[3]);
		activity.utopicVillageApplication.getStorage().getUser().setEmail(params[2]);
		//on format la date
		SimpleDateFormat spFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date birthdate=null;
		try {
			birthdate = spFormat.parse(params[1]);
		} catch (ParseException e) {
			Log.d("error","erreur lors du parsing de la date");
		}
		activity.utopicVillageApplication.getStorage().getUser().setBirthdate(birthdate);
		
		return true;
	}
	
	@Override
	protected void onPreExecute() {
		activity.displaySpinner();
		super.onPreExecute();
	}

	@Override
    protected void onPostExecute(Boolean result) {
		activity.callBackModif(result);
    }
}
