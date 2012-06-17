package com.exod.utopicvillage.transaction;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
import com.exod.utopidvillage.implement.WebServiceInterface;

public class WebServiceRest implements WebServiceInterface{
	String baseURL;
	UtopicVillageApplication application;
	public WebServiceRest(String url,UtopicVillageApplication app){
		baseURL = url;
		application = app;
	}
	
	private String callWebService(String url){ 
		String result=null;
        HttpClient httpclient = new DefaultHttpClient();  
        HttpGet request = new HttpGet(baseURL+url);  
        request.setHeader("Content-type", "application/json");
        ResponseHandler<String> handler = new BasicResponseHandler();
        
        try {
        	result = httpclient.execute(request, handler);  
        } catch (ClientProtocolException e) {  
            Log.e("tag","error client "+e);
        } catch (IOException e) {  
            Log.e("tag","error io "+e);  
        }  
        httpclient.getConnectionManager().shutdown();  
        return result;
    } 
	
	public boolean testConnect(String email,String password){
		JSONObject jsonObject;
		String status;
		try {
			String resultWebServ = callWebService(email+"/"+password+"/testConnect");
			if(resultWebServ==null){
				return false;
			}
			jsonObject = new JSONObject(resultWebServ);
			
			status = jsonObject.getString("status");
		} catch (JSONException e) {
			return false;
		} 
		
		if("ok".equals(status)){
			//on set l'user 
			try {
				JSONObject jsonObjectUser = jsonObject.getJSONObject("user");
				User user = application.getStorage().getUser();
				user.setId(jsonObjectUser.getInt("id"));
				user.setAmount(jsonObjectUser.getInt("amount"));
				user.setName(jsonObjectUser.getString("name"));
				user.setFirstname(jsonObjectUser.getString("firstname"));
				user.setLatitude(jsonObjectUser.getDouble("latitude"));
				user.setLongitude(jsonObjectUser.getDouble("longitude"));
				application.getStorage().setUser(user);
			} catch (JSONException e) {
				Log.d("error","error lors du parsing "+e);
				return false;
			}
			return true;
		}
		return false;
	}
	
	public boolean insertHelp(Help help){
		JSONObject jsonObject;
		String status;
		try {
			String resultWebServ = callWebService(help.getUser().getId()+"/"+help.getAmount()+"/"+help.getDescritpion()+"/"+help.isReproducible()+"/insertHelp");
			if(resultWebServ==null){
				return false;
			}
			jsonObject = new JSONObject(resultWebServ);
			
			status = jsonObject.getString("status");
		} catch (JSONException e) {
			return false;
		} 
		
		if("ok".equals(status)){
			return true;
		}
		
		return false;
	}
	
	public Help yourAskingHelp(){
		User user = application.getStorage().getUser();
		Help help = new Help();
		String resultWebServ = callWebService(user.getId()+"/askingHelp");
		try {
			JSONObject jsonObject = (JSONObject) new JSONObject(resultWebServ);
			if(!"ok".equals(jsonObject.getString("status"))){
				return null;
			}
			String stringDateBrut = jsonObject.getJSONObject("date").getString("date");
			SimpleDateFormat spdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date dateBrut=null;
			try {
				dateBrut = spdate.parse(stringDateBrut);
			} catch (ParseException e) {
				Log.d("TAG","error lors du parsing de la date "+e);
			}
			help.setDate(dateBrut);
			help.setId(jsonObject.getInt("id"));
			help.setAmount(jsonObject.getInt("amount"));
			help.setDescritpion(jsonObject.getString("description"));
			help.setReproducible(false);
			help.setUser(user);
			
			//Set du participant si il existe
			JSONObject jsonParticipant = jsonObject.getJSONObject("participant");
			if(jsonParticipant!=null){
				User participant = new User();
				participant.setId(jsonParticipant.getLong("id"));
				participant.setAmount(jsonParticipant.getInt("amount"));
				participant.setCommentaire(jsonParticipant.getString("commentaire"));
				participant.setFirstname(jsonParticipant.getString("firstname"));
				participant.setName(jsonParticipant.getString("name"));
				participant.setLatitude(jsonParticipant.getDouble("latitude"));
				participant.setLongitude(jsonParticipant.getDouble("longitude"));
				help.setParticipant(participant);
			}
		} catch (JSONException e) {
			return null;
		}
		
		return help;
	}
	
