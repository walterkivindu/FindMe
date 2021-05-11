package com.example.findme;

import java.util.Calendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConfirmClaim extends ActionBarActivity {

	private EditText nn, sn, date;
	SQLiteDatabase db;
	Button claim, cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirm_claim);
		claim = (Button) findViewById(R.id.btnccClaim);
		cancel = (Button) findViewById(R.id.btnccCancel);
		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				back();
			}
		});
		claim.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Calendar cal = Calendar.getInstance();
				int day,month,year;
				day=cal.get(Calendar.DAY_OF_MONTH);
				month=cal.get(Calendar.MONTH);
				year=cal.get(Calendar.YEAR);
				Toast.makeText(getApplicationContext(), day+"/"+(month+1)+"/"+year, Toast.LENGTH_SHORT).show();

			}
		});
	}

	public void back() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				ConfirmClaim.this);
		// Setting Dialog Title
		alertDialog.setTitle("Exit?");
		// Setting Dialog Message
		alertDialog.setMessage("Leave this page!");
		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.confirm);
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
						Intent intent;
						intent = new Intent(ConfirmClaim.this,
								MainActivity.class);
						startActivity(intent);
						finish();

					}
				});

		// Showing Alert Message
		alertDialog.show();

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		back();
	}

}
