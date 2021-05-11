package com.example.findme;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Contact_Message extends Activity {
	Button send;
	EditText msg;
	SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		db = this.openOrCreateDatabase("find.db", Context.MODE_PRIVATE, null);
		//String phone=this.getIntent().getStringExtra("phone");
		String name=getUserName();
		super.onCreate(savedInstanceState);
		setTitle(name);
		setContentView(R.layout.contact_message);
		send = (Button) findViewById(R.id.btnSendmsg);
		msg = (EditText) findViewById(R.id.txtMsg);
		final SqliteController1 data=new SqliteController1(this);
		send.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Bundle i = getIntent().getExtras();
				String phoneNo = i.getString("phone");
				String message = msg.getText().toString();
				try {
					Calendar cal = Calendar.getInstance();
					int day,month,year;
					day=cal.get(Calendar.DAY_OF_MONTH);
					month=cal.get(Calendar.MONTH);
					year=cal.get(Calendar.YEAR);
					String date=day+"/"+(month+1)+"/"+year;
					String number=getNumber();
					data.insertMsg(phoneNo, number, message, date);
					Toast.makeText(getApplicationContext(), "Message sent.",
							Toast.LENGTH_LONG).show();
				}

				catch (Exception e) {
					Toast.makeText(getApplicationContext(),
							"An error occured!", Toast.LENGTH_LONG)
							.show();
					e.printStackTrace();
				}
			}
		});
	}
	public String getUserName(){
		String name;
		Bundle i = getIntent().getExtras();
		String phoneNo = i.getString("phone");
		Cursor c = db.rawQuery("select * from users where phone='"+phoneNo+"'", null);
		if (c.moveToNext()) {
			name=c.getString(4);
			return name;
		}
		return null;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	public String getNumber(){
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String serial = tm.getSimSerialNumber();
		/*String imei = tm.getDeviceId();
		String number=tm.getLine1Number();*/
		String phone;
		Cursor c = db.rawQuery("select * from users where serial='"+serial+"'", null);
		if (c.moveToNext()) {
			phone=c.getString(3);
			return phone;
		}
		return null;
	}

	
	

}
