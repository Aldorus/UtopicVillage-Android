/**
 * @author Roussel Guillaume
 * @licence CC-BY-NC
 */
package com.exod.utopicvillage.activity;

import java.text.DecimalFormat;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

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
			//on redirige vers la connection
			Intent intent = new Intent(this, ConnectActivity.class);
			startActivity(intent);
			//et on tue l'activité en cours car pouvant causer des errors
			finish();
		}
	}
	
	/***DATE TIME PICKER****/
	static final int DATE_DIALOG_ID = 42;
	static final int TIME_DIALOG_ID = 12;
	static final int DATE_TIME_DIALOG_ID = 99;
	
	TextView editTextDated;
	public void doDateTimePicker(View view){
		editTextDated = (TextView)view;
		showDialog(DATE_TIME_DIALOG_ID);
	}
	public void doTimePicker(View view){
		editTextDated = (TextView)view;
		showDialog(TIME_DIALOG_ID);
	}
	public void doDatePicker(View view){
		editTextDated = (TextView)view;
		showDialog(DATE_DIALOG_ID);
	}
		
	@Override
	protected Dialog onCreateDialog(int id) {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		switch (id) {
		case DATE_DIALOG_ID:
		    // set date picker as current date
		    return new DatePickerDialog(this, datePickerListener, year, month,day);
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, timePickerListener, hour, minute, true);
		case DATE_TIME_DIALOG_ID:
			return new DatePickerDialog(this, dateTimePickerListener, year, month,day);
		}
			
			
		return null;
	}
 
	private DatePickerDialog.OnDateSetListener datePickerListener 
                = new DatePickerDialog.OnDateSetListener() {
 		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay) {
			
			// set selected date into textview
			DecimalFormat decimalFormat = new DecimalFormat("00");
			editTextDated.setText(new StringBuilder().append(decimalFormat.format(selectedDay))
			   .append("/").append(decimalFormat.format(selectedMonth + 1)).append("/").append(selectedYear)
			   .append(" "));
 		}
	};
	
	private DatePickerDialog.OnDateSetListener dateTimePickerListener 
				= new DatePickerDialog.OnDateSetListener() {
		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay) {
	
			// set selected date into textview
			DecimalFormat decimalFormat = new DecimalFormat("00");
			editTextDated.setText(new StringBuilder().append(decimalFormat.format(selectedDay))
			   .append("/").append(decimalFormat.format(selectedMonth + 1)).append("/").append(selectedYear)
			   .append(" "));
			
			doTimePicker(null);
		}
	};
	
	private OnTimeSetListener timePickerListener 
	    		= new  TimePickerDialog.OnTimeSetListener() {
		// when dialog box is closed, below method will be called.
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			//D FORmat
			DecimalFormat decimalFormat = new DecimalFormat("00");
			// set selected date into textview
			editTextDated.setText(editTextDated.getText()+" "+new StringBuilder().append(decimalFormat.format(hourOfDay))
			   .append(":").append(decimalFormat.format(minute)));		
		}
	};
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d("kill","touch");
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	    	Log.d("kill","touch back size "+((UtopicVillageApplication) getApplication()).getBufferActivity().size());
	        if(((UtopicVillageApplication) getApplication()).getBufferActivity().size()<=1){
	        	Log.d("kill","kill machine");
	        	//when is the last activity we kill the process
	        	android.os.Process.killProcess(android.os.Process.myPid());
	        }
	    }
        return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onStart() {
		((UtopicVillageApplication)getApplication()).addActivityToPile(this);
		super.onStart();
	}
	
	@Override
	protected void onDestroy() {
		((UtopicVillageApplication)getApplication()).removeActivityToPile(this);
		super.onDestroy();
	}
	
}
