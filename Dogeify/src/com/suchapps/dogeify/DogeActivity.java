package com.suchapps.dogeify;

import android.annotation.SuppressLint;
import android.app.*;
import android.app.AlertDialog.Builder;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.net.*;
import android.os.*;
import android.provider.MediaStore.*;
import android.provider.MediaStore.Images.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.io.*;
import java.util.GregorianCalendar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

@SuppressLint("NewApi")
public class DogeActivity extends Activity {

//declare these motherfuckers
DogeView dv;
AlertDialog ad;
AlertDialog.Builder adb;
LinearLayout ll;
Bitmap b;
EditText input;
File file;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//link all dem to the ids 
		setContentView(R.layout.doge);
	    adb = new AlertDialog.Builder(this);
	    
		file = new File(Environment.getExternalStorageDirectory()+File.separator+"temp.png");
	    Toast.makeText(this,"Click to add text, drag to place doge",Toast.LENGTH_SHORT).show();
		
		//make action bar reddish
	    if(android.os.Build.VERSION.SDK_INT>=11){
			ActionBar ab = getActionBar();
			ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#99cc00")));
	        }  
        
		//link all of the widget
	    ll = (LinearLayout) findViewById(R.id.layout);
		
		
		//declare a new dogeview and then add it
		dv = new DogeView(this);
		
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);

		
		
		AdView adView;
		
        adView = (AdView) findViewById(R.id.adView2);
      //  adView.setAdSize(AdSize.BANNER);
     //   adView.setAdUnitId(AD_UNIT_ID);
        AdRequest adRequest = new AdRequest.Builder()
        .setBirthday(new GregorianCalendar(2000, 1, 1).getTime())
        .build();
