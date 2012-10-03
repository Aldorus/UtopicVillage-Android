package com.exod.utopicvillage.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.alert.PromptForgottenPasswordAlert;
import com.exod.utopicvillage.asynchrone.ConnectAsync;

public class ConnectActivity extends MasterActivity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,R.layout.connect);
		
		//on stop les services
		
		//utopicVillageApplication.stopServiceNotification();
		//utopicVillageApplication.stopServiceNotificationParticipant();
		
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			goConnect(null);
		}
	}
	
	public void goConnect(View view){
		//close keyboard
		InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
	    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
	    
		String email = "";
		String password = "";
		//si on viens de l'enregistrement
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			email = bundle.getString("email");
			password = bundle.getString("password");
		}else{
			//on recupere les parametres sur la vue
			email = (String)((EditText)findViewById(R.id.emailField)).getText().toString();
			password = (String)((EditText)findViewById(R.id.passwordField)).getText().toString();
		}
		
		if(email==null || password==null || "".equals(email) || "".equals(password)){
			buildAlertMessage();
		}else{
			//on fait appel au webService de test de connection 
			//appel asynchrone
			ConnectAsync connection = new ConnectAsync(this, email, password);
			connection.execute();
		}
	}
	
	
	public void callbackAsync(int resultAsyncTask){
		if(resultAsyncTask==1){
			//on cherche les demandes d'aide ou on est participant
			webService.helpWhereYouParticipant();
			//on cherche les demandes d'aide ou on est volontaire
			webService.helpWhereYouVolunteer();
			
			Intent intent= new Intent(this,YourAskingHelpActivity.class	);
			startActivity(intent);
			
			//on demarre les services notification
			//utopicVillageApplication.startServiceNotification();
			//utopicVillageApplication.startServiceNotificationParticipant();
			
			finish();
		}else if(resultAsyncTask==2){
			displayData();
			buildAlertMessage();
		}
	}
	
	public void goRegister(View view){
		//under construct
		Intent intent= new Intent(this,RegisterActivity.class);
		startActivity(intent);
	}
	
	public void buildAlertMessage(){
		Builder alertBuilder = new AlertDialog.Builder(this);
		alertBuilder.setMessage(getResources().getString(R.string.wrongConnect));
		alertBuilder.setNeutralButton(getResources().getString(R.string.close), null);
		alertBuilder.setTitle(getResources().getString(R.string.Woops));
		alertBuilder.create().show();
	}
	
	public void goForgotPassword(View view){
		//builder prompter
		Intent intent = new Intent(this, PromptForgottenPasswordAlert.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    startActivity(intent);
	}
}
