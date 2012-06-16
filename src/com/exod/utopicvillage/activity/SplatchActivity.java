package com.exod.utopicvillage.activity;

import android.content.Intent;
import android.os.Bundle;

import com.exod.utopicvillage.R;

public class SplatchActivity extends MasterActivity {
	protected int _splashTime = 1000; // time to display the splash screen in ms
	private Thread mSplashThread; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState,R.layout.splatch);
	    
	    final SplatchActivity sPlashScreen = this;   
        
        // The thread to wait for splash screen events
        mSplashThread =  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this){
                        // Wait given period of time or exit on touch
                        wait(_splashTime);
                    }
                }
                catch(InterruptedException ex){                    
                }

                finish();
                
                // Run next activity
                Intent intent = new Intent();
                
                if(storage.getUser().getName()==null){
                	intent.setClass(sPlashScreen, ConnectActivity.class);
                }else{
                	intent.setClass(sPlashScreen, YourAskingHelpActivity.class);
                	//si il y a déja les inforations on précharge
                	//TODO
                }
                startActivity(intent);
                stop();
                finish();
            }
        };
        
        mSplashThread.start();   
	}
}
