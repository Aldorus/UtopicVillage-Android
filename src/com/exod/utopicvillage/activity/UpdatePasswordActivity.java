package com.exod.utopicvillage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.alert.AlertErrorWithMessage;
import com.exod.utopicvillage.application.UtopicVillageApplication;
import com.exod.utopicvillage.asynchrone.UpdatePlayerPasswordAsync;
import com.exod.utopicvillage.entity.User;
import com.exod.utopicvillage.util.StringUtil;

public class UpdatePasswordActivity extends HeaderActivity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.update_password);
		
	}
	
	public void doUpdate(View view){
		//test element in input, and creation of a StringBuffer for store the errors
		StringBuffer error = new StringBuffer();
		
		TextView oldPasswordTV = (TextView)findViewById(R.id.old_password);
		TextView newPasswordTV = (TextView)findViewById(R.id.new_password);
		TextView confirmPasswordTV = (TextView)findViewById(R.id.confirm_password);
		
		//we get the current user
		User currentUser = ((UtopicVillageApplication)getApplication()).getStorage().getUser();
		
		//we test the input element are not empty
		if(StringUtil.textViewIsEmpty(oldPasswordTV)){
			error.append(""+getResources().getString(R.string.old_password_needed)+"\n");
		}
		if(StringUtil.textViewIsEmpty(newPasswordTV)){
			error.append(""+getResources().getString(R.string.new_password_needed)+"\n");
		}
		if(StringUtil.textViewIsEmpty(confirmPasswordTV)){
			error.append(""+getResources().getString(R.string.confirm_password_needed)+"\n");
		}
		
		//first displaying
		if(error.length()>0){
			//add the dialog box with the label of the error
			Intent intent = new Intent(this,AlertErrorWithMessage.class);
			intent.putExtra("message", error+"");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		    startActivity(intent);
		}else{
			//	we continue the test
			if(!currentUser.getPassword().equals(oldPasswordTV.getText()+"")){
				error.append(""+getResources().getString(R.string.old_password_unknow)+"\n");
			}
			if(!(newPasswordTV.getText()+"").equals(confirmPasswordTV.getText()+"")){
				error.append(""+getResources().getString(R.string.match_new_confirm)+"\n");
			}
			
			if(error.length()>0){
				//if we have error
				Intent intent = new Intent(this,AlertErrorWithMessage.class);
				intent.putExtra("message", error+"");
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			    startActivity(intent);
			}else{
				//we do the modification
				UpdatePlayerPasswordAsync async = new UpdatePlayerPasswordAsync(this);
				String[]stringArg ={currentUser.getId()+"",newPasswordTV.getText()+""}; 
				async.execute(stringArg);
			}
		}
	}
	
	public void backWebServ(){
		//we display a message
		Intent intent = new Intent(this,AlertErrorWithMessage.class);
		intent.putExtra("message", getResources().getString(R.string.new_password_ok)+"");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    startActivity(intent);
		finish();
	}
}
