package com.example.demoapplication;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import java.text.DecimalFormat;

public class SecondActivity extends Activity implements RadioGroup.OnCheckedChangeListener, OnClickListener{
//This activity will be testing the relative layout and radiogroup widget to calculate tips
	RadioButton rb1;
	RadioButton rb2;
	RadioButton rb3;
	TextView tv1;//total
	TextView tv2;//tip
	EditText et1;
	Button b;
	RadioGroup rg;
	DecimalFormat df = new DecimalFormat("$####.00");
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		// Show the Up button in the action bar.
		setupActionBar();
		rb1 = (RadioButton)findViewById(R.id.ten);
		rb2 = (RadioButton)findViewById(R.id.twenty);
		rb3 = (RadioButton)findViewById(R.id.thirty);
		et1 = (EditText)findViewById(R.id.edit1);
		tv1 = (TextView)findViewById(R.id.text2);//display tip
		tv2 = (TextView)findViewById(R.id.text3);//display total
		rg = (RadioGroup)findViewById(R.id.rg1);
		rg.setOnCheckedChangeListener(this);
		b = (Button)findViewById(R.id.butts);
		b.setOnClickListener(this);
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
		return true;
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
			Intent i= new Intent(this,MainActivity.class);
			startActivity(i);
			overridePendingTransition(R.animator.reverse_in, R.animator.reverse_out);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCheckedChanged(RadioGroup rg, int id) {//three different radio buttons!
		// TODO Auto-generated method stub
		try{
		int ten = rb1.getId();
		int twenty = rb2.getId();
		int thirty = rb3.getId();
		double total = Double.parseDouble(et1.getText().toString());
		double temp = 0;
		
		if(id==ten){//ten percent tip
			temp = total * .1;
			tv1.setText("Tip: "+df.format(temp));
			tv2.setText("Total: "+df.format(temp+total));
		}
		if(id==twenty){//twenty percent tip
			temp = total * .2;
			tv1.setText("Tip: "+df.format(temp));
			tv2.setText("Total: "+df.format(temp+total));
			
		}
		if(id==thirty){//thirty percent tip
			temp = total * .3;
			tv1.setText("Tip: "+df.format(temp));
			tv2.setText("Total: "+df.format(temp+total));
			
		}
		}catch(Exception e){
			tv1.setText("Hey that wasn't a bill");
			tv2.setText("Yeah what the hell");
		}
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==b.getId()){
			Intent i = new Intent(this,ThirdActivity.class);
			startActivity(i);
			overridePendingTransition(R.animator.slide_in, R.animator.slide_out);
			
		}
	}

}
