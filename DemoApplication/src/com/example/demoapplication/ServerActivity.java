package com.example.demoapplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class ServerActivity extends Activity{
Button server;
EditText et;
TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server);
		// Show the Up button in the action bar.
		setupActionBar();
		et = (EditText)findViewById(R.id.serverEditText);
		server = (Button)findViewById(R.id.serverSend);
		tv = (TextView)findViewById(R.id.display);
		//set up client programming stuff
		
		//set onclicklistener for the server button
		server.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// this method shall set up a connection to the server and send the text in et
				Socket sack = null;
				String message = "";
				try{
				InetSocketAddress address = new InetSocketAddress(InetAddress.getLocalHost(),8888);
					sack = new Socket();
					sack.connect(address,2000);
					OutputStreamWriter out = new OutputStreamWriter(sack.getOutputStream());
					out.write(et.getText().toString()+"\n");
					out.flush();
					InputStreamReader in = new InputStreamReader(sack.getInputStream());
					BufferedReader buff = new BufferedReader(in);
					message = buff.readLine();
					tv.append(message);
					sack.close();
				}catch(Exception e){
					Log.d("NO","NO WORK CONNEXION");
					Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
				}
				
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
		getMenuInflater().inflate(R.menu.server, menu);
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
