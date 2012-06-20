package com.exod.utopicvillage.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.asynchrone.GetUserAsync;
import com.exod.utopicvillage.entity.User;
import com.exod.utopicvillage.util.IntegerUtil;
import com.exod.utopicvillage.util.StringUtil;

public class FichePlayerActivity extends TabMenuActivity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,R.layout.fiche_player);
		Bundle bundle = getIntent().getExtras();
		String userId = null;
		//on requete un utilisateur
		if(bundle!=null){
			userId = bundle.getString("userId");
		}
		Log.d("Ok","we are ok, lets go"+userId);
		if(userId!=null && IntegerUtil.isInt(userId)){
			Log.d("Ok","we are ok, lets go");
			//on vient bien de recuperer un user quelquonque
			//on requete donc la base pour avoir les informations
			GetUserAsync async = new GetUserAsync(this);
			async.execute(Integer.parseInt(userId));
		}
	}
	
	public void displayInformation(User user){
		TextView textName = (TextView)findViewById(R.id.name);
		TextView textDesc = (TextView)findViewById(R.id.desc);
		
		textDesc.setText(StringUtil.isNotNull(user.getCommentaire()));
		textName.setText(user.getName()+" "+user.getFirstname());
	}
}
