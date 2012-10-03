package com.exod.utopicvillage.transaction;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.exod.utopicvillage.application.UtopicVillageApplication;
import com.exod.utopicvillage.entity.Help;
import com.exod.utopicvillage.entity.User;

public class WebServiceRest{
	UtopicVillageApplication application;
	String urlApi = "http://utopic.rousselguillaume.fr/json/";
	
	public WebServiceRest(UtopicVillageApplication app){
		application = app;
	}
	
	private String callWebService(String url){
		Log.d("Bad request","Requete a deplacer "+url);
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
	
	public void helpWhereYouVolunteer(){
		SimpleDateFormat spdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String result = callWebService(application.getStorage().getUser().getId()+"/helpWhereYouVolunteer");
		try {
			
			JSONArray jsonArray = new JSONArray(result);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonHelp = jsonArray.getJSONObject(i);
				Help help = new Help();
				help.setId(jsonHelp.getInt("id"));
				help.setReproducible(false);
				help.setAmount(jsonHelp.getInt("amount"));
				try {
					help.setDate(spdate.parse(jsonHelp.getJSONObject("date").getString("date")));
				} catch (ParseException e) {
					Log.d("TAG","error lors du parsing de la date "+e);
				}
				help.setDescription(jsonHelp.getString("description"));
				JSONObject jsonUser = jsonHelp.getJSONObject("user");
				User userWhoAsk = new User();
				userWhoAsk.setId(jsonUser.getInt("id"));
				userWhoAsk.setAmount(jsonUser.getInt("amount"));
				userWhoAsk.setFirstname(jsonUser.getString("firstname"));
				userWhoAsk.setName(jsonUser.getString("name"));
				userWhoAsk.setLatitude(jsonUser.getDouble("latitude"));
				userWhoAsk.setLongitude(jsonUser.getDouble("longitude"));
				
				help.setUser(userWhoAsk);
				
				application.getStorage().addHelpToBeVolunteer(help);
			}
		} catch (JSONException e) {
			Log.d("Error","error lors du parsin JONS "+e);
		}
	}
	
	public void helpWhereYouParticipant(){
		SimpleDateFormat spdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String result = callWebService(application.getStorage().getUser().getId()+"/helpWhereYouParticipant");
		try {
			JSONArray jsonArray = new JSONArray(result);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonHelp = jsonArray.getJSONObject(i);
				Help help = new Help();
				help.setId(jsonHelp.getInt("id"));
				help.setReproducible(false);
				help.setAmount(jsonHelp.getInt("amount"));
				try {
					help.setDate(spdate.parse(jsonHelp.getJSONObject("date").getString("date")));
				} catch (ParseException e) {
					Log.d("TAG","error lors du parsing de la date "+e);
				}
				help.setDescription(jsonHelp.getString("description"));
				JSONObject jsonUser = jsonHelp.getJSONObject("user");
				User userWhoAsk = new User();
				userWhoAsk.setId(jsonUser.getInt("id"));
				userWhoAsk.setAmount(jsonUser.getInt("amount"));
				userWhoAsk.setFirstname(jsonUser.getString("firstname"));
				userWhoAsk.setName(jsonUser.getString("name"));
				userWhoAsk.setLatitude(jsonUser.getDouble("latitude"));
				userWhoAsk.setLongitude(jsonUser.getDouble("longitude"));
				
				help.setUser(userWhoAsk);
				
				application.getStorage().addHelpToBeParticipant(help);
			}
		} catch (JSONException e) {
			Log.d("Error","error lors du parsin JONS "+e);
		}
	}
}
