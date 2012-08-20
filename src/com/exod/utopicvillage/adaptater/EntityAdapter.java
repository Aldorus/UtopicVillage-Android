package com.exod.utopicvillage.adaptater;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.activity.MasterActivity;
import com.exod.utopicvillage.entity.Entity;
import com.exod.utopicvillage.util.DateUtil;
import com.exod.utopicvillage.util.Datetime;
import com.exod.utopicvillage.util.FormUtil;

public class EntityAdapter extends ArrayAdapter<Entity> {
	//this class used as an adaptator for the element of type Entity
	//with this class who can build easily an ListView
	
	public EntityAdapter(Context context, int textViewResourceId) {
	    super(context, textViewResourceId);
	}

	private List<Entity> entities;
	int ressource;
	MasterActivity activity;
	
	public EntityAdapter(List<Entity> entities, MasterActivity activity) {
	    super(activity.getApplication(), R.layout.cell_list, entities);
	    this.entities = entities;
	    this.ressource = R.layout.cell_list;
	    this.activity = activity;
	}
	
	public EntityAdapter(Context context, int resource, List<Entity> entities, MasterActivity activity) {
	    super(context, resource, entities);
	    this.entities = entities;
	    this.ressource = resource;
	    this.activity = activity;
	}
	
	//this method is the main method of the class
	//it add an element of entity into a view (viewgroup)
	//after who can get this element easily with the classic listener for a listView 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewGroup v = (ViewGroup)convertView;
	    LayoutInflater vi=null;
	    if (v == null) {
	        vi = LayoutInflater.from(getContext());
	        v = (ViewGroup)vi.inflate(ressource, null);
	    }
	    
	    Entity entity = entities.get(position);
	    if (entity != null && v!=null) {
	    	//we use the reflexion for find everything
	    	Collection<TextView> colTextView = FormUtil.getTextViews(v);
	    	
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
								//when is a date
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
	    return v;
	}
}
