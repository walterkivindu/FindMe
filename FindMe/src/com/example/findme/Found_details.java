package com.example.findme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Found_details extends Activity {
	
	SQLiteDatabase db;
	ImageView image1;
	TextView fn,sn,nn;
	EditText dob,place,psch,dist,from,gender,date;
	Button claim,cancel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		db = this.openOrCreateDatabase("find.db", Context.MODE_PRIVATE, null);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.found_details);
		
		image1=(ImageView)findViewById(R.id.image1);
		fn=(TextView)findViewById(R.id.txtFN);
		sn=(TextView)findViewById(R.id.txtSN);
		nn=(TextView)findViewById(R.id.txtNN);
		dob=(EditText)findViewById(R.id.txtDOB);
		place=(EditText)findViewById(R.id.txtFound);
		psch=(EditText)findViewById(R.id.txtSch);
		dist=(EditText)findViewById(R.id.txtDistrict);
		from=(EditText)findViewById(R.id.txtFrom);
		gender=(EditText)findViewById(R.id.txtGender);
		date=(EditText)findViewById(R.id.txtDate);
		claim=(Button)findViewById(R.id.btnClaim);
		cancel=(Button)findViewById(R.id.btnCancel);
		
		claim.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				foundClaim();
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				foundCancel();
			}
		});
		pupulate();
	}
	public void pupulate(){
		
		 Bundle i=getIntent().getExtras();
   	 //Intent i=getIntent();
		String id=i.getString("id");
		//String id=this.getIntent().getStringExtra("id");
		String name,from1,dob1,dist1,psch1,place1;
		
		Cursor c = db.rawQuery("select * from found where _id='"+id+"'", null);//+id+"'"
		if (c.moveToNext()) {
			name=c.getString(2);
			from1=c.getString(1);
			dob1=c.getString(3);
			dist1=c.getString(5);
			psch1=c.getString(6);
			place1=c.getString(4);
			fn.setText(name);
			sn.setText("No Surname");
			nn.setText("No Nickname");
			gender.setText("Male");
			dob.setText(dob1);
			from.setText(from1);
			dist.setText(dist1);
			place.setText(place1);
			date.setText("Not set");
			psch.setText(psch1);
			byte[] img = c.getBlob(7);
			Bitmap bm = BitmapFactory.decodeByteArray(img, 0, img.length);
			image1.setImageBitmap(bm);
		}
	}
	
	private void foundClaim() {
		// TODO Auto-generated method stub
		/*TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String serial = tm.getSimSerialNumber();
		String imei = tm.getDeviceId();
		String number=tm.getLine1Number();
		ContentValues values = new ContentValues();
		values.put("uby",number );
		values.put("over", fn.getText().toString());
		long c=db.insert("claim", null, values);
		db.close();*/
		Intent i = new Intent(Found_details.this, ConfirmClaim.class);
		startActivity(i);
		
	}
	private void foundCancel() {
		// TODO Auto-generated method stub
		Intent i = new Intent(Found_details.this, MainActivity.class);
		startActivity(i);
	}

}
