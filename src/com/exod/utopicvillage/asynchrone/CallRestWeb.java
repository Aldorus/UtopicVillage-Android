package com.exod.utopicvillage.asynchrone;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.exod.utopicvillage.activity.MasterActivity;
import com.exod.utopicvillage.application.UtopicVillageApplication;

public class CallRestWeb {
	public static String callWebService(String url) {
		try {
			String urlApi = "http://utopic.rousselguillaume.fr/json/";
			String result=null;
	    	HttpClient httpclient = new DefaultHttpClient();
	        //on fait la requete en l'ayant précédement correctement encodé
	        HttpGet request = new HttpGet(urlApi+URLEncoder.encode(url, "UTF-8"));  
	        request.setHeader("Content-type", "application/json");
	        ResponseHandler<String> handler = new BasicResponseHandler();
    		result = httpclient.execute(request, handler);
    		httpclient.getConnectionManager().shutdown();
    		return result;
		} catch (ClientProtocolException e) {
			return null;
		} catch (IOException e) {
			return null;
		}    
    } 
	
	public static String callWebService(MasterActivity activity, String url) {
		UtopicVillageApplication application = (UtopicVillageApplication) activity.getApplicationContext();
		try {
			String urlApi = "http://utopic.rousselguillaume.fr/json/";
			String result=null;
	    	HttpClient httpclient = new DefaultHttpClient();
	        //on fait la requete en l'ayant précédement correctement encodé
	        HttpGet request = new HttpGet(urlApi+URLEncoder.encode(url, "UTF-8"));  
	        request.setHeader("Content-type", "application/json");
	        ResponseHandler<String> handler = new BasicResponseHandler();
    		result = httpclient.execute(request, handler);
    		httpclient.getConnectionManager().shutdown();
    		return result;
		} catch (ClientProtocolException e) {
			application.catchErrorServer();
			return null;
		} catch (IOException e) {
			application.catchErrorServer();
			return null;
		}    
    } 
}
