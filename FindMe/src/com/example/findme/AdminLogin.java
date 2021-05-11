package com.example.findme;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLogin extends ActionBarActivity {
	private EditText user, pass;
	SQLiteDatabase db;
	Button submit, cancel;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		db = this.openOrCreateDatabase("find.db", Context.MODE_PRIVATE, null);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_login);
		user = (EditText) findViewById(R.id.txtAName);
		pass = (EditText) findViewById(R.id.txtAPass);
		submit = (Button) findViewById(R.id.btnASubmit);
		cancel = (Button) findViewById(R.id.btnACancel);

		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				adminLogin();
			}
		});

		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				adminCancel();
			}
		});
	}

	public void adminLogin() {
		String name, pas;
		name=user.getText().toString();
		pas=pass.getText().toString();
		Cursor c = db.rawQuery("select admin , pass from admin where admin='"+name
				+"' AND pass='"+pas+"'", null);
		if (c.moveToNext()) {
			name = c.getString(0);
			pas = c.getString(1);
			Intent i = new Intent(AdminLogin.this, Compare.class);
			startActivity(i);
			/*Toast.makeText(getApplicationContext(), name+" "+pas, Toast.LENGTH_SHORT).show();
			if (!(name.equalsIgnoreCase(user.getText().toString()))
					&& !(pas.equalsIgnoreCase(pass.getText().toString()))) {
				
			*/}else{
				Toast.makeText(getApplicationContext(), "Wrong creditials", Toast.LENGTH_SHORT).show();
//			}

		}

	}

	public void adminCancel() {
		Intent i = new Intent(AdminLogin.this, MainActivity.class);
		startActivity(i);
	}

	
	
	
}
