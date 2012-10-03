package com.exod.utopicvillage.util;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;

import android.util.Log;

import com.exod.utopicvillage.entity.Entity;

public class WebServiceUtil {
	
	//this method can be used for convert an object Entity to a string of parameters
	//this method encode the parameter in utf-8 and the result can be passed in GET method
	public static String entityToParameterString(Entity entity){
		//we convert an entity to a string for the webService
		StringBuffer string = new StringBuffer();
		
		Field[] fields = entity.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				//and we encode the parameters
				string.append("&"+field.getName()+"="+URLEncoder.encode(field.get(entity)+"", "UTF-8"));
			} catch (IllegalArgumentException e) {
				Log.d("error","error during the parsing "+e);
			} catch (IllegalAccessException e) {
				Log.d("error","error during the parsing "+e);
			} catch (UnsupportedEncodingException e) {
				Log.d("error","error during the encode url "+e);
			}
		}
		return string.toString();
	}
	
	public static String encoderUrl(String request){
		//with this method we can encode all parameter of one url
		String paramReturn = "";
		String[] requestSplited = request.split("/");
		for (int i = 0; i < requestSplited.length-1; i++) {
			paramReturn+="/"+URLEncoder.encode(requestSplited[i]);
		}
		paramReturn+="/"+requestSplited[requestSplited.length-1];
		return paramReturn;
	}
}
