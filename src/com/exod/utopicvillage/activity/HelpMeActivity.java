/**
 * @author Roussel Guillaume
 * @licence CC-BY-NC
 */
package com.exod.utopicvillage.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.asynchrone.InsertHelpAsync;
import com.exod.utopicvillage.entity.Help;

public class HelpMeActivity extends HeaderActivity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState,R.layout.help_me);
	}
	
	public void goAskHelp(View view){
		//close keyboard
		InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
	    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
		
		Help help = new Help();
		String error = "";
		TextView labelDesc = (TextView)findViewById(R.id.descField);
		if(labelDesc!=null && !"".equals(labelDesc.getText())){
			help.setDescription(labelDesc.getText()+"");
		}else{
			error+=getResources().getString(R.string.desc_needed);
		}
		TextView labelAmount = (TextView)findViewById(R.id.amountField);
		if(labelAmount!=null && !"".equals(labelAmount.getText())){
			try{
				help.setAmount(Integer.parseInt(labelAmount.getText()+""));
				if(help.getAmount()>storage.getUser().getAmount()){
					//not_enought_money
					error+=getResources().getString(R.string.not_enought_money);;
				}
			}catch (Exception e) {
				error+=getResources().getString(R.string.amount_is_numeric);
			}
		}else{
			error+=getResources().getString(R.string.amount_needed);
		}
		
		help.setReproducible(false);
		help.setUser(storage.getUser());
		
		//if no error
		if("".equals(error)){
			//asynchrone 
			InsertHelpAsync helpAsync = new InsertHelpAsync(help, this);
			helpAsync.execute();
		}else{
			//else we alert about errors
			Builder alertBuilder = new AlertDialog.Builder(this);
			alertBuilder.setMessage(error);
			alertBuilder.setNeutralButton(getResources().getString(R.string.close), null);
			alertBuilder.setTitle(getResources().getString(R.string.Woops));
			alertBuilder.create().show();
		}
	}
	
	public void callbackAsync(Boolean result){
		if(result){
			//redirection
			Intent intent = new Intent(this,YourAskingHelpActivity.class);
			startActivity(intent);
		}else{
			Builder alertBuilder = new AlertDialog.Builder(this);
			alertBuilder.setMessage(getResources().getString(R.string.error_information));
			alertBuilder.setNeutralButton(getResources().getString(R.string.close), null);
			alertBuilder.setTitle(getResources().getString(R.string.Woops));
			alertBuilder.create().show();
		}
	}
}
