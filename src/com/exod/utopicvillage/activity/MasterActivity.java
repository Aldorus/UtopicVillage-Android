package com.exod.utopicvillage.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.application.UtopicVillageApplication;
import com.exod.utopicvillage.store.Storage;
import com.exod.utopicvillage.transaction.WebServiceRest;
import com.google.android.maps.MapActivity;

public class MasterActivity extends MapActivity{
	
	//on recupere les objects stocké dans l'application
	public UtopicVillageApplication utopicVillageApplication;
	protected Storage storage;
	protected WebServiceRest webService;
	
	private int idRessource;
	private boolean menu;
	private boolean header;
	
	public void onCreate(Bundle savedInstanceState, int idRessource){
		onCreate(savedInstanceState, idRessource, false, false);
	}

	public void onCreate(Bundle savedInstanceState, int idRessource, boolean menu, boolean header) {
		super.onCreate(savedInstanceState);
		
		//decaration of useful element
		utopicVillageApplication = (UtopicVillageApplication)this.getApplicationContext();
		storage = utopicVillageApplication.getStorage();
		webService = utopicVillageApplication.getWebService();
		
		//creationOfWorld(idRessource, false, false);
		this.idRessource = idRessource;
		this.menu = menu;
		this.header = header;
		
		//procede au setting de la content view
		setContentView(R.layout.main);
		creationOfWorld();
		
		//on ajoute l'activity a la pile de navigation
		utopicVillageApplication.addActivityToPile(this);
	}
	
	private void creationOfWorld(){
		//puis set le header si besoin
		LinearLayout viewGlobal = (LinearLayout) findViewById(R.id.main_linear);
		//on supprimer les elements avant d'en ajouter de nouveau
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(header){
			View theInflatedHeader = inflater.inflate(R.layout.header, null);
			viewGlobal.addView(theInflatedHeader);
		}
		
		//puis set le menu si besoin
		if(menu){
			View theInflatedMenu = inflater.inflate(R.layout.menu, null);
			viewGlobal.addView(theInflatedMenu);
		}
		
		//et enfin set la ressource contentLayout qui contient les elements spécifique à la page
		View theInflatedContent = inflater.inflate(idRessource, null);
		viewGlobal.addView(theInflatedContent);	
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
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
		Log.d("Log","opopop");
		return true;
	}
	
	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
	}
	
	//reecriture de la methode de start activity pour que ne redemarre pas une activity deja lancée
	public void startActivityClean(Intent intent) {
		if(!intent.getComponent().getShortClassName().equals("."+this.getLocalClassName())){
			startActivity(intent);
		}
	}
	
	public void displayData(){
		LinearLayout viewGlobal = (LinearLayout) findViewById(R.id.main_linear);
		//on remove le spinner
		View viewSpinner = (View) findViewById(R.id.main_spinner);
		viewGlobal.removeView(viewSpinner);
		//on display l'autre layout
		View viewContent = (View) findViewById(R.id.main_content);
		viewContent.setVisibility(View.VISIBLE);
		
	}
	
	public void displaySpinner(){
		LinearLayout viewGlobal = (LinearLayout) findViewById(R.id.main_linear);
		View viewContent = (View) findViewById(R.id.main_content);
		viewContent.setVisibility(View.GONE);
		
		//on affiche le spinner
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout theInflatedView = (LinearLayout)inflater.inflate(R.layout.spinner, null); 
		theInflatedView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		viewGlobal.addView(theInflatedView);
	}
}
