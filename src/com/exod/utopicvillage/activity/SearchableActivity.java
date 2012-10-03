package com.exod.utopicvillage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.exod.utopicvillage.R;

public class SearchableActivity extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.under_construct);

	    // Get the intent, verify the action and get the query
	    Intent intent = getIntent();
	    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	      //String query = intent.getStringExtra(SearchManager.QUERY);
	      //doMySearch(query);
	    }
	}
}
