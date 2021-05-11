package com.example.findme;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Verify extends ActionBarActivity {
	Button sub;
	EditText cd;
	SqliteController db = new SqliteController(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.verify);
		sub = (Button) findViewById(R.id.btncode);
		cd = (EditText) findViewById(R.id.code);

		sub.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Bundle i=getIntent().getExtras();
				// Intent i=getIntent();
				// String code=i.getStringExtras("code");

				String[] code1 = db.getcode();
				// String code=i.getString("code");
				String code = code1[0];
				Toast.makeText(getBaseContext(), "code:" + code,
						Toast.LENGTH_SHORT).show();
				String cod = cd.getText().toString();
				if (!(code.equalsIgnoreCase(cod))) {
					Toast.makeText(getBaseContext(), "Error!\n :",
							Toast.LENGTH_SHORT).show();

				} else {

					Toast.makeText(getBaseContext(),
							"Account verified successfully!",
							Toast.LENGTH_SHORT).show();
					Bundle i = getIntent().getExtras();
					String phoneNo = i.getString("phone");
					updateLogin();
					Intent intent = new Intent(Verify.this, MainActivity.class);
					intent.putExtra("phone", phoneNo);
					startActivity(intent);

				}

			}

		});
	}
	
	public void updateLogin(){
		String status="1";
		Bundle i = getIntent().getExtras();
		String phoneNo = i.getString("phone");
		SqliteController1 dbhelper = new SqliteController1(this);
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("status", status);
		long rows = db.update("users", values,
				"phone = ?",new String[] { phoneNo });
		db.close();
		if (rows > 0){
			Toast.makeText(this, "Success!",
					Toast.LENGTH_LONG).show();
		}
	}
}
