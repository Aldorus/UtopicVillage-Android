package com.exod.utopicvillage.util;

public class IntegerUtil {

	public static boolean isInt(String chaineToTest){
		//verification de l'entier
		try{
			Integer.parseInt(chaineToTest);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
}
