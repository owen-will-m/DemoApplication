package com.suchapps.dogeify;


import android.annotation.SuppressLint;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import android.provider.*;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.*;

import java.io.*;
import java.util.GregorianCalendar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

@SuppressLint("NewApi")
public class MainActivity extends Activity
{
//Declarations of variables
	//private static final String AD_UNIT_ID = "ca-app-pub-5228682528822987/2677296950";
	AdView adView;
	Bitmap bit;
	InputStream inp;
	File file;
	SelectView s;
	//Variable central mofo
	


    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if(android.os.Build.VERSION.SDK_INT>=11){
    		ActionBar ab = getActionBar();
    		ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#99cc00")));
            }  
        
        
        
        
        
		//set the temp file location
		file = new File(Environment.getExternalStorageDirectory()+File.separator+"temp.png");
		
		//create a view that has the two options on it
		s = new PictureView(this);
		//make it fit to the entire screen
		LinearLayout.LayoutParams lp= new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.MATCH_PARENT,
		LinearLayout.LayoutParams.MATCH_PARENT
		);
		
		//then finally set it to the screen!
		LinearLayout ll= (LinearLayout) findViewById(R.id.linearLayout1);
		
		
		
//ad stuff
        
        adView = (AdView) findViewById(R.id.adView);
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
   

    

    ll.addView(s,lp);
        
        
        
        
        //ad stuff
		
		
    }
	
    
    
    
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{//we can receive the camera and gallery intents up in here...
		if(requestCode==PictureView.CAMERA){
			//YOU JUST RECEIVED A CAMERA THING
			
			/*
			//must replace this with decode.....file()
			try{
				InputStream is = new FileInputStream(file);
				//open up the file that was taken from the camera
				bit=BitmapFactory.decodeStream(is);
				is.close();
			}catch(Exception e){
				Toast.makeText(this,"This is your fault",Toast.LENGTH_SHORT).show();
			}
			*/
			bit = decodeSampledBitmapFromFile(file.toString(),s.getWidth(),s.getHeight());
			
			try{
				bit = Inter.applyOrientation(bit, Inter.resolveBitmapOrientation(file));
			} catch(Exception e){
				Toast.makeText(this,"wow",Toast.LENGTH_SHORT).show();
			}
			
			startCrop();
			}
			
			
		if(requestCode==PictureView.GALLERY){
			//YOU JUST GOT A GALLERY THING!
			try{
				bit = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
			}catch(Exception e){
			//	Toast.makeText(this,"You suck",Toast.LENGTH_SHORT).show();
			}
			try{
				bit = Inter.applyOrientation(bit, Inter.resolveBitmapOrientation(file));
			} catch(Exception e){
				Toast.makeText(this,"wow",Toast.LENGTH_SHORT).show();
			}
			

			startCrop();
	
		}	
		
		
			
		if(requestCode==Inter.CROPPED){
			//start doge activity
		//	Toast.makeText(this,"You suck",Toast.LENGTH_SHORT).show();
			Intent i = new Intent(this,CustomDoge.class);
			i.putExtra("heighty", s.getHeight())
			.putExtra("widthx",s.getWidth());
		startActivity(i);
			
		}
		
		
		
	}

	private void startCrop()
	{
		//start crop activity for result
		if(bit!=null){
			
			Toast.makeText(this,"Compressing... please wait",Toast.LENGTH_SHORT).show();
			try{

				FileOutputStream fos= new FileOutputStream(file);
				bit.compress(Bitmap.CompressFormat.PNG,100,fos);
				fos.close();
			}catch(Exception e){
				Toast.makeText(this,e.toString()+" probs with FOS",Toast.LENGTH_SHORT).show();
			}
			
			Intent yolo = new Intent(this, CropActivity.class)
			.putExtra("height",s.getHeight())
			.putExtra("bitH",bit.getHeight())
			.putExtra("bitW",bit.getWidth());
		startActivityForResult(yolo,Inter.CROPPED);
		}else{
			//we gots a problem
		//	Toast.makeText(this,"Ugh...",Toast.LENGTH_SHORT).show();
		}
	}
		
//this method prevents outofmemoryerror, no longer have to make inputstreams!
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
