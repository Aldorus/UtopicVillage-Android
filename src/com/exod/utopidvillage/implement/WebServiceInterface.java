package com.exod.utopidvillage.implement;

import java.util.Collection;

import com.exod.utopicvillage.entity.Help;
import com.exod.utopicvillage.entity.User;

public interface WebServiceInterface {
	abstract boolean testConnect(String email,String password);
	abstract boolean insertHelp(Help help);
	abstract Help yourAskingHelp();
	abstract void setLatitudeLongitude(double latitude,double longitude);
	abstract Collection<Help> getNearAskingHelp();
	abstract void toBeVolonteer(Help help);
	abstract void deleteHelp(int idHelp);
	abstract void reportHelp(int idHelp);
	abstract Collection<User> getVolunteer();
	abstract void insertNewParticipant(int idUser);
}
