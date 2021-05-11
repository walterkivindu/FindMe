package com.example.findme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Contact_In_Detail extends Activity {
	SQLiteDatabase db;
	ImageView image1;
	FrameLayout fl,fl1;
	TextView name, email, call,call1, msg,msg1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		db = this.openOrCreateDatabase("find.db", Context.MODE_PRIVATE, null);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_in_detail);
		
		image1 = (ImageView) findViewById(R.id.image1);
		name = (TextView) findViewById(R.id.txtName);
		email = (TextView) findViewById(R.id.txtEmail);
		call = (TextView) findViewById(R.id.txtCall);
		call1 = (TextView) findViewById(R.id.txtCall1);
		msg = (TextView) findViewById(R.id.txtmsg);
		msg1 = (TextView) findViewById(R.id.txtmsg1);
		fl=(FrameLayout)findViewById(R.id.frame1);
		fl1=(FrameLayout)findViewById(R.id.frame2);
		//registerForContextMenu(msg);
		//registerForContextMenu(msg1);
		registerForContextMenu(fl);
		call.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				openCaller();
			}
		});
		fl1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				openCaller();
			}
		});
		msg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// openMessager();
			}
		});
		pupulate();

	}

	private void openCaller() {
		// TODO Auto-generated method stub
		String number;
		String id = this.getIntent().getStringExtra("id");
		Cursor c = db.rawQuery("select * from users where _id='" + id + "'",
				null);// +id+"'"
		if (c.moveToNext()) {
			number = c.getString(3);
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:" + number));
			startActivity(callIntent);
		}

	}

	private void openMessager() {
		// TODO Auto-generated method stub

	}

	public void pupulate() {

		Bundle i = getIntent().getExtras();
		// Intent i=getIntent();
		String id = i.getString("id");
		// String id=this.getIntent().getStringExtra("id");
		String nam, eml, no;

		Cursor c = db.rawQuery("select * from users where _id='" + id + "'",
				null);// +id+"'"
		if (c.moveToNext()) {
			nam = c.getString(4);
			no = c.getString(3);
			name.setText(nam);
			email.setText("No Email");
			call.setText(no);
			msg.setText(no);
			// byte[] img = c.getBlob(7);
			// Bitmap bm = BitmapFactory.decodeByteArray(img, 0, img.length);
			// image1.setImageBitmap(bm);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Select The Action");
		menu.add(0, v.getId(), 0, "Message");// groupId, itemId, order, title
		menu.add(0, v.getId(), 0, "SMS");
	}

	public boolean onContextItemSelected(MenuItem item) {
		if (item.getTitle() == "Message") {
			Bundle i = getIntent().getExtras();
			String id = i.getString("id");
			String  no;

			Cursor c = db.rawQuery("select * from users where _id='" + id + "'",
					null);
			if (c.moveToNext()) {
				no = c.getString(3);
				Intent in = new Intent(getApplicationContext(), Contact_Message.class);
				in.putExtra("phone", no);
				startActivity(in);
			}
			
		} else if (item.getTitle() == "SMS") {
			Bundle i = getIntent().getExtras();
			String id = i.getString("id");
			String  no;

			Cursor c = db.rawQuery("select * from users where _id='" + id + "'",
					null);
			if (c.moveToNext()) {
				no = c.getString(3);
				Intent in = new Intent(getApplicationContext(), Contact_sms.class);
				in.putExtra("phone", no);
				startActivity(in);
			}
			
		} else {
			return false;
		}
		return true;
	}

}
