package com.exod.utopidvillage.implement;

import java.util.Collection;

import com.exod.utopicvillage.entity.Help;

public interface WebServiceInterface {
	abstract boolean testConnect(String email,String password);
	abstract boolean insertHelp(Help help);
	abstract Help yourAskingHelp();
	abstract void setLatitudeLongitude(double latitude,double longitude);
	abstract Collection<Help> getNearAskingHelp();
	abstract void toBeVolonteer(Help help);
}
