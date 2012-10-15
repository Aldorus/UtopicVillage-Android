/**
 * @author Roussel Guillaume
 * @licence CC-BY-NC
 */
package com.exod.utopicvillage.alert;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.application.UtopicVillageApplication;
import com.exod.utopicvillage.service.CustomLocationService;


public class AlertNoGPSActivity extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		buildAlertMessageNoGps();
		 super.onCreate(savedInstanceState);
	}
	
	private void buildAlertMessageNoGps() {
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setMessage(this.getResources().getString(R.string.GPS_enabled))
	       .setCancelable(false)
	       .setPositiveButton(this.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
	    	   public void onClick(final DialogInterface dialog, final int id) {
	    		   Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	    		   startActivity(callGPSSettingIntent);
	    		   finish();
	           }
	       })
	       .setNegativeButton(this.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
	           public void onClick(final DialogInterface dialog, final int id) {
	                dialog.cancel();
	                finish();
	                //we stop the service too
	                stopService(new Intent(getApplicationContext(),CustomLocationService.class));
	                ((UtopicVillageApplication)getApplication()).setAskGPS(false);
	           }
	       });
	    AlertDialog alert = builder.create();
	    alert.show();
	}
}
