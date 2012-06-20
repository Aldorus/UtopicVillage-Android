package com.exod.utopicvillage.activity;

import android.os.Bundle;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.entity.User;

public class MonProfilActivity extends TabMenuActivity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,R.layout.mon_profil);
		
		//il s'agit de nous
		//on affiche donc nos information
		displayInformation(storage.getUser());
	}
	
	public void displayInformation(User user){
		
	}
}
