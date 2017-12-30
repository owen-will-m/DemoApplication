package com.example.demoapplication;

import java.util.ArrayList;
import java.util.LinkedList;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View.OnClickListener;

public class ScrollActivity extends Activity {
//declare variables
	ArrayList<Button> buttons;
	ArrayList<TextView> tl;
	LinkedList<String> strings;
	EditText et;
	TextView tv;
	TableLayout tabl;
	TableRow tr;
	Button b;
	String text;
	int count = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scroll);
		// Show the Up button in the action bar.
		setupActionBar();
		
		et = (EditText)findViewById(R.id.editText1);
		tabl = (TableLayout)findViewById(R.id.tl1);
		
		strings = new LinkedList<String>();
		
	}

	public void onClick(View v){//must make all buttons in an arraylist of buttons
		

		text = et.getText().toString();
		if(text==null){
			text = "";
		}
		strings.add(text);
		et.setText("");
		tv = new TextView(getApplicationContext());
		tv.setText(text);
		tr = new TableRow(getApplicationContext());
		tr.addView(tv);
		b = new Button(this);
		b.setText("Toast it");
		b.setId(count);//the id is count plus 12345 to make it unique
		
		count++;
		

		
		b.setOnClickListener( new View.OnClickListener(){//setting an on click listener to the thing
			@Override
			public void onClick(View v) {//this button shall make a toast appear with the text the person put in
				int i;
				try{

				i = v.getId();
				Toast.makeText(getApplicationContext(),strings.get(i),Toast.LENGTH_SHORT).show();//that should be the index of the correct button
				}catch(Exception e){
					Log.d("NO","NO CAN MAKE TOSTE"+e.getMessage());
					
				}
			}
				});

		
		tr.addView(b);
		tabl.addView(tr);
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
		getMenuInflater().inflate(R.menu.scroll, menu);
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

}
