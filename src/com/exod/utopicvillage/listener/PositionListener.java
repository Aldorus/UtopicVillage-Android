package com.exod.utopicvillage.listener;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.exod.utopicvillage.application.UtopicVillageApplication;
import com.exod.utopicvillage.entity.User;

public class PositionListener implements LocationListener{
	double latitude;
	double longitude;
	
	UtopicVillageApplication application;
	
	public void setWebService(UtopicVillageApplication application){
		this.application = application;
	}
	
	@Override
	public void onLocationChanged(Location location) {
		
		if(application.getWebService()!=null){
			//si les webservice sont disponible
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			application.getWebService().setLatitudeLongitude(latitude, longitude);
			
			//on set l'user en memoire
			User user = application.getStorage().getUser();
			user.setLatitude(latitude);
			user.setLongitude(longitude);
			application.getStorage().setUser(user);
			
		}
		//sinon on ne fait rien
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

}
