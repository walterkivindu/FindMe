package com.example.findme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ContactList extends ListFragment{
 
    /** An array of items to display in ArrayList */
    String users[] = new String[]{
        "Cherono",
        "Ronoh",
        "Crispus Mbithi",
        "Jesse MJ",
        "Titus Walcott",
        "Many Fellows"
    };
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
    	
    	SqliteController dbhelper = new SqliteController(getActivity());
		//String get[] = new String[]{"No record","Found"};
		String user[] =dbhelper.getAllUsers();
		if (user == null){
			user=users;
		}
    	
        /** Creating array adapter to set data in listview */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(),
        		android.R.layout.simple_list_item_1, user);
 
        /** Setting the array adapter to the listview */
        setListAdapter(adapter);
        
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    
    
  
	
	public void onListItemClick(ListView lv, View v, int pos, long id) {
		super.onListItemClick(lv, v, pos, id);
		Object o = this.getListAdapter().getItem(pos);
        String posi = o.toString();
        String posit=posi.substring(0, 2);
		Intent i = new Intent(getActivity(), Contact_In_Detail.class);
		i.putExtra("id", posit);
		startActivity(i);
		Toast.makeText(getActivity(), posit, Toast.LENGTH_SHORT).show();

	}
 
    @Override
    public void onStart() {
        super.onStart();
        /** Setting the multiselect choice mode for the listview */
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
}
