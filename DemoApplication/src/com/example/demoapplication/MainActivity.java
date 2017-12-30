package com.example.demoapplication;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
Button b;
TextView tv;
EditText et;
Button next;
Button pb;
MediaPlayer player;
AlertDialog ad;
AlertDialog.Builder adb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		b = (Button)findViewById(R.id.b1);
		tv = (TextView)findViewById(R.id.tv1);
		et = (EditText)findViewById(R.id.et1);
		next = (Button)findViewById(R.id.next1);
		b.setOnClickListener(this);
		next.setOnClickListener(this);
		pb = (Button)findViewById(R.id.prev1);
		pb.setOnClickListener(this);
		player = new MediaPlayer();
	}
	public void audioActivity(View v){
		Intent intent = new Intent(this, MicrophoneActivity.class);
		startActivity(intent);	
	}
	
	public void popUp(View v){
		adb = new AlertDialog.Builder(this);
		adb.setCancelable(false);
		adb.setMessage("Should you pay extra for guac?");
		adb.setTitle("Preferences");
		adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Fuck you", Toast.LENGTH_SHORT).show();
			}
		});
		adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Seriously, like why.. $1.50 is so much", Toast.LENGTH_SHORT).show();
			}
		});
		ad = adb.create();
		ad.show();
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem mi){
		if(mi.getItemId()==R.id.serverBut){
			Toast.makeText(getApplicationContext(),"This worked",Toast.LENGTH_SHORT).show();
			Intent i = new Intent(this,ServerActivity.class);
			startActivity(i);
			return true;
		}
		
		
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==b.getId()){
		String s = et.getText().toString();
		tv.setText(s);
		}
		if(v.getId()==next.getId()){
			Intent i = new Intent(this, SecondActivity.class);
		    startActivity(i);
		    overridePendingTransition(R.animator.slide_in, R.animator.slide_out);
		}
		if(v.getId()==pb.getId()){
			Intent in = new Intent(this,NegativeFirstActivity.class);
			startActivity(in);
			overridePendingTransition(R.animator.reverse_in, R.animator.reverse_out);
		}
	}
	public void nextTest(View v){
		Intent j = new Intent(this,ScrollActivity.class);
		startActivity(j);
	}
	public void play(View v){
		audioPlayer();
		
		
	}
	public void audioPlayer(){
	    //set up MediaPlayer    
	    
	    try {
	    	AssetFileDescriptor afd = getAssets().openFd("fart.mp3");
	        player.setDataSource(afd.getFileDescriptor());
	        player.prepare();
	        player.start();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