//ll.addView(adView);
    // Start loading the ad in the background.
        //LinearLayout ll2 = (LinearLayout) findViewById(R.id.linearLayout2);
        //ll2.addView(adV)
    adView.loadAd(adRequest);
   

    
		ll.addView(dv,lp);
		
		
		
		
        dv.setOnTouchListener(new OnTouchListener(){//all of the touching goes to the activity, not the view  	
			@Override
			public boolean onTouch(View arg0, MotionEvent me) {
				dv.setItsX(me.getX());//update the x and y values!
            	dv.setItsY(me.getY());
            	
            	//if it's up AND IT'S ON THE UNDO BUTTON!!
            	if(MotionEvent.ACTION_UP==me.getAction()&&me.getX()>dv.startUndoX && me.getY()>dv.startUndoY){
         dv.drawLine=false;

            		dv.undo();
       		
            	}else{//if its not in the undo button then dont even do anything else.. like srsly
            	
            if(dv.getMode()==DogeView.MODE_TEXT&&me.getAction()==MotionEvent.ACTION_UP || me.getAction()==MotionEvent.ACTION_UP&&dv.getMode()==DogeView.MODE_CROP&&dv.lineLength<=25){//if i'm supposed to draw anything && end of an action
            	ad.show(); 
            	input.requestFocus();
            	dv.drawLine=false;
            }
            if(me.getAction()==MotionEvent.ACTION_DOWN){//the user has pressed down, so start drawing the crop line
            	dv.startLine();//this will start drawing the line
            }
            if(me.getAction()==MotionEvent.ACTION_MOVE){//this means that it should start drawing the line
            	dv.setMode(DogeView.MODE_CROP);//MAKE SURE THIS TURNS OFF AFTER IT HAS BEEN CROPPED!!!
            }
            if(me.getAction()==MotionEvent.ACTION_UP&&dv.getMode()==DogeView.MODE_CROP&&dv.lineLength>25){//if action ends and the doge is cropped, then
            	dv.endLine();
            	dv.setMode(DogeView.MODE_TEXT);
            }
            	}
		  
				dv.invalidate();//makes sure to redraw it
				return true;
			}
            	
        });
		
		
		
		dv.setMode(DogeView.MODE_TEXT);//which means that the user inputs text WILL BE CHANGED LATER
		dv.requestFocus(View.FOCUS_DOWN);//takes focus awway from edittext

		
		
		//create the alertdialogbuilder yessss
		input = new EditText(this);
		input.setOnFocusChangeListener(new View.OnFocusChangeListener() {//this basically means when it's focused open keyboard
		    @Override
		    public void onFocusChange(View v, boolean hasFocus) {
		        if (hasFocus) {
		            ad.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		        }
		    }
		});
		input.setFocusableInTouchMode(true);
		input.setFocusable(true);
		input.setHint("such character");
	    adb.setView(input);
	    adb.setPositiveButton("much enter",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {//if you selected yes, then add the text!
				dv.addText(input.getText().toString());
				input.setText("");
			}
		});
	    adb.setNegativeButton("noep",new DialogInterface.OnClickListener() {		
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				input.setText("");
            arg0.cancel();
			}
		});
	    adb.setTitle("very text");
		ad = adb.create();
		//all specifications met for ALERT DIALOG BUILDER!!! AND ITS CREATED TOO
		
		
		
	}

	
	
	public void setImage(Bitmap b){
		dv.dogeface = b;
		dv.invalidate();
	}
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menue, menu);	
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {//THIS SHIT IS LIKE FOR THE FUCKING ACTION BAR GOD DAMMIT
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	    
	    case R.id.save://YOU GOTTA FUCKING SAVE THAT GODDAMN SHIT YO	    	
	    save();
	    break;
		
	    case R.id.share://FUCKING SHARE THAT SHIT
        share();
	    break;
	   
	    case R.id.clear://clear everything on image
	    	dv.clear();
	    	break;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
	
	private void share(){


		dv.showUndo=false;
		dv.invalidate();//make sure there's no undo button visible there
        Bitmap icon = dv.getDrawingCache();
		dv.showUndo=true;
		dv.invalidate();//then put it back!


		Intent share = new Intent(Intent.ACTION_SEND);//ok this intent will share
		share.setType("image/jpg");//it's an image btw.

		ContentValues values = new ContentValues();
		values.put(Images.Media.TITLE, "dogeified");//aight so its named  dat
		values.put(Images.Media.MIME_TYPE, "image/jpg");//and its an image!


		Uri uri = getContentResolver().insert(Media.EXTERNAL_CONTENT_URI,//not sure, but like this makes a uri
											  values);


		OutputStream outstream;//ok
		try {
			outstream = getContentResolver().openOutputStream(uri);
			icon.compress(Bitmap.CompressFormat.PNG, 100, outstream);//ok so now like compress dat shit and get ready mofo
			outstream.close();//close dat shit
		} catch (Exception e) {
			Log.d("io prob",e.getMessage());
		}

		share.putExtra(Intent.EXTRA_STREAM, uri);//kk then share it
		startActivity(Intent.createChooser(share, "Share Image"));
		
		
	}

	private void save(){
		
		dv.showUndo=false;
		dv.invalidate();//make sure there's no undo button visible there
        Bitmap image = dv.getDrawingCache();
		dv.showUndo=true;
		dv.invalidate();//then put it back!
		//removeSideColor(Color.parseColor("ff4444"),image);

		File text = new File(Environment.getExternalStorageDirectory()+"/doges");
		if(text.mkdirs()){
			Toast.makeText(getApplicationContext(),"new folder created",Toast.LENGTH_LONG).show();
		}

        try{	//this saves the file
        	int i = 0;
    		File f = new File(Environment.getExternalStorageDirectory()+"/doges"+"/doge0.png");

        	while(f.exists()){
        		f = new File(Environment.getExternalStorageDirectory()+"/doges"+"/doge"+i+".png");
        		i++;
        	}
			FileOutputStream fos = new FileOutputStream(f);
			image.compress(Bitmap.CompressFormat.PNG, 100, fos);//compress that shit nigga

			Toast.makeText(getApplicationContext(),"Saved as: "+f.getPath(),Toast.LENGTH_LONG).show();
		}catch(Exception e){
			Log.d("NO WORK", "NOT SAVE"+e.getMessage());
		}
		
	}
	
	public void back(){
		super.onBackPressed();
	}
	@Override
	public void onBackPressed(){
	  //s  boolean endit = false;
		AlertDialog.Builder swagger420 = new AlertDialog.Builder(this);
		    swagger420.setPositiveButton("yeah",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {//if you selected yes, then add the text!
					back();
					
				}
			});
		    swagger420.setNegativeButton("noep",new DialogInterface.OnClickListener() {		
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					arg0.cancel();
				}
			});
		    swagger420.setTitle("abandon ur masterpice?");
			swagger420.create().show();
		
		
	}
	
	
	public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) 
	{ // BEST QUALITY MATCH
	     
	    //First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(path, options);
	 
	    // Calculate inSampleSize, Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    options.inPreferredConfig = Bitmap.Config.RGB_565;
	    int inSampleSize = 1;
	 
	    if (height > reqHeight) 
	    {
	        inSampleSize = Math.round((float)height / (float)reqHeight);
	    }
	    int expectedWidth = width / inSampleSize;
	 
	    if (expectedWidth > reqWidth) 
	    {
	        //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
	        inSampleSize = Math.round((float)width / (float)reqWidth);
	    }
	 
	    options.inSampleSize = inSampleSize;
	 
	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	 
	    return BitmapFactory.decodeFile(path, options);
	}
	
	
	
	
	
	
}
