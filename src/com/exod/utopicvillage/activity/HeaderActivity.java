package com.exod.utopicvillage.activity;

import com.exod.utopicvillage.R;

import android.os.Bundle;
import android.widget.TextView;

public class HeaderActivity extends MasterActivity{
	public void onCreate(Bundle savedInstanceState, int idRessource) {
		super.onCreate(savedInstanceState,idRessource,false,true);
		
		//on initialise les elements du header
		setHeader();
	}
	public void onCreate(Bundle savedInstanceState, int idRessource, boolean menu) {
		super.onCreate(savedInstanceState,idRessource,true,true);
		
		//on initialise les elements du header
		setHeader();
	}
	protected void setHeader(){
		//set header, alays displayed
		TextView labelUser = (TextView)findViewById(R.id.label_user);
		if(labelUser != null){
			labelUser.setText(storage.getUser().getName()+" "+storage.getUser().getFirstname());
		}
		
		TextView labelAmount = (TextView)findViewById(R.id.label_amount);
		if(labelAmount!=null){
			labelAmount.setText(storage.getUser().getAmount()+"");
		}
	}
}
