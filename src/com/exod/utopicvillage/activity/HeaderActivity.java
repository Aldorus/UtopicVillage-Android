package com.exod.utopicvillage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.service.LocationService;
import com.exod.utopicvillage.store.Storage;

public class HeaderActivity extends MasterActivity{
	Intent intentService; 
	public void onCreate(Bundle savedInstanceState, int idRessource) {
		super.onCreate(savedInstanceState,idRessource,false,true);
		//on initialise les elements du header
		setHeader();
	}
	
	public void onCreate(Bundle savedInstanceState, int idRessource, boolean menu) {
		//on vient d'une activity fille
		super.onCreate(savedInstanceState,idRessource,true,true);
		//on initialise les elements du header
		setHeader();
	}

	protected void setHeader(){
		//si l'user est null on amorce la déconnection
		if(storage.getUser()==null || storage.getUser().getFirstname()==null){
			Intent intent = new Intent(this,ConnectActivity.class);
	        utopicVillageApplication.setStorage(new Storage());
	        startActivity(intent);
	        //evite les problèmes après avoir eu un bug
		}
		
		//on active le service de geolocalisation
		intentService = new Intent(this, LocationService.class);
		startService(intentService);
		
		
		//set header, alays displayed
		TextView labelUser = (TextView)findViewById(R.id.label_user);
		if(labelUser != null){
			labelUser.setText(storage.getUser().getName()+" "+storage.getUser().getFirstname());
		}
		
		TextView labelAmount = (TextView)findViewById(R.id.label_amount);
		if(labelAmount!=null){
			labelAmount.setText(storage.getUser().getAmount()+" points");
		}
	}
	
	@Override
	protected void onPause() {
		if(intentService!=null){
			stopService(intentService);
		}
		super.onPause();
	}
}
