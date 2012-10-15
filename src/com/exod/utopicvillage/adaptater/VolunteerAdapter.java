/**
 * @author Roussel Guillaume
 * @licence CC-BY-NC
 */
package com.exod.utopicvillage.adaptater;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.exod.utopicvillage.R;
import com.exod.utopicvillage.util.StringUtil;

public class VolunteerAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] ids;
	private final String[] names;
	private final String[] descs;
	private final String[] pointsReputation;
	
	static class ViewHolder {
		//utilisation du pattern holder
		public TextView id;
		public TextView name;
		public TextView desc;
		public TextView pointReputation;
		
		//bouton for tag
		public Button takeVolonteer;
		public Button seeDetail;
	}
	
	public VolunteerAdapter(Activity context, String[]names, String[]id, String[]desc, String[]pointReputation) {
		super(context, R.layout.row_for_user, names);
		this.context = context;
		this.names = names;
		
		this.pointsReputation = pointReputation;
		this.ids = id;
		this.descs = desc;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			rowView = inflater.inflate(R.layout.row_for_user, null);
			ViewHolder viewHolder = new ViewHolder();
			
			//recuperation des champs pour les elements
			viewHolder.id = (TextView) rowView.findViewById(R.id.idCell);
			viewHolder.name = (TextView) rowView.findViewById(R.id.textCell);
			viewHolder.desc = (TextView) rowView.findViewById(R.id.sousTextCell);
			viewHolder.pointReputation = (TextView) rowView.findViewById(R.id.point_reputation);
			
			viewHolder.seeDetail = (Button)rowView.findViewById(R.id.id_take_volunteer);
			viewHolder.takeVolonteer = (Button)rowView.findViewById(R.id.id_see_detail);
			
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		
		//on affiche les informations
		String id = ids[position];
		String name = names[position];
		String desc = descs[position];
		String pointReputation = pointsReputation[position];
		
		holder.id.setText(id);
		holder.name.setText(name);
		holder.desc.setText(StringUtil.isNotNullShort(desc));
		holder.pointReputation.setText(context.getResources().getString(R.string.fame)+" "+pointReputation);
		holder.seeDetail.setTag(id+"");
		holder.takeVolonteer.setTag(id+"");
		//on retourne la cellule complété
		return rowView;
	}
}
