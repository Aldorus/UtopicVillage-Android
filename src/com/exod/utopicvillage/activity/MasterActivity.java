package com.exod.utopicvillage.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.application.UtopicVillageApplication;
import com.exod.utopicvillage.store.Storage;
import com.exod.utopidvillage.implement.WebServiceInterface;
import com.google.android.maps.MapActivity;

public class MasterActivity extends MapActivity{
	
	//on recupere les objects stocké dans l'application
	protected UtopicVillageApplication utopicVillageApplication;
	protected Storage storage;
	protected WebServiceInterface webService;
	
	public void onCreate(Bundle savedInstanceState, int idRessource){
		super.onCreate(savedInstanceState);
		creationOfWorld(idRessource, false, false);
	}
	public void onCreate(Bundle savedInstanceState, int idRessource, boolean menu, boolean header) {
		super.onCreate(savedInstanceState);
		creationOfWorld(idRessource, menu, header);
	}
	
	private void creationOfWorld(int idRessource, boolean menu, boolean header){
		//procede au setting de la content view
		setContentView(R.layout.main);
		//puis set le header si besoin
		LinearLayout viewGlobal = (LinearLayout) findViewById(R.id.main_linear);
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
		
		//decaration of useful element
		utopicVillageApplication = (UtopicVillageApplication)this.getApplicationContext();
		storage = utopicVillageApplication.getStorage();
		webService = utopicVillageApplication.getWebService();
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
