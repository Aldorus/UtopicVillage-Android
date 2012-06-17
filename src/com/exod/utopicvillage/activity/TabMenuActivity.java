package com.exod.utopicvillage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TabMenuActivity extends HeaderActivity{

	public void onCreate(Bundle savedInstanceState,int idRessource) {
		super.onCreate(savedInstanceState,idRessource,true);
	}
	
	public void goHelpMe(View view){
		//lors du clique sur le bouton aider moi
		Intent intent = new Intent(this,YourAskingHelpActivity.class);
		startActivity(intent);
	}
	
	public void goVolunteer(View view){
		//lors du clique sur le bouton j'aide
		Intent intent = new Intent(this,MapForHelpActivity.class);
		startActivity(intent);
	}

	public void goMe(View view){
		//lors du clique sur le bouton moi
		Intent intent = new Intent(this,MonProfilActivity.class);
		startActivity(intent);
	}
}
