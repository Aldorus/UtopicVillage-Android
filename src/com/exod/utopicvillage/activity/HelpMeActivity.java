package com.exod.utopicvillage.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.entity.Help;

public class HelpMeActivity extends HeaderActivity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState,R.layout.help_me);
	}
	
	public void goAskHelp(View view){
		Help help = new Help();
		boolean error = false;
		TextView labelDesc = (TextView)findViewById(R.id.descField);
		if(labelDesc!=null && !"".equals(labelDesc.getText())){
			help.setDescritpion(labelDesc.getText()+"");
		}else{
			error=true;
		}
		TextView labelAmount = (TextView)findViewById(R.id.amountField);
		if(labelAmount!=null && !"".equals(labelAmount.getText())){
			try{
				help.setAmount(Integer.parseInt(labelAmount.getText()+""));
				if(help.getAmount()>storage.getUser().getAmount()){
					//not_enought_money
					error=true;
				}
			}catch (Exception e) {
				error=true;
			}
		}else{
			error=true;
		}
		help.setReproducible(false);
		help.setUser(storage.getUser());
		
		if(!error){
			webService.insertHelp(help);
			//redirection
			Intent intent = new Intent(this,YourAskingHelpActivity.class);
			startActivity(intent);
			
		}else{
			//alert error
			Builder alertBuilder = new AlertDialog.Builder(this);
			alertBuilder.setMessage(getResources().getString(R.string.error_information));
			alertBuilder.setNeutralButton(getResources().getString(R.string.close), null);
			alertBuilder.setTitle(getResources().getString(R.string.Woops));
			alertBuilder.create().show();
		}
	}
}
