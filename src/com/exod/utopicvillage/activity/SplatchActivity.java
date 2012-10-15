/**
 * @author Roussel Guillaume
 * @licence CC-BY-NC
 */
package com.exod.utopicvillage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.exod.utopicvillage.R;

public class SplatchActivity extends MasterActivity {
	protected int SPLASHTIME  = 1000; // time to display the splash screen in ms
	private static final int STOPSPLASH = 0;
	
	private Handler splashHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case STOPSPLASH:
			//remove SplashScreen from view
			// Run next activity
        
	        if(storage.getUser().getName()==null){
	        	Intent intent = new Intent(getApplicationContext(), ConnectActivity.class);
	        	startActivity(intent);
	        	finish();
	        }else{
	        	Intent intent = new Intent(getApplicationContext(), YourAskingHelpActivity.class);
	        	startActivity(intent);
	        	finish();
	        	//si il y a déja les inforations on précharge
	        	//TODO
	        }
			break;
		}
		super.handleMessage(msg);
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState,R.layout.splatch);
	    Message msg = new Message();
	    msg.what = STOPSPLASH;
	    splashHandler.sendMessageDelayed(msg, SPLASHTIME);
	       
	}
}
