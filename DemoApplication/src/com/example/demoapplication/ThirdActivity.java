package com.example.demoapplication;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;

public class ThirdActivity extends Activity implements OnItemSelectedListener {
    //declare dem widgets~
	Spinner spinny;
	TextView texty;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_third);
		setupActionBar();
		//initialize dem widgets!
		spinny = (Spinner)findViewById(R.id.spinner1);
		texty = (TextView)findViewById(R.id.text4);
		spinny.setOnItemSelectedListener(this);
	}
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			Intent i= new Intent(this,SecondActivity.class);
			startActivity(i);
			overridePendingTransition(R.animator.reverse_in, R.animator.reverse_out);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.third, menu);
		return true;
	}
	@Override
	public void onItemSelected(AdapterView<?> arg0, View vous, int arg2,
			long arg3) {
		TextView t = (TextView)vous;
		String s = t.getText().toString();
        if(s.equals("Virginia")){
        	texty.setText("It was originally the strongest colony; it grew hella tobacco.");
        }
        if(s.equals("Florida")){
        	texty.setText("Such south.... wow. omg. many sun-----so bright");
        }
        if(s.equals("British Columbia")){
        	texty.setText("What even is this i don't think it's a state anyways");
        }
        if(s.equals("California")){
        	texty.setText("Liberal af. gold rush. wow. such west! all pacificy");
        }
		
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
