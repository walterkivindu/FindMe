package com.example.findme;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Menu extends ListActivity{
	
	String classes[]={"AddFound","AddLost","ListLostChild","ListFoundChild","Chat","Notice","Contacts","Donate"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(Menu.this, android.R.layout.simple_list_item_1, classes));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		String getclass=classes[position];
		try{
			Class<?> newclass=Class.forName("com.example.findme."+getclass);
			Intent i=new Intent(getApplicationContext(),newclass);
			startActivity(i);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}

	
	

}
