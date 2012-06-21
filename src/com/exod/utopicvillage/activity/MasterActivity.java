package com.exod.utopicvillage.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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
		
		//on verifie si on proviens d'une error serveur
		this.testError();
		
		//creationOfWorld(idRessource, false, false);
		this.idRessource = idRessource;
		this.menu = menu;
		this.header = header;
		
		//procede au setting de la content view
		setContentView(R.layout.main);
		creationOfWorld();		
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
	
	@Override
	public void startActivity(Intent intent) {
		//on ajoute l'activity a la pile de navigation
		super.startActivity(intent);
	}
	
	@Override
	protected void onStart() {
		utopicVillageApplication.addActivityToPile(this);
		super.onStart();
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
	
	public void testError(){
		if(utopicVillageApplication.errorServer){
			//on cas d'error d'excecution du serveur
			Builder alertBuilder = new AlertDialog.Builder(this);
			alertBuilder.setMessage(getResources().getString(R.string.connctivity_probleme));
			alertBuilder.setNeutralButton(getResources().getString(R.string.close), null);
			alertBuilder.setTitle(getResources().getString(R.string.Woops));
			alertBuilder.create().show();
			//on redirige vers la connection
			utopicVillageApplication.errorServer=false;
			Intent intent = new Intent(this, ConnectActivity.class);
			startActivity(intent);
			//et on tue l'activité en cours car pouvant causer des errors
			finish();
		}
	}
}
