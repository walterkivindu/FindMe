package com.example.findme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListFoundChild extends ActionBarActivity {
	ListView listAccounts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foundchild);
		
		
		
		listAccounts = (ListView) this.findViewById(R.id.listAccounts);
		listAccounts.setOnItemClickListener( new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View selectedView, int arg2,long arg3) {
				TextView  textAccountId = (TextView) selectedView.findViewById(R.id.txtId);
				Log.d("Child", "Selected Child Id : " + textAccountId.getText().toString());
				Intent intent = new Intent(ListFoundChild.this, UpdateFoundChild.class);
				intent.putExtra("accountid", textAccountId.getText().toString());
				startActivity(intent);
			}
		});
	}
	@Override 
	public void onStart() {
		super.onStart();
		try {
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ListFoundChild.this);
			SqliteController dbhelper = new SqliteController(this);
			SQLiteDatabase db = dbhelper.getReadableDatabase();
			Cursor  accounts = db.query( false,"found",null,null,null,null,null, null, null);
			
			String from [] = { "_id","cname","ffrom", "dob", "place" };
			int to [] = { R.id.txtId,R.id.tv_name,R.id.tv_email, R.id.tv_phone, R.id.place};
			
			/*String from [] = { "_id","fname","sname", "dob", "place" };
			int to [] = { R.id.txtId,R.id.tv_name,R.id.tv_email, R.id.tv_phone, R.id.place};*/
			@SuppressWarnings("deprecation")
			SimpleCursorAdapter ca  = new SimpleCursorAdapter(this,R.layout.child1, accounts,from,to);
			
		    ListView listAccounts = (ListView) this.findViewById( R.id.listAccounts);
		    listAccounts.setAdapter(ca);
		    dbhelper.close();
		} catch (Exception ex) {
			Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
		}
	}




}
