package com.exod.utopicvillage.util;

public class StringUtil {
	public static String isNotNull(String chaine){
		if("NULL".equals(chaine) || "null".equals(chaine) || " ".equals(chaine) || "Null".equals(chaine)){
			return "";
		}
		return chaine;
	}
	
	public static String isNotNullShort(String chaine){
		if("NULL".equals(chaine) || "null".equals(chaine) || " ".equals(chaine) || "Null".equals(chaine)){
			return "";
		}
		
		//puis decoupage de la chaine
		if(chaine.length()>100){
			return chaine.substring(0,100);
		}
		return chaine;
	}
}