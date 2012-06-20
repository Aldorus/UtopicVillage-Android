package com.exod.utopicvillage.activity;

import java.util.Collection;
import java.util.Hashtable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.adaptater.VolunteerAdapter;
import com.exod.utopicvillage.asynchrone.DeleteHelpAsync;
import com.exod.utopicvillage.asynchrone.GetVolunteerAsync;
import com.exod.utopicvillage.asynchrone.InsertNewParticipantAsync;
import com.exod.utopicvillage.asynchrone.PayerAskingHelpAsync;
import com.exod.utopicvillage.asynchrone.YourAskingHelpAsync;
import com.exod.utopicvillage.entity.Help;
import com.exod.utopicvillage.entity.User;
import com.exod.utopicvillage.util.CollectionUtil;
import com.exod.utopicvillage.util.DateUtil;
import com.exod.utopicvillage.util.StringUtil;

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
		
		//asynchrone 
		YourAskingHelpAsync askingHelpAsync = new YourAskingHelpAsync(this);
		askingHelpAsync.execute();
	}
	
	//methode de callback
	public void displayInfoHelp(Help help){
		this.help=help;
		//on sauvegarde cette aide
		utopicVillageApplication.getStorage().setAskingHelp(help);
			
		if(help!=null){
			//we get volunteer for asking help
			
			setViewOfHelp();
			if(utopicVillageApplication.getStorage().getAskingHelp().getParticipant()!=null){
				//soit on a déja un participants
				TextView textView = (TextView)findViewById(R.id.label_volunteer);
				textView.setText(getResources().getString(R.string.label_participant));
				setTheParticipant();
			}else{
				GetVolunteerAsync volunteerAsync = new GetVolunteerAsync(this);
				volunteerAsync.execute();
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
		DeleteHelpAsync async = new DeleteHelpAsync(this);
		async.execute(Integer.parseInt(textId.getText()+""));
	}
	
	public void callBackCancel(){
		//changment de vue pour prise en compte de la suppression
		Intent intent = new Intent(this,YourAskingHelpActivity.class);
		startActivity(intent);
	}
	
	public void takeThisVolunteer(View view){
		//asynchrone 
		InsertNewParticipantAsync async = new InsertNewParticipantAsync(this);
		async.execute(Integer.parseInt(view.getTag()+""));
		
	}
	
	public void insertedParticipant(){
		Intent intent = new Intent(this,YourAskingHelpActivity.class);
		startActivity(intent);
	}
	
	public void setViewOfHelp(){
		//on cache le bouton de creation d'une nouvelle aide
		Button buttonNewHelp = (Button)findViewById(R.id.bouton_new_help);
		buttonNewHelp.setVisibility(View.GONE);
		
		//on affiche la demande d'aide effectuée
		LinearLayout viewCell = (LinearLayout) findViewById(R.id.your_asking_help);
		viewCell.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		
		//on vide la seconde vue et on lui retire son background en vue de l'insertion de la vue inflatée
		LinearLayout linear_asking = (LinearLayout)findViewById(R.id.your_help_message);
		linear_asking.setVisibility(View.GONE);
		
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout theInflatedView = (LinearLayout)inflater.inflate(R.layout.row_for_asking_help, null); 
		theInflatedView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		
		viewCell.addView(theInflatedView);
		
		//on ajoute les elements de l'aide
		TextView textId = (TextView) theInflatedView.findViewById(R.id.idCell);
		TextView text = (TextView) theInflatedView.findViewById(R.id.textCell);
		TextView sousText= (TextView) theInflatedView.findViewById(R.id.sousTextCell);
		TextView nombre_point = (TextView) theInflatedView.findViewById(R.id.nombre_point);
		textId.setText(help.getId()+"");
		text.setText(getResources().getString(R.string.ask_for)+" "+DateUtil.convertToStringDifDate(help.getDate())+"");
		sousText.setText(help.getDescritpion()+"");
		nombre_point.setText(help.getAmount()+" "+getResources().getString(R.string.point));
	}
	
	public void setListeOfVolunteer(){
		//on set e nombre de volontaire pour la cellule de l'aide
		//problematique apparut avec les requetes asynchrones
		TextView nombre_volunteer = (TextView) findViewById(R.id.nombre_volunteer);
		if(help.getHashVolunteer()==null || help.getHashVolunteer().size()<2){
			//sigulier
			nombre_volunteer.setText(help.getHashVolunteer().size()+" "+getResources().getString(R.string.volunteer));
		}else{
			//masculin
			nombre_volunteer.setText(help.getHashVolunteer().size()+" "+getResources().getString(R.string.volunteers));
		}
		
		//reconstitution de la hashtable avec la hasttable complete
		Hashtable<String, Collection<String>>hashtable = new Hashtable<String, Collection<String>>();
		hashtable = CollectionUtil.convertHashToHashForUser(help.getHashVolunteer());
		
		//he return a hashtag with the elements
		Collection<String> colId  = hashtable.get("id");
		Collection<String> colName  = hashtable.get("name");
		Collection<String> colDesc = hashtable.get("desc");
		Collection<String> colPointReputaiton  = hashtable.get("point_reputation");
		
		//suppression du champ "volontaires :" dans le cas ou les collections sont vides
		if(colId.size()==0){
			TextView textView = (TextView)findViewById(R.id.label_volunteer);
			textView.setVisibility(View.GONE);
		}
		
		//we convert the collection of elements to array of elements
		//for the custom adapter
		String[] id = (String[])colId.toArray(new String[colId.size()]);
		String[] name = (String[])colName.toArray(new String[colName.size()]);
		String[] desc = (String[])colDesc.toArray(new String[colDesc.size()]);
		String[] pointReputaiton = (String[])colPointReputaiton.toArray(new String[colPointReputaiton.size()]);
		
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

	public void setTheParticipant(){

		TextView nombre_volunteer = (TextView)findViewById(R.id.nombre_volunteer);
		nombre_volunteer.setVisibility(View.GONE);
		
		LinearLayout viewCell = (LinearLayout) findViewById(R.id.your_participant);
		
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		LinearLayout theInflatedView = (LinearLayout)inflater.inflate(R.layout.row_for_particiapnt, null);
		theInflatedView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		viewCell.addView(theInflatedView);
		
		//on ajoute les elements du participant
		TextView textId = (TextView) theInflatedView.findViewById(R.id.idCell);
		TextView text = (TextView) theInflatedView.findViewById(R.id.textCell);
		TextView sousText= (TextView) theInflatedView.findViewById(R.id.sousTextCell);
		TextView pointReputation = (TextView) theInflatedView.findViewById(R.id.point_reputation);
		
		User participant = utopicVillageApplication.getStorage().getAskingHelp().getParticipant();
		textId.setText(participant.getId()+"");
		text.setText(participant.getName()+" "+participant.getFirstname());
		//TODO
		sousText.setText(StringUtil.isNotNullShort(participant.getCommentaire()));
		pointReputation.setText("0");
	}
	
	public void goPayer(View view){
		//on valide le payement entre les deux joueurs
		User user = storage.getUser();
		user.setAmount(user.getAmount()-storage.getAskingHelp().getAmount());
		
		//on l'envoi au webService pour mise a jour de la base
		PayerAskingHelpAsync payementAsync = new PayerAskingHelpAsync(this);
		payementAsync.execute();
	}
	
	public void callBackPayement(){
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
	
	public void goDetail(View view){
		//voir la fiche d'un joueur
		
		int userId = Integer.parseInt(view.getTag()+"");
		Log.d("Ok","we are ok, lets go "+userId);
		Intent intent = new Intent(this,FichePlayerActivity.class);
		intent.putExtra("userId", userId+"");
		startActivity(intent);
	}
}
