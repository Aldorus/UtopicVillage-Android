package com.exod.utopicvillage.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.exod.utopicvillage.R;

public class ConnectActivity extends MasterActivity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,R.layout.connect);
	}
	
	public void goConnect(View view){
		//on fait appel au webService de test de connection 
		String email = (String)((EditText)findViewById(R.id.emailField)).getText().toString();
		String password = (String)((EditText)findViewById(R.id.passwordField)).getText().toString();
		Log.d("testConnect","email : "+email+" password :"+password);
		if(webService.testConnect(email, password)){
			Intent intent= new Intent(this,YourAskingHelpActivity.class	);
			startActivity(intent);
			finish();
		}else{
			Builder alertBuilder = new AlertDialog.Builder(this);
			alertBuilder.setMessage(getResources().getString(R.string.wrongConnect));
			alertBuilder.setNeutralButton(getResources().getString(R.string.close), null);
			alertBuilder.setTitle(getResources().getString(R.string.Woops));
			alertBuilder.create().show();
		}
	}
	
	public void goRegister(View view){
		//under construct
		Intent intent= new Intent(this,UnderConstructActivity.class	);
		startActivity(intent);
	}
}
