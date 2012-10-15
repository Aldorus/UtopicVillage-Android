/**
 * @author Roussel Guillaume
 * @licence CC-BY-NC
 */
package com.exod.utopicvillage.application;

public class Constante {
	
	//Url of server
//	private static final String url = "http://10.0.2.2/UtopicVillage/web/app_dev.php";
	private static final String url = "http://utopic.rousselguillaume.fr";
	
	//url of webservice access
	public static final String urlApi = url+"/json";
	
	//url of documents
	public static final String urlFile = url+"/tmpfile";
	
	//time for time out
	public static final int timeOutConnection = 20000;
	public static final int timeOutSocket = 20000;
	
	//number of element to be displayed on the map
	public static final int LIMIT_PIN_MAP = 29;
	
	//lenght of the string will be splited be StringUtil.isNotNullShort() method
	public static final int LIMIT_SPLIT_STRING = 100;
	public static final double TOLERANCE_SEARCH = 0.5;
	
	//pattern for Date and Datetime class
	public static final String DATE_PATTERN = "dd/MM/yyyy";
	public static final String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm";
	public static final String DATE_TIME_FULL_PATTERN = "dd/MM/yyyy HH:mm:ss";
	public static final String DATE_PATTERN_SERVER = "yyyy-MM-dd HH:mm:ss";
}
