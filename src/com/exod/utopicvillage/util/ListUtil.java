package com.exod.utopicvillage.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.exod.utopicvillage.entity.ElementListInterface;

public class ListUtil {
	
	//this method is used for put the element passed in argument in the first place of a list
	//return a List
	public static List<ElementListInterface> sortToFirst(List<ElementListInterface>colEntity, long firstElementId){
		List<ElementListInterface> colSorted = new ArrayList<ElementListInterface>();
		List<ElementListInterface> colParcours = new ArrayList<ElementListInterface>(colEntity);
		for (Iterator<ElementListInterface> iterator = colEntity.iterator(); iterator.hasNext();) {
			ElementListInterface elementListInterface = (ElementListInterface) iterator.next();
			if(elementListInterface.getId()==firstElementId){
				//we match the first element
				colSorted.add(elementListInterface);
				colParcours.remove(elementListInterface);
				break;
			}
		}
		colSorted.addAll(colParcours);
		return colSorted;
	}
}
