package com.exod.utopicvillage.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.entity.Help;
import com.exod.utopicvillage.util.DateUtil;

public class YourAskingHelpActivity extends TabMenuActivity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState,R.layout.your_asking_help);
	    
	    //on charge la liste view
	    getYourAskingHelp();
	}
	
	public void goAskHelp(View view){
		//TODO
		Intent intent = new Intent(this,HelpMeActivity.class);
		startActivity(intent);
	}
	
	private void getYourAskingHelp(){
		//we get the information from the webservice
		Help help = webService.yourAskingHelp();
		//on sauvegarde cette aide
		utopicVillageApplication.getStorage().setAskingHelp(help);
		if(help!=null){
			//on cche le bouton de creation d'une nouvelle aide
			Button buttonNewHelp = (Button)findViewById(R.id.bouton_new_help);
			buttonNewHelp.setVisibility(View.GONE);
			
			//on affiche la demande d'aide effectuée
			LinearLayout viewCell = (LinearLayout) findViewById(R.id.your_asking_help);
			//on vide cette vue et on lui retire son background en vue de l'insertion de la vue inflatée
			viewCell.setBackgroundDrawable(null);
			
			LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View theInflatedView = inflater.inflate(R.layout.row_for_asking_help, null); // 2 and 3
			viewCell.addView(theInflatedView);
			
			//on ajout les element de l'aide
			
			TextView textId = (TextView) theInflatedView.findViewById(R.id.idCell);
			TextView text = (TextView) theInflatedView.findViewById(R.id.textCell);
			TextView sousText= (TextView) theInflatedView.findViewById(R.id.sousTextCell);
			TextView nombre_point = (TextView) theInflatedView.findViewById(R.id.nombre_point);
			TextView nombre_volunteer = (TextView) theInflatedView.findViewById(R.id.nombre_volunteer);
			
			textId.setText(help.getId()+"");
			text.setText(getResources().getString(R.string.ask_for)+DateUtil.convertToStringDifDate(help.getDate())+"");
			sousText.setText(help.getDescritpion()+"pouette");
			nombre_point.setText(help.getAmount()+" points");
			//TODO
			nombre_volunteer.setText(0+" participant");
			
			
			//on ajout un écouteur sur le layout
			viewCell.setClickable(true);
			viewCell.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					TextView cellId = (TextView)view.findViewById(R.id.idCell);
					String stringId = cellId.getText()+"";
					
					Intent intent = new Intent(getApplicationContext(), DetailYourHelpActivity.class);
					intent.putExtra("id", stringId);
					startActivity(intent);
				}
			}); 
		}
		//sinon on laisse le message et la vue par defaut
		LinearLayout linear_asking = (LinearLayout)findViewById(R.id.your_asking_help);
		TextView textViewMessage = new TextView(utopicVillageApplication);
		textViewMessage.setText(getResources().getString(R.string.no_helpQ));
		textViewMessage.setTextColor(getResources().getColor(R.color.text_color));
		linear_asking.addView(textViewMessage);
	}
	
	
//	//he return a hasctag with the elemnts
//	Collection<String> colTitre  = hashtable.get("titre");
//	Collection<String> colSousTitre  = hashtable.get("sousTitre");
//	Collection<String> colId  = hashtable.get("id");
//	Collection<String> colDesc = hashtable.get("desc");
//	Collection<String> colAmount  = hashtable.get("amount");
//	
//	//we convert the collection of elements to array of elements
//	//for the custom adapter
//	String[] titre = (String[])colTitre.toArray(new String[colTitre.size()]);
//	String[] sousTitre = (String[])colSousTitre.toArray(new String[colSousTitre.size()]);
//	String[] id = (String[])colId.toArray(new String[colId.size()]);
//	String[] desc = (String[])colDesc.toArray(new String[colDesc.size()]);
//	String[] amount = (String[])colAmount.toArray(new String[colAmount.size()]);
//	
//	//we use the custom adaptater
//	YourAskingHelpAdapter adapter = new YourAskingHelpAdapter(this, titre, sousTitre,id,desc,amount);
//	ListView listeView = (ListView) findViewById(R.id.liste_your_asking_help);
//	listeView.setAdapter(adapter);
//	
//	//add the listener for the list view
//	listeView.setOnItemClickListener(new ListView.OnItemClickListener() {
//        @Override
//		public void onItemClick(AdapterView<?> arg0, View view, int psition,long id) {
//        	//recuperation des informations de la cellule selectionnée
//        	TextView cellId = (TextView)view.findViewById(R.id.idCell);
//			String stringId = cellId.getText()+"";
//			TextView cellDesc = (TextView)view.findViewById(R.id.descCell);
//			String stringDesc = cellDesc.getText()+"";
//			TextView cellAmount = (TextView)view.findViewById(R.id.amountCell);
//			String stringAmount = cellAmount.getText()+"";
//			
//			
//			Intent intent = new Intent(getApplicationContext(), DetailHelpActivity.class);
//			intent.putExtra("id", stringId);
//			intent.putExtra("desc", stringDesc);
//			intent.putExtra("amount", stringAmount);
//			startActivity(intent);
//		}
//	});
	
}
