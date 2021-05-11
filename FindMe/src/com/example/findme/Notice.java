package com.example.findme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Notice extends Activity {
	Thread thread;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice);
		
		thread = new Thread(runable);
        thread.start();
	}
	
	 public Runnable runable = new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	            try {
	                startActivityForResult(new Intent(Notice.this,MainActivity.class),0);
	                finish();
	            } catch (Exception e) {
	                // TODO: handle exception
	            }
	        }
	    };
	
	

}
