package com.exod.utopicvillage.activity;

import java.util.Collection;
import java.util.Hashtable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.adaptater.VolunteerAdapter;
import com.exod.utopicvillage.entity.Help;
import com.exod.utopicvillage.entity.User;
import com.exod.utopicvillage.util.CollectionUtil;
import com.exod.utopicvillage.util.DateUtil;

public class YourAskingHelpActivity extends TabMenuActivity{
	Help help;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState,R.layout.your_asking_help);
	    
	    //on charge la liste view
	    getYourAskingHelp();
	}
	
	public void goAskHelp(View view){
		Intent intent = new Intent(this,HelpMeActivity.class);
		startActivity(intent);
	}
	
	private void getYourAskingHelp(){
		//we get the information from the webservice
		help = webService.yourAskingHelp();
		Log.d("TAG","aagaa"+help);
		//on sauvegarde cette aide
		utopicVillageApplication.getStorage().setAskingHelp(help);
			
		if(help!=null){
			//we get volunteer for asking help
			webService.getVolunteer();
			setViewOfHelp();
			
			if(utopicVillageApplication.getStorage().getAskingHelp().getParticipant()!=null){
				//soit on a déja un participants
				TextView textView = (TextView)findViewById(R.id.label_volunteer);
				textView.setText(getResources().getString(R.string.label_participant));
				
				setTheParticipant();
			}else{
				//soit on ajout les volontaires
				//on ajoute les utilisateurs volontaire
				setListeOfVolunteer();
			}
			
		}else{
			//sinon on laisse le message et la vue par defaut
			LinearLayout linear_asking = (LinearLayout)findViewById(R.id.your_help_message);
			TextView textViewMessage = new TextView(utopicVillageApplication);
			textViewMessage.setText(getResources().getString(R.string.no_helpQ));
			textViewMessage.setTextColor(getResources().getColor(R.color.text_color));
			linear_asking.addView(textViewMessage);
			
			TextView labelVolunteer = (TextView)findViewById(R.id.label_volunteer);
			labelVolunteer.setVisibility(View.GONE);
		}
	}
		
	public void goCancel(View view){
		TextView textId = (TextView) findViewById(R.id.idCell);
		webService.deleteHelp(Integer.parseInt(textId.getText()+""));

		//changment de vue pour prise en compte de la suppression
		Intent intent = new Intent(this,YourAskingHelpActivity.class);
		startActivity(intent);
		
	}
	
	public void takeThisVolunteer(View view){
		Log.d("Taf",view.getTag()+"");
		//TODO
		//on requete le web service qui permet de supprimer les volontaire et qui ajoute le participant
		webService.insertNewParticipant(Integer.parseInt(view.getTag()+""));
		Intent intent = new Intent(this,YourAskingHelpActivity.class);
		startActivity(intent);
	}
	
	public void goDetail(View view){
		//TODO		
	}
	
	public void setViewOfHelp(){
		//on cche le bouton de creation d'une nouvelle aide
		Button buttonNewHelp = (Button)findViewById(R.id.bouton_new_help);
		buttonNewHelp.setVisibility(View.GONE);
		
		//on affiche la demande d'aide effectuée
		LinearLayout viewCell = (LinearLayout) findViewById(R.id.your_asking_help);
		
		//on vide la seconde vue et on lui retire son background en vue de l'insertion de la vue inflatée
		LinearLayout linear_asking = (LinearLayout)findViewById(R.id.your_help_message);
		linear_asking.setVisibility(View.GONE);
		
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View theInflatedView = inflater.inflate(R.layout.row_for_asking_help, null); 
		viewCell.addView(theInflatedView);
		
		//on ajoute les elements de l'aide
		TextView textId = (TextView) theInflatedView.findViewById(R.id.idCell);
		TextView text = (TextView) theInflatedView.findViewById(R.id.textCell);
		TextView sousText= (TextView) theInflatedView.findViewById(R.id.sousTextCell);
		TextView nombre_point = (TextView) theInflatedView.findViewById(R.id.nombre_point);
		TextView nombre_volunteer = (TextView) theInflatedView.findViewById(R.id.nombre_volunteer);
		textId.setText(help.getId()+"");
		text.setText(getResources().getString(R.string.ask_for)+" "+DateUtil.convertToStringDifDate(help.getDate())+"");
		sousText.setText(help.getDescritpion()+"");
		nombre_point.setText(help.getAmount()+" "+getResources().getString(R.string.point));
		
		//TODO
		if(help.getHashVolunteer()==null || help.getHashVolunteer().size()<2){
			nombre_volunteer.setText(0+" "+getResources().getString(R.string.volunteer));
		}else{
			nombre_volunteer.setText(0+" "+getResources().getString(R.string.volunteers));
		}
	}
	
	private void setListeOfVolunteer(){
		//reconstitution de la hashtable avec la collection
		Hashtable<String, Collection<String>>hashtable = new Hashtable<String, Collection<String>>();
		hashtable = CollectionUtil.convertHashToHashForUser(help.getHashVolunteer());
		
		//he return a hashtag with the elements
		Collection<String> colId  = hashtable.get("id");
		Collection<String> colName  = hashtable.get("name");
		Collection<String> colDesc = hashtable.get("desc");
		Collection<String> colPointReputaiton  = hashtable.get("point_reputation");
		
		//suppression du champ "volontaires :" dans le cas ou la collection est vide
		if(colId.size()==0){
			TextView textView = (TextView)findViewById(R.id.label_volunteer);
			textView.setVisibility(View.GONE);
		}
		
		//we convert the collection of elements to array of elements
		//for the custom adapter
		String[] id = (String[])colId.toArray(new String[colId.size()]);
		String[] name = (String[])colName.toArray(new String[colName.size()]);
		String[] desc = (String[])colName.toArray(new String[colDesc.size()]);
		String[] pointReputaiton = (String[])colName.toArray(new String[colPointReputaiton.size()]);
		
		//we use the custom adaptater
		VolunteerAdapter adapter = new VolunteerAdapter(this, name, id, desc, pointReputaiton);
		ListView listeView = (ListView) findViewById(R.id.list_view_user);
		listeView.setAdapter(adapter);
		
		//add the listener for the list view
		listeView.setOnItemClickListener(new ListView.OnItemClickListener() {
	        @Override
			public void onItemClick(AdapterView<?> arg0, View view, int psition,long id) {
	        	//recuperation des informations de la cellule selectionnée
	        	TextView cellId = (TextView)view.findViewById(R.id.idCell);
				String stringId = cellId.getText()+"";
				
				Intent intent = new Intent(getApplicationContext(), DetailHelpActivity.class);
				intent.putExtra("id", stringId);
				startActivity(intent);
			}
		});
	}
	
	private void setTheParticipant(){
		//TODO
		//envoi de la notification
		
		LinearLayout viewCell = (LinearLayout) findViewById(R.id.your_participant);
		
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View theInflatedView = inflater.inflate(R.layout.row_for_particiapnt, null); 
		viewCell.addView(theInflatedView);
		
		//on ajoute les elements de l'aide
		
		TextView textId = (TextView) theInflatedView.findViewById(R.id.idCell);
		TextView text = (TextView) theInflatedView.findViewById(R.id.textCell);
		TextView sousText= (TextView) theInflatedView.findViewById(R.id.sousTextCell);
		TextView pointReputation = (TextView) theInflatedView.findViewById(R.id.point_reputation);
		
		User participant = utopicVillageApplication.getStorage().getAskingHelp().getParticipant();
		textId.setText(participant.getId()+"");
		text.setText(participant.getName()+" "+participant.getFirstname());
		sousText.setText(participant.getCommentaire());
		pointReputation.setText("0");
	}
	
	public void goPayer(View view){
		//on valide le payement entre les deux joueurs
		User user = storage.getUser();
		user.setAmount(user.getAmount()-storage.getAskingHelp().getAmount());
		
		//on l'envoi au webService pour mise a jour de la base
		webService.payerAskingHelp();
		
		//on clean le storage
		storage.setAskingHelp(null);
		
		//on affiche un message dans un toast pour signaler a l'utilisateur que ça demande a bien été prise en compte
		CharSequence text = getResources().getString(R.string.payement_done);
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(getApplicationContext(), text, duration);
		toast.show();
		
		Intent intent = new Intent(this,YourAskingHelpActivity.class);
		startActivity(intent);
	}
}
