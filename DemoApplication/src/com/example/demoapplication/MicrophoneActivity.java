package com.example.demoapplication;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.os.Build;

public class MicrophoneActivity extends Activity implements OnCheckedChangeListener{
    RadioGroup rg1;
    RadioGroup rg2;
	Button start;
	Button stop;
	Button play;
	MediaRecorder recorder;
	boolean isRunning;
	String mFileName;
	EditText et;
	EditText hz;
	int format;
	int encoding;
	String name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_microphone);
		// Show the Up button in the action bar.
		setupActionBar();
		start = (Button)findViewById(R.id.buttonStart);
		stop = (Button)findViewById(R.id.buttonStop);
		play =(Button)findViewById(R.id.player);
		isRunning = false;
		stop.setVisibility(View.INVISIBLE);
		start.setVisibility(View.INVISIBLE);
		et = (EditText)findViewById(R.id.nameOfFile);
		hz = (EditText)findViewById(R.id.hertz);
		rg1 = (RadioGroup) findViewById(R.id.chooseFormat);
		rg2 = (RadioGroup) findViewById(R.id.chooseEncoding);
		rg1.setOnCheckedChangeListener(this);
		rg2.setOnCheckedChangeListener(this);
        
		start.setOnClickListener(new OnClickListener(){//start button on click method

			@Override
			public void onClick(View v) {
				name = et.getText().toString();
				int rate;
				if(!hz.getText().toString().equals("")){
			    rate = Integer.parseInt(hz.getText().toString());
				}else{
				rate = 0;
				}
				
				recorder = new MediaRecorder();
				start.setVisibility(View.INVISIBLE);
				stop.setVisibility(View.VISIBLE);
				mFileName = getExternalCacheDir()+"";
		        mFileName += "/"+name;
		        Log.d("DIRECTORYY",getExternalCacheDir()+"");
				recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
				//make radiobuttons control encoder and format, as well as set the sampling rate
				recorder.setOutputFormat(format);
				recorder.setOutputFile(mFileName);
				recorder.setAudioEncoder(encoding);
				
				if(rate!=0){
					recorder.setAudioSamplingRate(rate);
					
				}else{
					Toast.makeText(getApplicationContext(), "Sampling rate set to default", Toast.LENGTH_SHORT).show();
				}
				try{
					recorder.prepare();
				}catch(Exception e){
					Log.d("NO","NOT WORK AUDIO" + e.getMessage());
				}
				recorder.start();
				isRunning = true;
			}
		});
		
		stop.setOnClickListener(new OnClickListener(){//stop button onclick method

			@Override
			public void onClick(View v) {
				if(isRunning){
					recorder.stop();
					recorder.release();
					stop.setVisibility(View.INVISIBLE);
					start.setVisibility(View.VISIBLE);
					Toast.makeText(getApplicationContext(), "Saved as: "+name, Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		play.setOnClickListener(new OnClickListener(){//play button onclick method

			@Override
			public void onClick(View v) {
            MediaPlayer player;
            player = new MediaPlayer();
				    
				    try {
				        player.setDataSource(getExternalCacheDir()+"/"+et.getText().toString());
				        player.prepare();
				        player.start();
				    } catch (Exception e) {
				        e.printStackTrace();
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
		getMenuInflater().inflate(R.menu.microphone, menu);
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
	public void onCheckedChanged(RadioGroup arg0, int rbid) {//change format or encoding
		//formats
        if(rbid == R.id.aac){
	        format = MediaRecorder.OutputFormat.AAC_ADTS;
	        Toast.makeText(getApplicationContext(), "Requires Minimum  of JellyBean APIs", Toast.LENGTH_SHORT).show();
	        start.setVisibility(View.VISIBLE);
        }
		if(rbid==R.id.mpeg4){
			format = MediaRecorder.OutputFormat.MPEG_4;
			start.setVisibility(View.VISIBLE);
		}
		if(rbid==R.id.threeGP){
			format = MediaRecorder.OutputFormat.THREE_GPP;
			start.setVisibility(View.VISIBLE);
		}
		//encoding options
		if(rbid==R.id.encodeAac){
		encoding = MediaRecorder.AudioEncoder.AAC;
		start.setVisibility(View.VISIBLE);
		}
		if(rbid==R.id.aaceld){
		encoding = MediaRecorder.AudioEncoder.AMR_NB;
		start.setVisibility(View.VISIBLE);
		}
		if(rbid==R.id.amrwb){
		encoding = MediaRecorder.AudioEncoder.AMR_WB;
		start.setVisibility(View.VISIBLE);
		}
		if(rbid==R.id.heaac){
		encoding = MediaRecorder.AudioEncoder.HE_AAC;
		start.setVisibility(View.VISIBLE);
		Toast.makeText(getApplicationContext(), "Requires Minimum  of JellyBean APIs", Toast.LENGTH_SHORT).show();
		}
	}

}
