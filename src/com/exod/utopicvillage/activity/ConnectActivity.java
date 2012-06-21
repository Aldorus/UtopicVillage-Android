package com.exod.utopicvillage.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.asynchrone.ConnectAsync;

public class ConnectActivity extends MasterActivity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,R.layout.connect);
		//on stop les services
		utopicVillageApplication.stopServiceNotification();
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			goConnect(null);
		}
		if(storage.getUser()!=null && storage.getUser().getName()!=null){
			//on provient d'une error donc on redirige vers la page suivante
			callbackAsync(true);
		}
	}
	
	public void goConnect(View view){
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
		//on fait appel au webService de test de connection 
		//appel asynchrone
		ConnectAsync connection = new ConnectAsync(this, email, password);
		connection.execute();
	}
	
	
	public void callbackAsync(boolean resultAsyncTask){
		if(resultAsyncTask){
			//on cherche les demandes d'aide ou on est participant
			webService.helpWhereYouParticipant();
			//on cherche les demandes d'aide ou on est volontaire
			webService.helpWhereYouVolunteer();
			
			Intent intent= new Intent(this,YourAskingHelpActivity.class	);
			startActivity(intent);
			
			//on demarre les services
			//notification
			utopicVillageApplication.startServiceNotification();
			finish();
		}else{
			Builder alertBuilder = new AlertDialog.Builder(this);
			alertBuilder.setMessage(getResources().getString(R.string.wrongConnect));
			alertBuilder.setNeutralButton(getResources().getString(R.string.close), null);
			alertBuilder.setTitle(getResources().getString(R.string.Woops));
			alertBuilder.create().show();
			this.displayData();
		}
	}
	
	public void goRegister(View view){
		//under construct
		Intent intent= new Intent(this,RegisterActivity.class	);
		startActivity(intent);
	}
	
}
