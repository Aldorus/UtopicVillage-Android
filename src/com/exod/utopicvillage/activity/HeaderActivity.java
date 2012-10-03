package com.exod.utopicvillage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.application.UtopicVillageApplication;
import com.exod.utopicvillage.service.CustomLocationService;
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
		if(((UtopicVillageApplication)getApplication()).isAskGPS()){
			intentService = new Intent(this, CustomLocationService.class);
			startService(intentService);
		}
		//on active le service de messagerie
		
		
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
	
	//Gestion des evenements
	
	//Gestion des touches spé android.
	//menu option
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_option, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//selement si on est sur une activity loggé
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.logout:
	        Intent intent = new Intent(this,ConnectActivity.class);
	        utopicVillageApplication.setStorage(new Storage());
	        startActivityClean(intent);
	    	return true;
	    case R.id.me:
	        Intent intent2 = new Intent(this,MonProfilActivity.class);
	        startActivityClean(intent2);
	    	return true;
	    default:
	    	return true;
	    }
	}
	
	//menu recherche
	@Override
	public boolean onSearchRequested(){
		//TODO
		Intent intent = new Intent(this,SearchableActivity.class);
		startActivity(intent);
		return true;
	}
}
