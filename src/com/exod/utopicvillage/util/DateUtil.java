package com.exod.utopicvillage.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	static SimpleDateFormat spdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String convertToString(Date event){
		SimpleDateFormat sp = new SimpleDateFormat("dd/MM/yyyy");
		return sp.format(event);
	}
	
	public static String convertToStringDifDate(Date event){
		//pour une comparaison avec la date d'aujourd'hui
		return convertToStringDifDate(event, new Date());
	}
	//TODO probleme internationalisation
	public static String convertToStringDifDate(Date event, Date compareTo){
		//on converie la difference entre les deux dates en un temps 
		// il y a 1seconde
		// il y a 1 minute
		// il y a 1 heure
		// il y a 1 jour
		// il y a 1 mois
		// il y a 1 an
		
		//on set un calendar avec la difference de temps entre les deux
		long time = compareTo.getTime()-event.getTime();
		
		if(time/(1000*60*60*24*30*12)>=1){
			//il y a plus d'un an
			if(time/(1000*60*60*24*30*12)>=2){
				return time/(1000*60*60*24*30*12)+" ans";
			}else{
				return time/(1000*60*60*24*30*12)+" an";
			}
			
			//TODO nombre de jour par mois
		}else if(time/(1000*60*60*24*30)>=1){
			//il y a plus d'un mois
			return time/(1000*60*60*24*30)+" mois";
			
		}else if(time/(1000*60*60*24)>=1){
			//il y a plus d'un jour
			if(time/(1000*60*60*24)>=2){
				return time/(1000*60*60*24)+" jours";
			}else{
				return time/(1000*60*60*24)+" jour";
			}
			
		}else if(time/(1000*60*60)>=1){
			//il y a plus d'une heure
			if(time/(1000*60*60)>=2){
				return time/(1000*60*60)+" heures";
			}else{
				return time/(1000*60*60)+" heure";
			}
			
		}else if(time/(1000*60)>=1){
			//il y a plus d'une minute
			if(time/(1000*60)>=2){
				return time/(1000*60)+" minutes";
			}else{
				return time/(1000*60)+" minute";
			}
			
		}else if(time/(1000)>=1){
			//il y a plus d'une seconde
			if(time/(1000)>=2){
				return time/(1000)+" secondes";
			}else{
				return time/(1000)+" seconde";
			}
		}else {
			return "quelques secondes";
		}
	}
}
