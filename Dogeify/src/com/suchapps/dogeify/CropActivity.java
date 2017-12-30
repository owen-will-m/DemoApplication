package com.suchapps.dogeify;
import android.annotation.SuppressLint;
import android.app.*;
import android.content.Intent;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.io.*;

@SuppressLint("NewApi")
public class CropActivity extends Activity implements OnClickListener
{


	
	//in this activity the user will crop an image 
	// and return a bitmap with setresult
	
	//declarations
	Button save;
	CropView cv;
	Bitmap temp;
    int bitW;
    int bitH;
	private File file;
	//declarations
	
	//constructor
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
		file = new File(Environment.getExternalStorageDirectory()+File.separator + "temp.png");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop);
		//set the crop view
       
        if(android.os.Build.VERSION.SDK_INT>=11){
		ActionBar ab = getActionBar();
		ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#99cc00")));
        }        
		//receive bitmap, filepath from extras
		
		Toast.makeText(this,"Crop the image you would like to dogeify, then press \"YEA\"",Toast.LENGTH_LONG).show();
		int bitW = (Integer) getIntent().getExtras().get("bitW");
		int bitH = getIntent().getExtras().getInt("bitH");
	
	    temp = decodeSampledBitmapFromFile(file.toString(),bitW,bitH);
			
		int heighty = (Integer)this.getIntent()
		.getExtras().get("height");
	    cv = new CropView(this,temp);
        cv.givenHeight = heighty;
		RelativeLayout.LayoutParams lp= new RelativeLayout.LayoutParams(
			RelativeLayout.LayoutParams.MATCH_PARENT,
			RelativeLayout.LayoutParams.MATCH_PARENT
		);

		RelativeLayout ll= (RelativeLayout) findViewById(R.id.relativeLayout1);
		ll.addView(cv,lp);

		
		
		
		
		Button b1 = (Button)findViewById(R.id.button1);
		Button b2 , b3,rotate;
		rotate=(Button) findViewById(R.id.rotate);
		b2=(Button) findViewById(R.id.button3);
		b3=(Button) findViewById(R.id.button4);
		b1.setOnClickListener(this);
		save = (Button)findViewById(R.id.button2);
		save.setOnClickListener(this);
		b1.bringToFront();
		save.bringToFront();
		b2.bringToFront();
		b3.bringToFront();
		//rotate.setVisibility(View.GONE);
		rotate.bringToFront();
	    rotate.setOnClickListener(this);
		b2.setOnClickListener(this);
		b3.setOnClickListener(this);
	 
    }

	@Override
	public void onBackPressed()
	{
		// TODO: Implement this method
		
		
		super.onBackPressed();
	}
	
	
	
	@Override
	public void onClick(View p1)// clickers for the yea and naw
	{
		if (p1.getId()==R.id.button1){
			startActivity(new Intent(this,MainActivity.class));
		}
		if(p1.getId()==R.id.button2){
			Bitmap temp= cv.crop();
			//save it
			try{
			FileOutputStream fos = new FileOutputStream(file);
			temp.compress(Bitmap.CompressFormat.PNG,100,fos);
			fos.close();
			}catch(Exception e){
				Toast.makeText(this,"Why you make such mistake",Toast.LENGTH_SHORT).show();
			}
			finish();
			//startactivity the dogeact. with bmp as an extra
			
			/*
			
		Bitmap bm =	cv.crop();
			
			try{
				FileOutputStream fos = new FileOutputStream(file);
				bm.compress(Bitmap.CompressFormat.JPEG,100,fos);
				fos.close();
				//write the file for further use
			}catch(Exception e){
			
			}
			this.finish();
			*/
		}
		
		
if(p1.getId()==R.id.rotate){
	cv.rotate();
}
		if(p1.getId()==R.id.button3){
			//increase rectangle size
			cv.big();
		}
		if(p1.getId()==R.id.button4){
			//decrease rectangle size
			cv.small();
		}
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
