/**
 * @author Roussel Guillaume
 * @licence CC-BY-NC
 */
package com.exod.utopicvillage.util;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.exod.utopicvillage.entity.Entity;

public class JSONParser {	
	//this method return a object Entity with a JSON object and the type of Entity
	//this method parse the element which in the JSON object into the property of entity 
	public static Entity toEntity(JSONObject jsonObject, Entity entity) throws JSONException, NumberFormatException, IllegalArgumentException, IllegalAccessException{
		@SuppressWarnings("unchecked")
		Iterator<String> iterator = jsonObject.keys();
		while(iterator.hasNext()){
			String key = (String)iterator.next(); 
			Field[]fields = entity.getClass().getDeclaredFields();
			//we range the fields and the key
			for (Field field : fields) {
				field.setAccessible(true);
				if(key.equals(field.getName()) && !jsonObject.isNull(key)){
					//match
					String canonicalName = field.getType().getCanonicalName();
					//convert and parse to string format
					if(int.class.getCanonicalName().equals(canonicalName)){
						field.set(entity, Integer.parseInt(jsonObject.getInt(key)+""));
					}else if(long.class.getCanonicalName().equals(canonicalName)){
						field.set(entity, Long.parseLong(jsonObject.getInt(key)+""));
					}else if(double.class.getCanonicalName().equals(canonicalName)){
						field.set(entity, Double.parseDouble(jsonObject.getDouble(key)+""));
					}else if(boolean.class.getCanonicalName().equals(canonicalName)){
						if(jsonObject.getBoolean(key)) {
							field.set(entity,true);
						}else{
							field.set(entity,false);
						}
					}else if(Date.class.getCanonicalName().equals(canonicalName)){
						field.set(entity, DateUtil.convertToDate(jsonObject.getJSONObject(key).getString("date")+""));
					}else if(Datetime.class.getCanonicalName().equals(canonicalName)){
						field.set(entity, (Datetime)(DateUtil.convertToDateTime(jsonObject.getJSONObject(key).getString("date")+"")));
					}else if(String.class.getCanonicalName().equals(canonicalName)){
						field.set(entity, jsonObject.getString(key)+"");
					}else{
						Log.d("error","Type de donnée non prise en charge");
					}
				}
			}
		}
		return entity;
	}
}
