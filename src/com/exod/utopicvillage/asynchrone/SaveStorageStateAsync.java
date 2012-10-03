package com.exod.utopicvillage.asynchrone;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.os.AsyncTask;

import com.exod.utopicvillage.application.UtopicVillageApplication;

public class SaveStorageStateAsync extends AsyncTask<UtopicVillageApplication, Integer, Void>{
	@Override
	protected Void doInBackground(UtopicVillageApplication... params) {
		UtopicVillageApplication application = params[0];
		
//		File root = new File(Environment.getExternalStorageDirectory(), "MagellanEvolution/Storage");
//		if (!root.exists()) {
//            root.mkdirs();
//        }
		try {
//			FileOutputStream fichier = new FileOutputStream(root+"storage.ser");
			FileOutputStream fichier = application.openFileOutput("storage.ser", Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fichier);
			oos.writeObject(application.getStorageRequest().getHashTable());
			oos.flush();
			oos.close();
		}
		catch (java.io.IOException e) {
				e.printStackTrace();
		}
		return null;
	}
}
