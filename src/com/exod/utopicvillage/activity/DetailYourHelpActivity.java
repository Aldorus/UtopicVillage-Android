package com.exod.utopicvillage.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.exod.utopicvillage.R;

public class DetailYourHelpActivity extends HeaderActivity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,R.layout.detail_your_help);
		
		String id = getIntent().getExtras().getString("id");
		String desc = getIntent().getExtras().getString("desc");
		String amount = getIntent().getExtras().getString("amount");
		
		TextView descHelp = (TextView)findViewById(R.id.descHelp);
		TextView amountHelp = (TextView)findViewById(R.id.amountHelp);
		
		descHelp.setText(desc);
		amountHelp.setText(amount);
		
		//appel au web service qui permet de recuperer les participants
		//TODO
	}
}
