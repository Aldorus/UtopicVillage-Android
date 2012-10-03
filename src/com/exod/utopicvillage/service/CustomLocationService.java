package com.exod.utopicvillage.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.exod.utopicvillage.alert.AlertNoGPSActivity;
import com.exod.utopicvillage.application.UtopicVillageApplication;

public class CustomLocationService extends Service implements LocationListener {
	//this class is a service who control the location manager. 
	//it can show a message when the GPS is turned off
	//and it set the latitude and the longitude of the advisor
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.d("service log","service binded");
		return null;
	}
	
	@Override
    public void onCreate() {
		LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 500.0f, this);
		
		//we test if the GPS is enbaled
		if ( !lm.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
	        buildAlertMessageNoGps();
	    }else{
			//we get the last position
			Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			
			if(location!=null){
				Log.d("service log","location changed for "+location.getLatitude()+" and "+location.getLongitude());
				//and we change the advisor of the application
				((UtopicVillageApplication)getApplication()).getStorage().getUser().setLocation(location);
			}else{
				//we set on the center of france
				((UtopicVillageApplication)getApplication()).getStorage().getUser().setLatitude(47.08);
				((UtopicVillageApplication)getApplication()).getStorage().getUser().setLongitude(2.39);
			}
	    }
    }

	@Override
	public void onLocationChanged(Location location) {
		if(location!=null){
			((UtopicVillageApplication)getApplication()).getStorage().getUser().setLocation(location);
			Log.d("service log","location changed for "+location.getLatitude()+" and "+location.getLongitude());
		}else{
			Log.d("service log","location null");
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	private void buildAlertMessageNoGps() {
		Intent intent = new Intent(this,AlertNoGPSActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    startActivity(intent);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
