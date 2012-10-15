/**
 * @author Roussel Guillaume
 * @licence CC-BY-NC
 */
package com.exod.utopicvillage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.exod.utopicvillage.R;

public class TabMenuActivity extends HeaderActivity{

	public void onCreate(Bundle savedInstanceState,int idRessource) {
		super.onCreate(savedInstanceState,idRessource,true);
		//on set l'element du menu surligné
		if("YourAskingHelpActivity".equals(this.getClass().getSimpleName())){
			LinearLayout linear = (LinearLayout)findViewById(R.id.element_menu_helpme);
			linear.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_menu_focus));
		}if("MapForHelpActivity".equals(this.getClass().getSimpleName())){
			LinearLayout linear = (LinearLayout)findViewById(R.id.element_menu_volunteer);
			linear.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_menu_focus));
		}if("MonProfilActivity".equals(this.getClass().getSimpleName())){
			LinearLayout linear = (LinearLayout)findViewById(R.id.element_menu_me);
			linear.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_menu_focus));
		}
		
	}
	
	public void goHelpMe(View view){
		//lors du clique sur le bouton aider moi
		Intent intent = new Intent(this,YourAskingHelpActivity.class);
		startActivityClean(intent);
	}
	
	public void goVolunteer(View view){
		//lors du clique sur le bouton j'aide
		Intent intent = new Intent(this,MapForHelpActivity.class);
		startActivityClean(intent);
	}

	public void goMe(View view){
		//lors du clique sur le bouton moi
		Intent intent = new Intent(this,MonProfilActivity.class);
		startActivityClean(intent);
	}
}
