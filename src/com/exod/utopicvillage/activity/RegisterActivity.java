package com.exod.utopicvillage.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
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
		TextView textName = (TextView)findViewById(R.id.nameField);
		TextView textFirstname = (TextView)findViewById(R.id.firstnameField);
		TextView textBirthdate = (TextView)findViewById(R.id.birthdateField);
		TextView textEmail = (TextView)findViewById(R.id.emailField);
		TextView textPassword = (TextView)findViewById(R.id.passwordField);
		TextView textDesc = (TextView)findViewById(R.id.descField);
		
		//on test les champs obligatoires
		Log.d("Field",textName.getText()+"");
		Log.d("Field",textFirstname.getText()+"");
		Log.d("Field",textBirthdate.getText()+"");
		Log.d("Field",textEmail.getText()+"");
		Log.d("Field",textDesc.getText()+"");
		if(StringUtil.isEmpty(textName.getText()+"") || StringUtil.isEmpty(textFirstname.getText()+"") || 
				StringUtil.isEmpty(textBirthdate.getText()+"") || StringUtil.isEmpty(textEmail.getText()+"") ||
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
			SimpleDateFormat spFormat = new SimpleDateFormat("dd-MM-yyyy");
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
	
	public void callbackRegister(Boolean result,String email, String password){
		if(result){
			Intent intent = new Intent(this,ConnectActivity.class);
			intent.putExtra("email", email);
			intent.putExtra("password", password);
			
			startActivity(intent);
		}
	}
	
	
	/***DATE PICKER****/
	
	static final int DATE_DIALOG_ID = 999;
	public void doDatePicker(View vienw){
		showDialog(DATE_DIALOG_ID);
	}
	@Override
	protected Dialog onCreateDialog(int id) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, -20);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		switch (id) {
		case DATE_DIALOG_ID:
		   // set date picker as current date
		   return new DatePickerDialog(this, datePickerListener, 
                         year, month,day);
		}
		return null;
	}
 
	private DatePickerDialog.OnDateSetListener datePickerListener 
                = new DatePickerDialog.OnDateSetListener() {
 
		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay) {
			
			TextView textBirthdate = (TextView)findViewById(R.id.birthdateField);
			
			// set selected date into textview
			textBirthdate.setText(new StringBuilder().append(selectedMonth + 1)
			   .append("-").append(selectedDay).append("-").append(selectedYear)
			   .append(" "));
 
			// set selected date into datepicker also
			//dpResult.init(year, month, day, null);
 
		}
	};
}
