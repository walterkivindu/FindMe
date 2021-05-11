package com.example.findme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ViewLost  extends Activity{
	SQLiteDatabase db1;
	 SqliteController db = new SqliteController(this);
	// Array of strings storing country names
    String[] names = db.getNames();
    
    
 // Array of integers points to images stored in /res/drawable-ldpi/
    int[] images = new int[]{
            R.drawable.cruise,
            R.drawable.cursor,
            R.drawable.denied,
            R.drawable.dev,
            R.drawable.dolly,
            R.drawable.door,
            R.drawable.download,
            R.drawable.easel,
            R.drawable.eye,
            R.drawable.email
        };
    
    
    
 // Array of strings to store currencies
    String[] places= db.getPlaces();
 
	
	
	
   

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//SQLiteDatabase db=openOrCreateDatabase("FindMe",MODE_PRIVATE,null);
		//this.db1=db;
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewlost);
		
		// Each row in the list stores country name, currency and flag
        List<HashMap<String,String>> aList =  new ArrayList<HashMap<String,String>>();
 
        for(int i=0;i<10;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("txt", "Names : " + names[i]);
            hm.put("cur","Places : " + places[i]);
           // hm.put("flag", Integer.toString(images[i]) );
            aList.add(hm);
        }
        
        
        
     // Keys used in Hashmap
        String[] from = { "flag","txt","cur" };
        //String[] from = { "txt","cur" };
 
        // Ids of views in listview_layout
        int[] to = { R.id.flag,R.id.txt,R.id.cur};
        //int[] to = { R.id.txt,R.id.cur};
 
        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.list2, from, to);
 
        // Getting a reference to listview of main.xml layout file
        ListView listView = ( ListView ) findViewById(R.id.listview);
 
        // Setting the adapter to the listView
        listView.setAdapter(adapter);
        
        
        
     // Item Click Listener for the listview
        OnItemClickListener itemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                // Getting the Container Layout of the ListView
                LinearLayout linearLayoutParent = (LinearLayout) container;
 
                // Getting the inner Linear Layout
                LinearLayout linearLayoutChild = (LinearLayout ) linearLayoutParent.getChildAt(1);
 
                // Getting the Country TextView
                TextView tvCountry = (TextView) linearLayoutChild.getChildAt(0);
 
                Toast.makeText(getBaseContext(), tvCountry.getText().toString(), Toast.LENGTH_SHORT).show();
                
                
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(i);
            }

			
        };
 
        // Setting the item click listener for the listview
        listView.setOnItemClickListener(itemClickListener);
        
		
		
	}
		

}
