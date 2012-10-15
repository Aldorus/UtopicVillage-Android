/**
 * @author Roussel Guillaume
 * @licence CC-BY-NC
 */
package com.exod.utopicvillage.alert;

import java.util.Iterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.application.ErrorMessage;
import com.exod.utopicvillage.application.UtopicVillageApplication;


public class AlertErrorActivity extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		buildAlert();
		 super.onCreate(savedInstanceState);
	}
	
	private void buildAlert() {
		//we remove all messages when we need to show
		StringBuffer messageError = new StringBuffer();
	    for (Iterator<ErrorMessage> iterator = ((UtopicVillageApplication)getApplication()).getColError().iterator(); iterator.hasNext();) {
			ErrorMessage errorMessage = (ErrorMessage) iterator.next();
			messageError.append(errorMessage.getMessage()+"\n"); 
		}
	    Builder alertBuilder = new AlertDialog.Builder(this);
	    alertBuilder.setMessage(messageError);
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
