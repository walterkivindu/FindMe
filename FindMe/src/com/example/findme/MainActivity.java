package com.example.findme;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ActionBar.Tab;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity {

	ActionBar mActionBar;
	ViewPager mPager;
	Thread thread;
	SQLiteDatabase db;
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//thread = new Thread(runable);
		//thread.start();
		setContentView(R.layout.activity_main);


		/** Getting a reference to action bar of this activity */
		mActionBar = getActionBar();

		/** Set tab navigation mode */
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		/** Getting a reference to ViewPager from the layout */
		mPager = (ViewPager) findViewById(R.id.pager);

		/** Getting a reference to FragmentManager */
		FragmentManager fm = getSupportFragmentManager();

		/** Defining a listener for pageChange */
		ViewPager.SimpleOnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
			@SuppressLint("NewApi")
			@Override
			public void onPageSelected(int position) {
				super.onPageSelected(position);
				mActionBar.setSelectedNavigationItem(position);
			}
		};

		/** Setting the pageChange listner to the viewPager */
		mPager.setOnPageChangeListener(pageChangeListener);

		/** Creating an instance of FragmentPagerAdapter */
		MyFragmentPagerAdapter fragmentPagerAdapter = new MyFragmentPagerAdapter(
				fm);

		/** Setting the FragmentPagerAdapter object to the viewPager object */
		mPager.setAdapter(fragmentPagerAdapter);

		mActionBar.setDisplayShowTitleEnabled(true);

		/** Defining tab listener */
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {

			@SuppressLint("NewApi")
			@Override
			public void onTabReselected(Tab arg0,
					android.app.FragmentTransaction arg1) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onTabSelected(Tab tab,
					android.app.FragmentTransaction ft) {
				mPager.setCurrentItem(tab.getPosition());
				
			}

			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@SuppressLint("NewApi")
			@Override
			public void onTabUnselected(Tab tab,
					android.app.FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
		};

		/** Creating Android Tab */
		Tab tab = mActionBar.newTab().setText("View Found")
		// .setIcon(R.drawable.select)
				.setTabListener(tabListener);

		mActionBar.addTab(tab);

		/** Creating Apple Tab */
		tab = mActionBar.newTab().setText("Messages")
		// .setIcon(R.drawable.report)
				.setTabListener(tabListener);

		mActionBar.addTab(tab);

		/*tab = mActionBar.newTab().setText("Notification")
		// .setIcon(R.drawable.report)
				.setTabListener(tabListener);

		mActionBar.addTab(tab);*/

		tab = mActionBar.newTab().setText("Contacts ")
		// .setIcon(R.drawable.report)
				.setTabListener(tabListener);

		mActionBar.addTab(tab);
	}
	public Runnable runable = new Runnable() {
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
            	int log=logged();
            	if(log!=1){
            		startActivityForResult(new Intent(MainActivity.this, AutoLogin.class),0);
                    finish();
            		
            	}else{
            		Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
            	}
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    };
    
    public int logged(){
    	int log=0;
    	db = this.openOrCreateDatabase("find.db", Context.MODE_PRIVATE, null);
    	TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String serial = tm.getSimSerialNumber();
		Bundle i = getIntent().getExtras();
		String phoneNo = i.getString("phone");
		String status="1";
		String status1="";
    	Cursor c = db.rawQuery("select status from users where serial='"+serial+"' OR phone='"+phoneNo+"'", null);
    	while (c.moveToNext()) {
    		status1=c.getString(5);
    		Toast.makeText(getApplicationContext(), status1, Toast.LENGTH_SHORT).show();
    		if(status1.equalsIgnoreCase(status)){
    			log=1;
    		return log;
    		}
    	}
    	return log;
    }
    
    public void Logout(){
		String status="0";
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
			Toast.makeText(this, "bye!",
					Toast.LENGTH_LONG).show();
			System.exit(0);
		}
	}
    
    public void backButtonHandler() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());
		// Setting Dialog Title
		alertDialog.setTitle("Log out?");
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
						Logout();
					}
				});

		// Showing Alert Message
		alertDialog.show();
	}

    
	
	/* @Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		backButtonHandler();
	}*/

	// Method to start the service
	   public void startService(View view) {
	      startService(new Intent(getBaseContext(), BackgroundService.class));
	   }

	   // Method to stop the service
	   public void stopService(View view) {
	      stopService(new Intent(getBaseContext(), BackgroundService.class));
	   }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.action_settings:
			return false;
		case R.id.mymenu:

			try {
				Class<?> newclass = Class.forName("com.example.findme.Menu");
				Intent i = new Intent(getApplicationContext(), newclass);
				startActivity(i);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return true;
		case R.id.AddFoundMenu:
			Intent i = new Intent(getApplicationContext(), AddFound.class);
			startActivity(i);
			return true;
		case R.id.AddLostMenu:
			Intent b = new Intent(getApplicationContext(), AddLost.class);
			startActivity(b);
			return true;
		case R.id.ContactMenu:
			Intent c = new Intent(getApplicationContext(), Contacts.class);
			startActivity(c);
			return true;
		case R.id.Admin:
			Intent d = new Intent(getApplicationContext(), AdminLogin.class);
			startActivity(d);
			return true;
		case R.id.ViewLost:
			Intent e = new Intent(getApplicationContext(), ListLostChild.class);
			startActivity(e);
			return true;
		case R.id.ViewFound:
			Intent f = new Intent(getApplicationContext(), ListFoundChild.class);
			startActivity(f);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
		
	}

}
