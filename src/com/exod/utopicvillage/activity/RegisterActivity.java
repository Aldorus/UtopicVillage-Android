package com.exod.utopicvillage.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.asynchrone.RegisterAsync;
import com.exod.utopicvillage.util.EmailUtil;
import com.exod.utopicvillage.util.StringUtil;

public class RegisterActivity extends MasterActivity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,R.layout.register);
	}
	
	public void goRegister(View view){
		//close keyboard
		InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
	    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
		
		TextView textName = (TextView)findViewById(R.id.nameField);
		TextView textFirstname = (TextView)findViewById(R.id.firstnameField);
		TextView textBirthdate = (TextView)findViewById(R.id.birthdateField);
		TextView textEmail = (TextView)findViewById(R.id.emailField);
		TextView textPassword = (TextView)findViewById(R.id.passwordField);
		TextView textDesc = (TextView)findViewById(R.id.descField);
		
		//on teste les champs obligatoires
		if(StringUtil.isEmpty(textName.getText()+"") || StringUtil.isEmpty(textFirstname.getText()+"") || 
				StringUtil.isEmpty(textBirthdate.getText()+"") || StringUtil.isEmpty(textEmail.getText()+"") ||
				StringUtil.isEmpty(textPassword.getText()+"") ||
				!EmailUtil.checkEmail(textEmail.getText()+"")){
			//alert error
			Builder alertBuilder = new AlertDialog.Builder(this);
			alertBuilder.setMessage(getResources().getString(R.string.error_information));
			alertBuilder.setNeutralButton(getResources().getString(R.string.close), null);
			alertBuilder.setTitle(getResources().getString(R.string.Woops));
			alertBuilder.create().show();
			
		}else{
			RegisterAsync async = new RegisterAsync(this);
			//convertion de la date
			SimpleDateFormat spFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date birthdate=null;
			try {
				birthdate = spFormat.parse(textBirthdate.getText()+"");
			} catch (ParseException e) {
				Log.d("Error","Error lors du parsing de la date "+e);
			}
			SimpleDateFormat spFormatDestination = new SimpleDateFormat("yyyy-MM-dd");
			String[] stringArg= {textPassword.getText()+"",spFormatDestination.format(birthdate)+"",textEmail.getText()+"",textName.getText()+"",textFirstname.getText()+"",textDesc.getText()+""};
			async.execute(stringArg);
		}
	}
	
	public void callbackRegister(String result,String email, String password){
		if(result==null){
			//nothing, the application have already react
		}else if("ok".equals(result)){
			Intent intent = new Intent(this,ConnectActivity.class);
			intent.putExtra("email", email);
			intent.putExtra("password", password);
			
			startActivity(intent);
		}else{
			//error
			Builder alertBuilder = new AlertDialog.Builder(this);
			alertBuilder.setMessage(result);
			alertBuilder.setNeutralButton(getResources().getString(R.string.close), null);
			alertBuilder.setTitle(getResources().getString(R.string.Woops));
			alertBuilder.create().show();
		}
	}
}
