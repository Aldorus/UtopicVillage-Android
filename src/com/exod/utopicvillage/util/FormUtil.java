/**
 * @author Roussel Guillaume
 * @licence CC-BY-NC
 */
package com.exod.utopicvillage.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.activity.MasterActivity;
import com.exod.utopicvillage.application.ErrorMessage;
import com.exod.utopicvillage.application.UtopicVillageApplication;
import com.exod.utopicvillage.entity.Entity;

public class FormUtil {
	
	
	//this method can insert datas of an entity into a view
	//you must passed the activity and the entity(can be empty : new Entity())
	//and the method put the properties of entity into the Textfield Object who have the same id (that the name of the property )
	//the view must be in a view group of id : table_for_element
	public static void insertDataIntoTextFieldTable(MasterActivity activity, Entity entity){
		ViewGroup view = (ViewGroup)activity.findViewById(R.id.table_for_element);
		Collection<TextView> colTextView = getTextViews(view);
		//when we have every textView we put the data into the good textView
		Field[] fields =  entity.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			int fieldId = activity.getResources().getIdentifier(field.getName(), "id", activity.getPackageName());
			for (Iterator<TextView> iterator = colTextView.iterator(); iterator.hasNext();) {
				TextView textView = (TextView) iterator.next();
				int ressourceInt = textView.getId(); 
				if(ressourceInt==fieldId){
					//match
					try {
						if(field.get(entity)!=null){
							//if date we parse the date
							if(field.getType().getCanonicalName().equals(Date.class.getCanonicalName())){
								textView.setText(DateUtil.convertToString((Date)field.get(entity)));
							}else if(field.getType().getCanonicalName().equals(Datetime.class.getCanonicalName())){
								textView.setText(DateUtil.convertToStringWithTime((Date)field.get(entity)));
							}else{
								textView.setText(field.get(entity)+"");
							}
						}
					} catch (IllegalArgumentException e) {
						Log.d("error","error dans le mapping "+e);
					} catch (IllegalAccessException e) {
						Log.d("error","error dans le mapping "+e);
					}
					break;
				}
			}
		}
	}
	
	//this method is used when, where are no needed field
	//see next for more detail
	//next : formToEntity(MagellanActivity activity, Entity entity, String[]neededInput)
	//the view must be in a view group of id : table_for_element
	public static Entity formToEntity(MasterActivity activity, Entity entity){
		return formToEntity(activity, entity, null);
	}
	
	//this method can make an object with a view
	//you can pass an string[] of the needed element and the method put the data of the textView into an entity
	//this method can construct ErrorMessage for the application, the application display the problem with data of the form
	public static Entity formToEntity(MasterActivity activity, Entity entity, String[]neededInput){
		ViewGroup view = (ViewGroup)activity.findViewById(R.id.table_for_element);
		Collection<TextView> colTextView = getTextViews(view);
		//when we have every textView we put the data into the good textView
		Field[] fields =  entity.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			//we test if the field is needed
			boolean needed=false;
			if(neededInput!=null){
				for (String string : neededInput) {
					if(string.equals(field.getName())){
						//needed
						needed=true;
					}
				}
			}
			
			int fieldId = activity.getResources().getIdentifier(field.getName(), "id", activity.getPackageName());
			for (Iterator<TextView> iterator = colTextView.iterator(); iterator.hasNext();) {
				
				TextView textView = (TextView) iterator.next();
				int ressourceInt = textView.getId(); 
				if(ressourceInt==fieldId){
					//match
					try {
						//we set the field with the label
						int ressourceString = activity.getResources().getIdentifier(field.getName(), "string", activity.getPackageName());
						String labelField =field.getName();
						try{
							labelField = activity.getResources().getString(ressourceString);
						}catch (Exception e) {
							Log.d("trace","id or string not found "+e);
						}
						//we set the element by the type of field
						if(field.getType().getCanonicalName().equals(int.class.getCanonicalName())){
							try {
								field.set(entity, Integer.parseInt(textView.getText()+""));
							} catch (Exception e) {
								//error during parsing
								Log.d("error", "error during parsing of int "+e);
								if(needed){
									((UtopicVillageApplication)activity.getApplication()).addErrorMessage(new ErrorMessage(labelField+": "+((UtopicVillageApplication)activity.getApplication()).getResources().getString(R.string.is_in_error)));
								}
							}
						}else if(field.getType().getCanonicalName().equals(long.class.getCanonicalName())){
							try {
								field.set(entity, Long.parseLong(textView.getText()+""));
							} catch (Exception e) {
								//error during parsing
								Log.d("error", "error during parsing of long "+e);
								if(needed){
									((UtopicVillageApplication)activity.getApplication()).addErrorMessage(new ErrorMessage(labelField+" "+((UtopicVillageApplication)activity.getApplication()).getResources().getString(R.string.is_in_error)));
								}
							}
						}else if(field.getType().getCanonicalName().equals(double.class.getCanonicalName())){
							try {
								field.set(entity, Double.parseDouble(textView.getText()+""));
							} catch (Exception e) {
								//error during parsing
								Log.d("error", "error during parsing of double "+e);
								if(needed){
									((UtopicVillageApplication)activity.getApplication()).addErrorMessage(new ErrorMessage(labelField+" "+((UtopicVillageApplication)activity.getApplication()).getResources().getString(R.string.is_in_error)));
								}
							}
						}else if(field.getType().getCanonicalName().equals(boolean.class.getCanonicalName())){
							try {
								if("true".equals(textView.getText()+"")){
									field.set(entity,true); 
								}else{
									field.set(entity,false);
								}
								
							} catch (Exception e) {
								//error during parsing
								Log.d("error", "error during parsing of boolean "+e);
								if(needed){
									((UtopicVillageApplication)activity.getApplication()).addErrorMessage(new ErrorMessage(labelField+" "+((UtopicVillageApplication)activity.getApplication()).getResources().getString(R.string.is_in_error)));
								}
							}
						}else if(field.getType().getCanonicalName().equals(Datetime.class.getCanonicalName())){
							try {
								field.set(entity, DateUtil.convertToDateTime(textView.getText()+""));
							} catch (Exception e) {
								//error during parsing
								Log.d("error", "error during parsing of date "+e);
								if(needed){
									((UtopicVillageApplication)activity.getApplication()).addErrorMessage(new ErrorMessage(labelField+" "+((UtopicVillageApplication)activity.getApplication()).getResources().getString(R.string.format_needed)));
								}
							}
						}else if(field.getType().getCanonicalName().equals(Date.class.getCanonicalName())){
							try {
								field.set(entity, DateUtil.convertToDate(textView.getText()+""));
							} catch (Exception e) {
								//error during parsing
								Log.d("error", "error during parsing of date "+e);
								if(needed){
									((UtopicVillageApplication)activity.getApplication()).addErrorMessage(new ErrorMessage(labelField+" "+((UtopicVillageApplication)activity.getApplication()).getResources().getString(R.string.format_needed)));
								}
							}
						}else if(field.getType().getCanonicalName().equals(String.class.getCanonicalName())){
							try {
								if(needed && "".equals(textView.getText()+"")){
									((UtopicVillageApplication)activity.getApplication()).addErrorMessage(new ErrorMessage(labelField+" "+((UtopicVillageApplication)activity.getApplication()).getResources().getString(R.string.is_in_error)));
								}else{
									field.set(entity, textView.getText()+"");
								}
							} catch (Exception e) {
								//error during parsing
								Log.d("error", "error during parsing of string "+e);
								if(needed){
									((UtopicVillageApplication)activity.getApplication()).addErrorMessage(new ErrorMessage(labelField+" "+((UtopicVillageApplication)activity.getApplication()).getResources().getString(R.string.is_in_error)));
								}
							}
						}else{
							Log.d("error","Type de donnée non prise en charge");
						}
					} catch (IllegalArgumentException e) {
						Log.d("error","error dans le mapping "+e);
					}
					break;
				}
			}
		}
		
		return entity;
	}
	
	//this method can find all view who are TextView (or decend) with id into a view Group
	public static Collection<TextView> getTextViews(ViewGroup view){
		Collection<TextView> colTextView = new ArrayList<TextView>();
		//we search all children (the row) and we find all textField
		for (int i = 0; i < view.getChildCount(); i++) {
			if(view.getChildAt(i) instanceof ViewGroup){
				ViewGroup tableRow = (ViewGroup) view.getChildAt(i);
				//recurcivity
//				colTextView.addAll(getTextViews(tableRow));
				for (int j = 0; j < tableRow.getChildCount(); j++) {
					if(tableRow.getChildAt(j) instanceof TextView){
						TextView tview = (TextView)tableRow.getChildAt(j);
						if(tview.getId()!=0){
							colTextView.add(tview);
						}
					}
				}
			}else if(view.getChildAt(i) instanceof TextView){
				TextView tview = (TextView)view.getChildAt(i);
				if(tview.getId()!=0){
					colTextView.add(tview);
				}
			}
		}
		return colTextView;
	}
}
