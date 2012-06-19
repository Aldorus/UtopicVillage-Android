package com.exod.utopicvillage.asynchrone;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class CallRestWeb {
	public static String callWebService(String url){
		String urlApi = "http://utopic.rousselguillaume.fr/json/";
		String result=null;
        try {
        	HttpClient httpclient = new DefaultHttpClient();
            //on fait la requete en l'ayant précédement correctement encodé
            HttpGet request = new HttpGet(urlApi+URLEncoder.encode(url, "UTF-8"));  
            request.setHeader("Content-type", "application/json");
            ResponseHandler<String> handler = new BasicResponseHandler();
        	result = httpclient.execute(request, handler);  
        	httpclient.getConnectionManager().shutdown();  
        } catch (ClientProtocolException e) {  
            Log.e("tag","error client "+e);
        } catch (IOException e) {  
            Log.e("tag","error io "+e);  
        }  
        
        return result;
    } 
}
