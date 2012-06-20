package com.exod.utopicvillage.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.util.DateUtil;
import com.exod.utopicvillage.util.StringUtil;

public class MonProfilActivity extends TabMenuActivity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,R.layout.mon_profil);
		
		//on set ls elemnts de la vue
		TextView textName = (TextView)findViewById(R.id.name);
		TextView textDesc = (TextView)findViewById(R.id.desc);
		TextView textBirthdate = (TextView)findViewById(R.id.birthdate);
		TextView textEmail = (TextView)findViewById(R.id.email);
		
		textName.setText(storage.getUser().getName()+" "+storage.getUser().getFirstname());
		textBirthdate.setText(getResources().getString(R.string.birthdate)+" "+DateUtil.convertToString(storage.getUser().getBirthdate()));
		textEmail.setText(getResources().getString(R.string.email)+" "+storage.getUser().getEmail());
		textDesc.setText(StringUtil.isNotNull(storage.getUser().getCommentaire()));
		
		
	}
}
