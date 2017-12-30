package com.suchapps.dogeify;
import android.app.*;
import android.content.*;
import android.content.pm.ActivityInfo;
import android.net.*;
import android.os.*;
import android.provider.*;
import android.widget.*;

import java.io.*;

public class PictureView extends SelectView 
{
	Context c;
	File file;
	public static final int CAMERA = 420;
	public static final int GALLERY = 420000;
	public PictureView(Context c){
		super(c);
		this.c = c;
		file = new File(Environment.getExternalStorageDirectory()+File.separator + "temp.png");
	}

	@Override
	public void actionTop()
	{
	//opens adb asks for gallery or camera 
AlertDialog.Builder adb = new AlertDialog.Builder(c);
	adb.setTitle("chose pickture");
	adb.setCancelable(true);
		adb.setPositiveButton("take photoe", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					//ask for camera
					doCamera();
				
					//start intermediary activity with extra as camera
				}

			
			
	});
		adb.setNegativeButton("chose galery", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					//ask for gallery
					chose();
					//start intermediary with extra as gallery
				}	
			});
	adb.create().show();
//show it
	}

	
	
	@Override
	public void actionBottom()
	{
		//opens dogeactivity with no bmp
		//Toast.makeText(c,"work",Toast.LENGTH_SHORT).show();
		
		c.startActivity(new Intent(c, DogeActivity.class));
		
		
		
	}
	
	private void doCamera()	{
		// TODO: Implement this method
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		Toast.makeText(c, "Portrait is better",Toast.LENGTH_SHORT  ).show();
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
	
	Activity t =	(Activity)c;
		t.	startActivityForResult(intent,CAMERA);
		
		
		
	}	
	public void chose(){
		
		Intent dIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		
		Toast.makeText(c, "Please be patient with gallery, cut him some slack",Toast.LENGTH_SHORT  ).show();
		Activity t =	(Activity)c;
		t.	startActivityForResult(dIntent,GALLERY);
		
		
	}

	
}
