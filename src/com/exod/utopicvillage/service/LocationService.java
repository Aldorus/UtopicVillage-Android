/**
 * @author Roussel Guillaume
 * @licence CC-BY-NC
 */
package com.exod.utopicvillage.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.IBinder;

import com.exod.utopicvillage.application.UtopicVillageApplication;
import com.exod.utopicvillage.listener.PositionListener;

public class LocationService extends Service {

	LocationManager objgps;
	PositionListener positionListener;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	 public void onCreate(){
		objgps = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
	    positionListener = new PositionListener();
	    //on lui permet d'utiliser les element de l'application
	    positionListener.setWebService((UtopicVillageApplication) getApplicationContext());
	    objgps.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, positionListener);
	 }
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		objgps.removeUpdates(positionListener);
	}
}
