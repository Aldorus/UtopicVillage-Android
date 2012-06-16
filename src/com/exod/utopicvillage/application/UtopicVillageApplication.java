package com.exod.utopicvillage.application;

import com.exod.utopicvillage.store.Storage;
import com.exod.utopicvillage.transaction.WebServiceRest;
import com.exod.utopidvillage.implement.WebServiceInterface;

import android.app.Application;

public class UtopicVillageApplication extends Application{
	Storage storage;
	String urlApi;
	WebServiceInterface webService ;
	public UtopicVillageApplication() {
		storage = new Storage();
		urlApi="http://utopic.rousselguillaume.fr/json/";
		webService = new WebServiceRest(urlApi,this);
	}

	public Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}

	public String getUrlApi() {
		return urlApi;
	}

	public void setUrlApi(String urlApi) {
		this.urlApi = urlApi;
	}

	public WebServiceInterface getWebService() {
		return webService;
	}

	public void setWebService(WebServiceInterface webService) {
		this.webService = webService;
	}
	
	
}
