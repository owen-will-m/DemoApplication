package com.example.demoapplication;

import java.io.IOException;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class AudioRecordActivity extends Activity implements OnClickListener {
//declare all objects
	Button start;
	Button stopSave;
	Button play;
	EditText fileName;
	MediaRecorder mr;
	String filePath;
	String name;
	String file;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audio_record);
		// Show the Up button in the action bar.
		setupActionBar();
		//set up references 
      try{
		start = (Button)findViewById(R.id.startRecord);
      }catch(Exception e){
    	  
    	  Log.d("NO","NO WORK "+ e.getMessage());
      }
		
		
		mr = new MediaRecorder();
		mr.setAudioSource(MediaRecorder.AudioSource.MIC);
	    mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
	    mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

		
		
		
	}
	
	public void playFile(View v){
		
		
		
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
		getMenuInflater().inflate(R.menu.audio_record, menu);
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
	public void onClick(View v) {
		boolean isRunning = false;
		file = fileName.getText().toString();
		file = getExternalCacheDir()+file+".3gp";
		if(v.getId()==start.getId()){//this is the submethod that starts recording
			mr.setOutputFile(file);
		    try {
				mr.prepare();
			} catch (Exception e) {
				e.printStackTrace();
			}
		    mr.start();
			isRunning = true;
		}
		
		if(v.getId()==stopSave.getId()&&isRunning){//this is the submethod that stops recording and saves it as that file name
			
			mr.stop();
			mr.release();
			isRunning = false;
		}

	}

}
