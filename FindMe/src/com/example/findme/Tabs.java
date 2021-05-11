package com.example.findme;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class Tabs extends Activity implements OnClickListener{
  
	TabHost th;
	TextView ShowResults;
	long start,stop;
	TextView tv;
	
	@SuppressLint("CutPasteId") @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fortab);
		 th= (TabHost)findViewById(R.id.tabhost);
		
		Button newTab = (Button)findViewById(R.id.bAddTab);
		Button bStart = (Button)findViewById(R.id.bStartWatch);
		Button bStop = (Button)findViewById(R.id.bStopWatch);
		tv=(TextView)findViewById(R.id.tvShowresults);
	   
		ShowResults =(TextView)findViewById(R.id.tvShowresults);
		bStart.setOnClickListener(this);
		
		bStop.setOnClickListener(this);
		
		newTab.setOnClickListener(this);
		
		
		
		th.setup();
		TabSpec specs = th.newTabSpec("tag1");
		specs.setContent(R.id.tab1);
		specs.setIndicator("Tab one");
		th.addTab(specs);
		
		 specs = th.newTabSpec("tag2");
		specs.setContent(R.id.tab2);
		specs.setIndicator("Tab two");
		th.addTab(specs);
		
		 specs = th.newTabSpec("tag3");
		specs.setContent(R.id.tab3);
		specs.setIndicator("Tab Three");
		th.addTab(specs);
		start = 0;
	}

	@Override
	public void onClick(View arg0) {
		
		
		long result ;
		int millis ;
		int seconds;
		int minutes ;
		
		// TODO Auto-generated method stub
		switch(arg0.getId()){
	
		
		
		
		case R.id.bAddTab:
			TabSpec ourSpec = th.newTabSpec("tag1");
			ourSpec.setContent(new TabHost.TabContentFactory(){
				
				@Override
				public View createTabContent(String tag) {
					// TODO Auto-generated method stub
					TextView text =new TextView(Tabs.this);
					text.setTag("you av created new tab");
					
					return (text);
				}
			});
		ourSpec.setIndicator("new")	;
		th.addTab(ourSpec);
		break;
		
	 case R.id.bStartWatch:
		start = System.currentTimeMillis();
		ShowResults.setText(String.format("%d", start));
		break;
	 case R.id.bStopWatch:
		 stop = System.currentTimeMillis();
		 
		if(start != 0){
			result = stop-start ;
			millis = (int)result;
			seconds = (int) result/1000;
			minutes = seconds/60;
			millis = millis % 100;
			seconds = seconds % 60;
			
			
			ShowResults.setText(String.format("%d:%02d:%02d", minutes,seconds,millis));
			}
		break;
		}
		
		
	
	}

}
