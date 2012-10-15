/**
 * @author Roussel Guillaume
 * @licence CC-BY-NC
 */
package com.exod.utopicvillage.alert;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;

import com.exod.utopicvillage.R;


public class AlertNoCompatibleApplication extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		buildAlert();
		 super.onCreate(savedInstanceState);
	}
	
	private void buildAlert() {
		Builder alertBuilder = new AlertDialog.Builder(this);
	    alertBuilder.setMessage(getResources().getString(R.string.no_compatible_application));
	    alertBuilder.setNegativeButton(this.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
	           public void onClick(final DialogInterface dialog, final int id) {
	                dialog.cancel();
	                finish();
	           }
	       });
		alertBuilder.setTitle(getResources().getString(android.R.string.dialog_alert_title));
		alertBuilder.create().show();
	}
}
