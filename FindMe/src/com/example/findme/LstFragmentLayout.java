package com.example.findme;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class LstFragmentLayout extends ListFragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle saveinstance) {

		ViewGroup rootview = (ViewGroup) inflater.inflate(
				R.layout.fragmentlayout, container, false);
		SqliteController1 dbhelper = new SqliteController1(getActivity());
		String get[] = dbhelper.getAllNames();
		

		// Creating array adapter to set data in listview
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				R.layout.rawlayout, R.id.txtitem, get);

		// Setting the array adapter to the listview
		setListAdapter(adapter);
		setRetainInstance(true);

		return rootview;

	}

	public void onListItemClick(ListView lv, View v, int pos, long id) {
		Intent i = new Intent(getActivity(), Found_details.class);
		i.putExtra("id", pos);
		startActivity(i);
		Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();

	};

}
