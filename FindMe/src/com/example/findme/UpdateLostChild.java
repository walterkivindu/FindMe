package com.example.findme;

import java.util.HashMap;




import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateLostChild extends Activity {
	private String accountId;
	private EditText editAcno, editCno, editFname, editStation,Date;
	ProgressDialog prgDialog;
	HashMap<String, String> queryValues;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_lost);
		editAcno = (EditText) this.findViewById(R.id.editAcno);
		editCno = (EditText) this.findViewById(R.id.editCno);
		editFname = (EditText) this.findViewById(R.id.editFname);
		editStation = (EditText) this.findViewById(R.id.editStationName);
	    Date = (EditText) this.findViewById(R.id.date);
		
	}
	

	

	@Override
	public void onStart() {
		super.onStart();
		accountId = this.getIntent().getStringExtra("accountid");
		Log.d("Accounts", "Account Id : " + accountId);
		SqliteController dbhelper = new SqliteController(this);
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor account = db.query("lost", null,
				"_id = ?", new String[] { accountId }, null, null, null);
		//startManagingCursor(accounts);
		if (account.moveToFirst()) {
			// update view
			editAcno.setText(account.getString(account
					.getColumnIndex("fname")));
			editCno.setText(account.getString(account
					.getColumnIndex("sname")));
			editFname.setText(account.getString(account
					.getColumnIndex("dob")));
			editStation.setText(account.getString(account
					.getColumnIndex("place")));
			
			Date.setText(account.getString(account
					.getColumnIndex("district")));
		}
		account.close();
		db.close();
		dbhelper.close();

	}

	public void updateAccount(View v) {
		try {
			SqliteController dbhelper = new SqliteController(this);
			SQLiteDatabase db = dbhelper.getWritableDatabase();
			// execute insert command
		    HashMap<String, String> queryValues = new HashMap<String, String>();
            queryValues.put("pacno", editAcno.getText().toString());
            queryValues.put("pcno", editCno.getText().toString());
            queryValues.put("pfname", editFname.getText().toString());
            queryValues.put("pstation", editStation.getText().toString());
            queryValues.put("date", Date.getText().toString());
        
			ContentValues values = new ContentValues();
			values.put("fname", queryValues.get("pacno"));
			values.put("sname", queryValues.get("pcno"));
			values.put("dob", queryValues.get("pfname"));
			values.put( "place", queryValues.get("pstation"));
			
			values.put( "district", queryValues.get("date"));

			long rows = db.update("lost", values,
					"_id = ?", new String[] { accountId });

			db.close();
			if (rows > 0){
				Toast.makeText(this, "Updated Lost Child Successfully!",
						Toast.LENGTH_LONG).show();
			//	syncUpdate();
			}
			else{
				Toast.makeText(this, "Sorry! Could not update lost child!",
						Toast.LENGTH_LONG).show();}
		} catch (Exception ex) {
			Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	public void deleteAccount(View v) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Are you sure you want to delete this lost child?")
			       .setCancelable(false)
			       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	  //  syncDelete();
			                deleteCurrentAccount();
			                
			           }
			       })
			       .setNegativeButton("No", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
	}
	
	
         			
    public void deleteCurrentAccount() {
    	try {
    		final SqliteController dbhelper = new SqliteController(this);
			final SQLiteDatabase db = dbhelper.getWritableDatabase();
			
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(
					UpdateLostChild.this);
			// Setting Dialog Title
			alertDialog.setTitle("Sure?");
			// Setting Dialog Message
			alertDialog.setMessage("Record will be deleted!");
			// Setting Icon to Dialog
			alertDialog.setIcon(R.drawable.warning);
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
							int rows = db.delete("lost", "_id=?", new String[] { accountId});
							dbhelper.close();
							if ( rows == 1) {
								Toast.makeText(UpdateLostChild.this, "Lost Child Deleted Successfully!", Toast.LENGTH_LONG).show();
								
								finish();
							}
							else
								Toast.makeText(UpdateLostChild.this, "Could not delete child!", Toast.LENGTH_LONG).show();

					
							 
						}
					});
			alertDialog.show();
			
			
				} catch (Exception ex) {
			Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
		}

	}
    
    
    @Override
	public void onBackPressed() {
		// Display alert message when back button has been pressed
		backButtonHandler();
		return;
	}

	public void backButtonHandler() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				UpdateLostChild.this);
		// Setting Dialog Title
		alertDialog.setTitle("Exit?");
		// Setting Dialog Message
		alertDialog.setMessage("Move out of this page?");
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
						
						 Intent intent ; intent = new
						 Intent(getApplicationContext(), ListLostChild.class);
						 startActivity(intent); 
						 finish();
						 
						//System.exit(0);
					}
				});
		
		// Showing Alert Message
		alertDialog.show();
	}

 
   
    
}
