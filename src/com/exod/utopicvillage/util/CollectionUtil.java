/**
 * @author Roussel Guillaume
 * @licence CC-BY-NC
 */
package com.exod.utopicvillage.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;

import com.exod.utopicvillage.entity.User;

public class CollectionUtil {
	
	public static Hashtable<String, Collection<String>> convertHashToHashForUser(Hashtable<Integer,User>hashVolunteer){
		Hashtable<String, Collection<String>>hastableUser = new Hashtable<String, Collection<String>>();
		
		Collection<String> colId = new ArrayList<String>();
		Collection<String> colName = new ArrayList<String>();
		Collection<String> colDesc = new ArrayList<String>();
		Collection<String> colPointReputation = new ArrayList<String>();
		
		//parcours de la hastable
		Enumeration<User> emunameration = hashVolunteer.elements();
		while ( emunameration.hasMoreElements()) { 
			User volunteer = (User) emunameration.nextElement();
			colId.add(volunteer.getId()+"");
			colName.add(volunteer.getName()+" "+volunteer.getFirstname());
			colDesc.add(volunteer.getCommentaire()+"");
			//TODO reputation
			colPointReputation.add("0");
		}
		
		hastableUser.put("id", colId);
		hastableUser.put("name", colName);
		hastableUser.put("desc", colDesc);
		hastableUser.put("point_reputation", colPointReputation);
		
		return hastableUser;
	}
}
