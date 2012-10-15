/**
 * @author Roussel Guillaume
 * @licence CC-BY-NC
 */
package com.exod.utopicvillage.alert;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.asynchrone.NewPasswordAsync;

public class PromptForgottenPasswordAlert extends Activity {
	PromptForgottenPasswordAlert activity;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		buildAlert();
		activity = this;
		 super.onCreate(savedInstanceState);
	}
	
	private void buildAlert() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle(getResources().getString(R.string.forgot_passwordQ));
		alert.setMessage(getResources().getString(R.string.text_password));

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT); // Verbose!
		lp.setMargins(5, 0, 5, 0);
		input.setLayoutParams(lp);
		alert.setView(input);

		alert.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				Editable value = input.getText();
				//call the webservice
				NewPasswordAsync async = new NewPasswordAsync(activity);
				async.execute(value.toString());
				
				finish();
			}
		});

		alert.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
				finish();
			}
		});
		alert.show();
	}
	
	public void displayMessage(boolean result){
		Toast toast;
		if(result){
			toast = Toast.makeText(this, getResources().getString(R.string.new_forgotten_password_ok), Toast.LENGTH_SHORT);
		}else{
			toast = Toast.makeText(this, getResources().getString(R.string.email_unknow), Toast.LENGTH_SHORT);
		}
		toast.show();
	}
}
