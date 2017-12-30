package com.suchapps.dogeify;
import android.graphics.*;
import android.os.*;
import java.io.*;

public class CustomDoge extends DogeActivity
{
File file;
int height;
int width;
Bitmap temp;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		file = new File(Environment.getExternalStorageDirectory()+File.separator+"temp.png");
		
		try{
			temp = decodeSampledBitmapFromFile(file.toString(),
					(Integer)getIntent().getExtras().get("widthx"),
					(Integer)getIntent().getExtras().get("heighty"));
		
		super.setImage(temp);
		
		
		file.delete();
		}catch(Exception e){
			
		}
		
	}

	
	
}
