package com.exod.utopicvillage.alert;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.application.UtopicVillageApplication;


public class AlertErrorWithMessage extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		String message="";
		if(getIntent().getExtras()!=null && !"".equals(getIntent().getExtras().getString("status"))){
			message = getIntent().getExtras().getString("message");
		}
		
		buildAlert(message);
		 super.onCreate(savedInstanceState);
	}
	
	private void buildAlert(String message) {
		//we remove all messages when we need to show
		Builder alertBuilder = new AlertDialog.Builder(this);
	    alertBuilder.setMessage(message);
	    alertBuilder.setNegativeButton(this.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
	           public void onClick(final DialogInterface dialog, final int id) {
	                dialog.cancel();
	                finish();
	           }
	       });
		alertBuilder.setTitle(getResources().getString(android.R.string.dialog_alert_title));
		alertBuilder.create().show();
		
	    ((UtopicVillageApplication)getApplication()).getColError().removeAll(((UtopicVillageApplication)getApplication()).getColError());
	}
}
