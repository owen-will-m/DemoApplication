package com.example.demoapplication;

import java.io.File;
import java.io.FileOutputStream;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;

public class NegativeFirstActivity extends Activity implements OnCheckedChangeListener {
DrawingPanel dp;
LinearLayout rl;
Button b;
RadioGroup rg;
RadioButton red;
RadioButton green;
RadioButton blue;
Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_negative_first);
		// Show the Up button in the action bar.
		setupActionBar();
		dp = new DrawingPanel(this,350);
		rl = (LinearLayout)findViewById(R.id.rl1);
		rl.addView(dp,1);
		b=(Button)findViewById(R.id.buttonz);
		rg = (RadioGroup)findViewById(R.id.rg2);
		red = (RadioButton)findViewById(R.id.red);
		green = (RadioButton)findViewById(R.id.green);
		blue = (RadioButton)findViewById(R.id.blue);
		rg.setOnCheckedChangeListener(this);
		context = getApplicationContext();
		b.setOnClickListener(new View.OnClickListener(){
        
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rl.removeView(dp);
				dp = new DrawingPanel(getApplicationContext() ,350);
				rl.addView(dp,1);
				rg.clearCheck();
				dp.setColor(Color.BLACK);
			}
			
			
		});
		
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
		getMenuInflater().inflate(R.menu.negative_first, menu);
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
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCheckedChanged(RadioGroup rg, int id) {
		// TODO Auto-generated method stub

		if(id==red.getId()){
			rl.removeView(dp);
			dp = new DrawingPanel(getApplicationContext(),350);
			rl.addView(dp,1);
			dp.setColor(Color.RED);
		}
		if(id==green.getId()){
			rl.removeView(dp);
			dp = new DrawingPanel(getApplicationContext(),350);
			rl.addView(dp,1);
			dp.setColor(Color.GREEN);
		}
		if(id==blue.getId()){
			rl.removeView(dp);
			dp = new DrawingPanel(getApplicationContext(),350);
			rl.addView(dp,1);
			dp.setColor(Color.BLUE);
		}
	}
	public void save(View v){
		try{	//this saves the file
			File f = new File(context.getExternalCacheDir()+"/sketch.jpg");
			FileOutputStream fos = new FileOutputStream(f);
			dp.getDrawingCache().compress(Bitmap.CompressFormat.JPEG, 100, fos);
			Log.d("Directory:",""+context.getExternalCacheDir());
			Toast.makeText(getApplicationContext(),"Saved as: "+f.getName(),Toast.LENGTH_SHORT).show();
		}catch(Exception e){
			Log.d("NO WORK", "NOT SAVE");
		}
		
	}

}


