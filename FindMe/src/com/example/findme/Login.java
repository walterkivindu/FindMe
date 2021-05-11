package com.example.findme;

import java.util.Random;





import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends ActionBarActivity{
	Button login,register;
	EditText ph;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ph=(EditText)findViewById(R.id.code);
        login();
    }
	
	SqliteController db=new SqliteController(this);
	
	public void login(){
		login=(Button)findViewById(R.id.btnLogin);
		login.setOnClickListener(new View.OnClickListener() {
	         @Override
	         public void onClick(View v) {
	        	 String phoneNo=ph.getText().toString();
	        	 String SMS=getRandom();
	        	 try {
						SmsManager smsManager=SmsManager.getDefault();
						smsManager.sendTextMessage(phoneNo, null,"your verification code is "+ SMS, null, null);
						Toast.makeText(getApplicationContext(),"SMS Sent!...",Toast.LENGTH_LONG).show();
						Bundle sm=new Bundle();
						sm.putString("code",SMS);
						
						db.deletecode(phoneNo);
						db.submitCode(phoneNo, SMS);
						Intent i = new Intent(getApplicationContext(),Verify.class);
						i.putExtras(sm);
						startActivity(i);
						Log.d("Code=",SMS);
				} catch (Exception e) {				
						Toast.makeText(getApplicationContext(),	"SMS faild, please try again later!",Toast.LENGTH_LONG).show();
						e.printStackTrace();
				}
	        	 Log.d("Code=",SMS);
	            Intent intent = new Intent(Login.this,Verify.class);
	            intent.putExtra("phone", phoneNo);
	            startActivity(intent);
	         }
	      });
	}
	public String getRandom(){
		Random r= new Random();
		String rand=String.valueOf((r.nextInt(10)+55)+r.nextInt(55)+99);
		return rand;
	}


	

}
