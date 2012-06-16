package com.exod.utopicvillage.adaptater;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.exod.utopicvillage.R;

public class YourAskingHelpAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] names;
	private final String[] dates;
	private final String[] ids;
	private final String[] descs;
	private final String[] amounts;

	static class ViewHolder {
		//utilisation du pattern holder
		public TextView text;
		public TextView date;
		public TextView id;
		public TextView desc;
		public TextView amount;
	}
	
	public YourAskingHelpAdapter(Activity context, String[] names, String[] date, String[] id, String[] desc, String[]amount) {
		super(context, R.layout.row_for_asking_help, names);
		this.context = context;
		this.names = names;
		this.dates = date;
		this.ids = id;
		this.descs = desc; 
		this.amounts = amount;
		
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			rowView = inflater.inflate(R.layout.row_for_asking_help, null);
			ViewHolder viewHolder = new ViewHolder();
			
			//recuperation des champs pour les elements
			viewHolder.text = (TextView) rowView.findViewById(R.id.textCell);
			viewHolder.date = (TextView) rowView.findViewById(R.id.sousTextCell);
			viewHolder.id = (TextView) rowView.findViewById(R.id.idCell);
			viewHolder.desc = (TextView) rowView.findViewById(R.id.descCell);
			viewHolder.amount = (TextView) rowView.findViewById(R.id.amountCell);
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		
		//on affiche les informations
		String s = names[position];
		String date = dates[position];
		String id = ids[position];
		String desc = descs[position];
		String amount = amounts[position];
		holder.text.setText(s);
		holder.date.setText(date);
		holder.id.setText(id);
		holder.desc.setText(desc);
		holder.amount.setText(amount);
		
		//on retourne la cellule complété
		return rowView;
	}
}
