package com.exod.utopicvillage.activity;

import java.util.Hashtable;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.entity.Help;
import com.exod.utopicvillage.store.Storage;

public class DetailHelpActivity extends HeaderActivity{
	Help help;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,R.layout.detail_help,false,true);
		
		//on recupere l'aide stocké dans la hashtable de l'application
		String idHelp = getIntent().getExtras().getString("idHelp");
		Storage storage =utopicVillageApplication.getStorage();
		Hashtable<String, Help> another = storage.getAnotherHelp();
		help=null;
		if(another!=null && idHelp!=null){
			help  = another.get(idHelp);
		}
		
		if(help!=null){
			TextView descHelp = (TextView)findViewById(R.id.descHelp);
			TextView amountHelp = (TextView)findViewById(R.id.amountHelp);
			
			descHelp.setText(help.getDescritpion());
			//TODO bug
//			amountHelp.setText(help.getAmount());
		}else{
			//TODO error message
		}
	}
	
	public void goToBeVolunteer(View view){
		//on requete le webservice pour devenir volontiare
		webService.toBeVolonteer(help);
		
		//on desactive le bouton
		disabledButton();
		
		//on affiche un message dans un toast pour signaler a l'utilisateur que ça demande a bien été prise en compte
		CharSequence text = getResources().getString(R.string.you_re_volunteer);
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(getApplicationContext(), text, duration);
		toast.show();
	}
	
	public void disabledButton(){
		//on change le bouton
		Button button = (Button)findViewById(R.id.button_volunteer);
		button.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_disabled));
				
	}
}
