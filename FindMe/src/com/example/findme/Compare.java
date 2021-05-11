package com.example.findme;

import java.util.Calendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Compare extends ActionBarActivity {
	private TextView lost, found, report;
	SQLiteDatabase db;
	Button next,pre;
	ImageView imgl, imgf;
	private ProgressDialog pDialog;
	private int progressBarStatus = 0;
	private Handler progressBarbHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		db = openOrCreateDatabase("find.db", MODE_PRIVATE, null);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.compare);
		lost = (TextView) findViewById(R.id.txtCLost);
		found = (TextView) findViewById(R.id.txtCFound);
		imgl = (ImageView) findViewById(R.id.imageLost);
		imgf = (ImageView) findViewById(R.id.imageFound);
		report = (TextView) findViewById(R.id.txtReport);
		next = (Button) findViewById(R.id.button1);
		pre = (Button) findViewById(R.id.button2);
		pre.setEnabled(false);
		progress();
		next.setEnabled(false);
	}

	public void ontart() {
		String sql, sql1;
		sql = "select * from lost";
		sql1 = "select * from found";

		Cursor c = db.rawQuery(sql, null);
		Cursor c1 = db.rawQuery(sql1, null);
		if (c.moveToNext()) {
			byte[] img = c.getBlob(7);
			Bitmap bm = BitmapFactory.decodeByteArray(img, 0, img.length);
			if (c1.moveToNext()) {
				byte[] img1 = c1.getBlob(7);
				Bitmap bm1 = BitmapFactory
						.decodeByteArray(img1, 0, img1.length);
				int width = bm1.getWidth();
				int height = bm1.getHeight();
				for (int y = 0; y < height; ++y)
					for (int x = 0; x < width; ++x)
						if (bm1.getPixel(x, y) == bm.getPixel(x, y)) {
							lost.setText(c.getString(1));
							found.setText(c1.getString(2));
							imgl.setImageBitmap(bm);
							imgf.setImageBitmap(bm1);
							/*
							 * String tittle="New Match"; String
							 * subject="One new match"; String
							 * body=c.getString(2)+" matches "+c1.getString(2);
							 * NotificationManager
							 * notif=(NotificationManager)getSystemService
							 * (Context.NOTIFICATION_SERVICE); Notification
							 * notify=new
							 * Notification(R.drawable.infor,tittle,System
							 * .currentTimeMillis()); PendingIntent pending=
							 * PendingIntent
							 * .getActivity(getApplicationContext(), 0, new
							 * Intent(), 0);
							 * notify.setLatestEventInfo(getApplicationContext
							 * (),subject,body,pending); notif.notify(0,
							 * notify);
							 */
						} else if (bm1.getPixel(x, y) != bm.getPixel(x, y)) {
							progressBarStatus = 100;
							report.setText("No matching Records");
						}

			}
		}

	}

	public void progress() {
		pDialog = new ProgressDialog(Compare.this);
		pDialog.setMessage("Processing data.. Please wait...");
		pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		pDialog.setProgress(0);
		pDialog.setMax(100);
		pDialog.show();
		progressBarStatus = 0;
		new Thread(new Runnable() {
			public void run() {

				while (progressBarStatus < 100) {

					progressBarStatus = 100;

					try {
						Thread.sleep(1000);
					}

					catch (InterruptedException e) {
						e.printStackTrace();
					}

					progressBarbHandler.post(new Runnable() {
						public void run() {
							pDialog.setProgress(progressBarStatus);
							ontart();
						}
					});
				}

				if (progressBarStatus >= 100) {
					try {
						Thread.sleep(2000);
					}

					catch (InterruptedException e) {
						e.printStackTrace();
					}
					pDialog.dismiss();
				}
			}
		}).start();

	}

}
