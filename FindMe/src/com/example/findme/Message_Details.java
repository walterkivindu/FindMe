package com.example.findme;

import java.util.Calendar;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Message_Details extends Activity{
	TextView  msg,msg1;
	SQLiteDatabase db;
	Button send;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		db = this.openOrCreateDatabase("find.db", Context.MODE_PRIVATE, null);
		setContentView(R.layout.message_detail);
		msg = (TextView) findViewById(R.id.txtmsg);
		msg1 = (TextView) findViewById(R.id.txtMsgR);
		send = (Button) findViewById(R.id.btnSendmsg);
		//final SqliteController1 data=new SqliteController1(this);
		SqliteController1 dbhelper = new SqliteController1(this);
		final SQLiteDatabase db = dbhelper.getWritableDatabase();
		/*send.setOnClickListener(new View.OnClickListener() {
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
					//String number=getNumber();
					//data.insertMsg(phoneNo, phoneNo, message, date);
					
					ContentValues values = new ContentValues();
					values.put("lto", phoneNo);
					values.put("ffrom", phoneNo);
					values.put("msg", message);
					values.put("date",  date);
					String sql="insert into msg values( '"+phoneNo+"', '"+phoneNo+"','"+message+"','"+date+"')";
					db.execSQL(sql);
					db.close(); 
					
						Toast.makeText(getApplicationContext(), "Message sent.",
								Toast.LENGTH_LONG).show();
					
				}

				catch (Exception e) {
					Toast.makeText(getApplicationContext(),
							"SMS faild, please try again.", Toast.LENGTH_LONG)
							.show();
					e.printStackTrace();
				}
			}
		});*/
		load();
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
	
	public void load(){
		
		 Bundle i=getIntent().getExtras();
  	 //Intent i=getIntent();
		String phone=i.getString("phone");
		//String id=this.getIntent().getStringExtra("id");
		String msg;
		
		Cursor c = db.rawQuery("select * from msg where ffrom='"+phone+"'", null);//+id+"'"
		if (c.moveToNext()) {
			msg=c.getString(2);
			
			msg1.setText(msg);
		}
	}
	

}
