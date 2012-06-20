package com.exod.utopicvillage.util;

import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.exod.utopicvillage.entity.Help;
import com.exod.utopicvillage.entity.User;

public class ParsingUtil {
	//class static permettant de parser les donnée d'un json object en USER/HELP
	
	public static User toUser(JSONObject jsonObject) throws JSONException{
		User user = new User();
		user.setId(jsonObject.getInt("id"));
		user.setAmount(jsonObject.getInt("amount"));
		user.setFirstname(jsonObject.getString("firstname"));
		user.setName(jsonObject.getString("name"));
		user.setLatitude(jsonObject.getDouble("latitude"));
		user.setLongitude(jsonObject.getDouble("longitude"));
		user.setEmail(jsonObject.getString("email"));
		try {
			user.setBirthdate(DateUtil.spdate.parse(jsonObject.getJSONObject("birthdate").getString("date")));
		} catch (ParseException e) {
			Log.d("TAG","error lors du parsing de la date "+e);
		}
		return user;

	}
	
	public static Help toHelp(JSONObject jsonObject) throws JSONException{
		Help help = new Help();
		help.setId(jsonObject.getInt("id"));
		help.setReproducible(false);
		help.setAmount(jsonObject.getInt("amount"));
		try {
			help.setDate(DateUtil.spdate.parse(jsonObject.getJSONObject("date").getString("date")));
		} catch (ParseException e) {
			Log.d("TAG","error lors du parsing de la date "+e);
		}
		help.setDescritpion(jsonObject.getString("description"));
		return help;
	}
}
