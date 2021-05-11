package com.example.findme;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Contact_sms extends Activity {
	Button send;
	EditText msg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_sms);
		String phone=this.getIntent().getStringExtra("phone");
		send = (Button) findViewById(R.id.btnSend);
		msg = (EditText) findViewById(R.id.txtSMS);
		
		setTitle(phone);
		send.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				
				Bundle i = getIntent().getExtras();
				String phoneNo = i.getString("phone");
				String message = msg.getText().toString();

				try {
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(phoneNo, null, message, null,
							null);
					Toast.makeText(getApplicationContext(), "SMS sent.",
							Toast.LENGTH_LONG).show();
				}

				catch (Exception e) {
					Toast.makeText(getApplicationContext(),
							"SMS faild, please try again.", Toast.LENGTH_LONG)
							.show();
					e.printStackTrace();
				}
			}
		});
	}

}
