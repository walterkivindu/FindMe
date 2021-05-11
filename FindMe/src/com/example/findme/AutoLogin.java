package com.example.findme;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AutoLogin extends ActionBarActivity {

	Button login, register;
	EditText ph,un;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		ph = (EditText) findViewById(R.id.txtNumber);
		un = (EditText) findViewById(R.id.txtUName);
		register();
		login();
		//auto();
		
		SqliteController dbhelper = new SqliteController(getApplicationContext());
		//String get[] = new String[]{"No record","Found"};
		String user[] =dbhelper.getAllUsers();
		if (user == null){
			Toast.makeText(getBaseContext(),
					"No registered user" , Toast.LENGTH_SHORT)
					.show();
		}

	}

	public void login() {
		login = (Button) findViewById(R.id.btnLogin);
		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AutoLogin.this, Login.class);
				startActivity(intent);
			}
		});
	}

	SqliteController db = new SqliteController(this);;

	public void register() {
		register = (Button) findViewById(R.id.btnRegister);
		register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String number = ph.getText().toString();

				try {
					//String phone,String name,String serial, String imei
					String name=un.getText().toString();
					TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
					String serial = tm.getSimSerialNumber();
					String imei = tm.getDeviceId();
					db.AddUser(number,name, serial, imei,"0");
					Toast.makeText(getBaseContext(),
							"User registered successfully :",
							Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(getBaseContext(),
							"Error!\n :" + e.getMessage(), Toast.LENGTH_SHORT)
							.show();
				}
				Intent intent = new Intent(AutoLogin.this, Login.class);
				startActivity(intent);

			}
		});
	}

	public boolean exists() {
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String serial = tm.getSimSerialNumber();
		while (db.getPhone(serial) != null) {
			return true;
		}
		return false;
	}

	public void auto() {
		if (exists() == true) {
			Toast.makeText(getBaseContext(), "Passed!\n :", Toast.LENGTH_SHORT)
					.show();
			Intent intent = new Intent(AutoLogin.this, MainActivity.class);
			startActivity(intent);

		}else{
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		// Display alert message when back button has been pressed
		backButtonHandler();
		return;
	}

	public void backButtonHandler() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				AutoLogin.this);
		// Setting Dialog Title
		alertDialog.setTitle("Exit?");
		// Setting Dialog Message
		alertDialog.setMessage("System will Exit!");
		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.exit);
		// Setting Negative "NO" Button
				alertDialog.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								// Write your code here to invoke NO event
								dialog.cancel();
							}
						});
		
		// Setting Positive "Yes" Button
		alertDialog.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						/*
						 * Intent intent ; intent = new
						 * Intent(getApplicationContext(), AdminLogin.class);
						 * startActivity(intent); finish();
						 */
						System.exit(0);
					}
				});
		
		// Showing Alert Message
		alertDialog.show();
	}

}
