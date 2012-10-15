/**
 * @author Roussel Guillaume
 * @licence CC-BY-NC
 */
package com.exod.utopicvillage.util;

import android.widget.TextView;

public class StringUtil {
	public static String isNotNull(String chaine){
		if(chaine==null || "NULL".equals(chaine) || "null".equals(chaine) || " ".equals(chaine) || "Null".equals(chaine)){
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
	
	public static boolean isEmpty(String chaine){
		if("".equals(chaine) || " ".equals(chaine) || "null".equals(chaine) || "NULL".equals(chaine) || "Null".equals(chaine)){
			return true;
		}
		return false;
	}
	
	public static boolean textViewIsEmpty(TextView textView){
		//in this method we test if textView is null or if getText is null or empty
		if(textView==null || isEmpty(textView.getText()+"")){
			return true;
		}
		return false;
	}
}
