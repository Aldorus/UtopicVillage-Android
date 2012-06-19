package com.exod.utopicvillage.activity;

import java.util.Hashtable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.asynchrone.ReportHelpAsync;
import com.exod.utopicvillage.asynchrone.ToBeVolunteerAsync;
import com.exod.utopicvillage.entity.Help;
import com.exod.utopicvillage.store.Storage;
import com.exod.utopicvillage.util.DateUtil;

public class DetailHelpActivity extends HeaderActivity{
	Help help;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,R.layout.detail_help);
		
		//on recupere l'aide stocké dans la hashtable de l'application
		String idHelp = getIntent().getExtras().getString("idHelp");
		Storage storage =utopicVillageApplication.getStorage();
		Hashtable<String, Help> another = storage.getAnotherHelp();
		help=null;
		if(another!=null && idHelp!=null){
			help  = another.get(idHelp);
		}
		
		if(help!=null){
			TextView descHelp = (TextView)findViewById(R.id.sousTextCell);
			TextView amountHelp = (TextView)findViewById(R.id.amountHelp);
			TextView titreHelp = (TextView)findViewById(R.id.textCell);
			
			titreHelp.setText(getResources().getString(R.string.ask_for)+" "+DateUtil.convertToStringDifDate(help.getDate()));
			descHelp.setText(help.getDescritpion());
			amountHelp.setText(help.getAmount()+" "+getResources().getString(R.string.point));
			
			//on test si l'on est deja volontaire pour cette aide
			if(storage.getHelpYouReParticipate().get(help.getId())!=null){
				disabledButton();
			}
		}else{
			//TODO error message
		}
	}
	
	public void goShowOnMap(View view){
		Intent intent = new Intent(this,UnderConstructActivity.class);
		startActivity(intent);
	}
	
	
	public void goShowProfil(View view){
		//vrs le profil du joueur
		Intent intent = new Intent(this,UnderConstructActivity.class);
		startActivity(intent);
	}
	
	public void goToBeVolunteer(View view){
		//on desactive le bouton
		disabledButton();

		//on requete le webservice pour devenir volontiare
		ToBeVolunteerAsync async = new ToBeVolunteerAsync(this, help);
		async.execute();
	}
	
	public void callbackVolunteer(){
		//on l'ajoute à la hashtable du storage
		storage.addHelpToBeVolunteer(help);
		//on affiche un message dans un toast pour signaler a l'utilisateur que ça demande a bien été prise en compte
		CharSequence text = getResources().getString(R.string.you_re_volunteer);
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(getApplicationContext(), text, duration);
		toast.show();
	}
	
	public void disabledButton(){
		//on change le bouton
		Button button = (Button)findViewById(R.id.button_volunteer);
		button.setClickable(false);
		button.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_disabled));
		button.setText(getResources().getString(R.string.already_volonteer));		
	}
	
	public void goReportHelp(View view){
		//requete asynchrone
		ReportHelpAsync async = new ReportHelpAsync(this);
		async.execute(help.getId());
	}
	
	public void callBackReport(){

		CharSequence text = getResources().getString(R.string.reported_help);
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(getApplicationContext(), text, duration);
		toast.show();
	}
}
