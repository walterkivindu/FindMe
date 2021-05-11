package com.example.findme;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class BackgroundService extends Service {
	SQLiteDatabase db;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	//SqliteController1 dbhelper = new SqliteController1(this);
	//SQLiteDatabase db = dbhelper.getWritableDatabase();
	
	
	

	public void onStart() {
		//match();
	}
	
	 @Override
	   public int onStartCommand(Intent intent, int flags, int startId) {
	      // Let it continue running until it is stopped.
	      Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
	      return START_STICKY;
	   }
	 @Override
	   public void onDestroy() {
	      super.onDestroy();
	      Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
	   }
	public void match(){
		db = openOrCreateDatabase("findme.db",MODE_PRIVATE,null);
		String sql,sql1,sql2,sql3;
		sql="select img from lost";
		sql1="select img from found";
		
		Cursor c = db.rawQuery(sql, null);
		Cursor c1 = db.rawQuery(sql1, null);
		if (c.moveToNext()) {
			byte[] img = c.getBlob(7);
			Bitmap bm = BitmapFactory.decodeByteArray(img, 0, img.length);
			if(c1.moveToNext()){
				byte[] img1 = c1.getBlob(7);
				Bitmap bm1 = BitmapFactory.decodeByteArray(img1, 0, img1.length);
				int width = bm1.getWidth(); 
				int height = bm1.getHeight();
				 for (int y = 0; y < height; ++y)
				       for (int x = 0; x < width; ++x)
				            if (bm1.getPixel(x, y) == bm.getPixel(x, y)){
				            	String tittle="New Match";
				                String subject="One new match";
				                String body=c.getString(2)+" matches "+c1.getString(2);
				                NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
				                Notification notify=new Notification(R.drawable.infor,tittle,System.currentTimeMillis());
				                PendingIntent pending= PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), 0);
				                notify.setLatestEventInfo(getApplicationContext(),subject,body,pending);
				                notif.notify(0, notify);
				            }
				 

			}
		}
		
	}

}
