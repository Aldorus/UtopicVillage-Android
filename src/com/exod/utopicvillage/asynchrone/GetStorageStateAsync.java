/**
 * @author Roussel Guillaume
 * @licence CC-BY-NC
 */
package com.exod.utopicvillage.asynchrone;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Hashtable;

import android.os.AsyncTask;

import com.exod.utopicvillage.application.UtopicVillageApplication;

public class GetStorageStateAsync extends AsyncTask<UtopicVillageApplication, Integer, Void>{

	@Override
	protected Void doInBackground(UtopicVillageApplication... params) {
		UtopicVillageApplication application = params[0];
//		File root = new File(Environment.getExternalStorageDirectory(), "MagellanEvolution/Storage");
//		if (!root.exists()) {
//            root.mkdirs();
//        }
		try {
//			FileInputStream fichier = new FileInputStream(root+"storage.ser");
			FileInputStream fichier = application.openFileInput("storage.ser");
			ObjectInputStream ois = new ObjectInputStream(fichier);
			@SuppressWarnings("unchecked")
			Hashtable<String, String> hashTable = (Hashtable<String, String>) ois.readObject();
			application.getStorageRequest().setHashTable(hashTable);
		}
		catch (java.io.IOException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