	public void setLatitudeLongitude(double latitude,double longitude){
		//on envoye les position de l'userà un webservice qui la sauvegarde
		User user = application.getStorage().getUser();
		callWebService(user.getId()+"/"+latitude+"/"+longitude+"/savePosition");
		//on suppose que le webservice retourne toujours le satatus ok
		//TODO
	}
	
	public Collection<Help> getNearAskingHelp(){
		User user = application.getStorage().getUser();
		Collection<Help> colHelpAsking = new ArrayList<Help>();
		
		String resultWebServ = callWebService(user.getId()+"/"+user.getLatitude()+"/"+user.getLongitude()+"/getNearAskingHelp");
		SimpleDateFormat spdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			JSONArray jsonArray = new JSONArray(resultWebServ);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonHelp	= jsonArray.getJSONObject(i);
				Help help = new Help();
				help.setId(jsonHelp.getInt("id"));
				help.setReproducible(false);
				help.setAmount(jsonHelp.getInt("amount"));
				try {
					help.setDate(spdate.parse(jsonHelp.getJSONObject("date").getString("date")));
				} catch (ParseException e) {
					Log.d("TAG","error lors du parsing de la date "+e);
				}
				help.setDescritpion(jsonHelp.getString("description"));
				JSONObject jsonUser = jsonHelp.getJSONObject("user");
				User userWhoAsk = new User();
				userWhoAsk.setId(jsonUser.getInt("id"));
				userWhoAsk.setAmount(jsonUser.getInt("amount"));
				userWhoAsk.setFirstname(jsonUser.getString("firstname"));
				userWhoAsk.setName(jsonUser.getString("name"));
				userWhoAsk.setLatitude(jsonUser.getDouble("latitude"));
				userWhoAsk.setLongitude(jsonUser.getDouble("longitude"));
				
				help.setUser(userWhoAsk);
				colHelpAsking.add(help);
				
				//on les ajoutes dans la hasttable de l'application
				//TODO refactoring la colleciton n'est plus utile
				application.getStorage().addHelpToHashtable(help);
			}
		}catch (JSONException e) {
			return null;
		}
		return colHelpAsking;
	}
	
	public void toBeVolonteer(Help help){
		User user = application.getStorage().getUser();
		callWebService(user.getId()+"/"+help.getId()+"/insertNewVolunteer");
	}
	
	public void deleteHelp(int idHelp){
		callWebService(idHelp+"/deleteHelp");	
	}
	public void reportHelp(int idHelp){
		callWebService(idHelp+"/reportHelp");	
	}
	
	public Collection<User> getVolunteer(){
		Collection<User>colVolunteer = new ArrayList<User>();
		//recuperation de la demande d'aide
		Help askingHelp = application.getStorage().getAskingHelp();
		if(askingHelp!=null){
			String result = callWebService(askingHelp.getId()+"/getVolunteer");
			try {
				JSONArray jsonArray = new JSONArray(result);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonUser = jsonArray.getJSONObject(i);
					User volunteer = new User();
					volunteer.setId(jsonUser.getInt("id"));
					volunteer.setAmount(jsonUser.getInt("amount"));
					volunteer.setCommentaire(jsonUser.getString("commentaire"));
					volunteer.setFirstname(jsonUser.getString("firstname"));
					volunteer.setName(jsonUser.getString("name"));
					volunteer.setLatitude(jsonUser.getDouble("latitude"));
					volunteer.setLongitude(jsonUser.getDouble("longitude"));
					
					askingHelp.getHashVolunteer().put(volunteer.getId()+"", volunteer);
				}
				
			} catch (JSONException e) {
				Log.d("Error","error lors du parsin JONS "+e);
			}
		}
		
		return colVolunteer;
	}

	public void insertNewParticipant(int idUser) {
		callWebService(application.getStorage().getAskingHelp().getId()+"/"+idUser+"/insertParticipant");
		User participant = application.getStorage().getAskingHelp().getHashVolunteer().get(idUser+"");
		//suppression des elements de l'application
		application.getStorage().getAskingHelp().cleanVolunteer();
		//stockage du nouvel element
		application.getStorage().getAskingHelp().setParticipant(participant);
	}
}
