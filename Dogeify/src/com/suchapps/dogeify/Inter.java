package com.suchapps.dogeify;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.provider.*;
import android.widget.*;
import java.io.*;

public class Inter extends Activity
{
	public static final int CAMERA = 420;
	public static final int GALLERY = 420000;
	public static final int CROPPED = 4200;
ImageView iv;
	private File file;
	
	
	
	//constructor
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		setContentView(R.layout.inter);
		file = new File(Environment.getExternalStorageDirectory()+File.separator + "temp.jpg");
		
		iv = (ImageView) findViewById(R.id.imageView);
		
		
	if(	getIntent().getExtras().getBoolean("camera")){
		
		//start camera activity for result
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
	
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		startActivityForResult(intent,CAMERA);
	}else{
		
		//start gallery for result
		Intent dIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		Toast.makeText(this,"USE GALLERY",Toast.LENGTH_SHORT).show();
		startActivityForResult(dIntent, GALLERY);
	}
	
	
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		
		
		Bitmap bit = null;

		
		if(requestCode==CAMERA){
			
			//get bmp 
		 
			try{
				InputStream is = new FileInputStream(file);
				bit=BitmapFactory.decodeStream(is);
				
			is.close();
			}catch(Exception e){
				Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
			}
			if(bit!=null){
		
				//start doge with it
				
				try{
				bit = applyOrientation(bit, resolveBitmapOrientation(file));
				} catch(Exception e){
					Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
				}
			
			
				//thats it
			}
			}
			

			
			if(requestCode==GALLERY){
				if(data==null)
					super.onBackPressed();//go back
					
				Toast.makeText(this,"gallery",Toast.LENGTH_SHORT).show();
	    	//get bmp and start doge with it
		
		try{
			bit = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
		}catch(Exception e){
			Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
		}
		
		}
		
		//start doge with it
		if(bit!=null){
	
			try{

				FileOutputStream fos= new FileOutputStream(file);
				bit.compress(Bitmap.CompressFormat.JPEG,100,fos);
				fos.close();
			}catch(Exception e){
				Toast.makeText(this,e.toString()+" inter.java probs with FOS",Toast.LENGTH_SHORT).show();
				super.onBackPressed();
			}
			
			this.startActivity(new Intent(getApplicationContext(),CropActivity.class));
			iv.setImageBitmap(bit);
		
		}else{
			Toast.makeText(this,"bit was null",Toast.LENGTH_SHORT).show();
		}
			

	

		}
		
			

		
		
		
		
		
		
	
	public static int resolveBitmapOrientation(File bitmapFile) throws IOException {
        ExifInterface exif = null;
        exif = new ExifInterface(bitmapFile.getAbsolutePath());

        return exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
    }
		
	public static Bitmap applyOrientation(Bitmap bitmap, int orientation) {
        int rotate = 0;
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotate = 270;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotate = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotate = 90;
                break;
            default:
                return bitmap;
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix mtx = new Matrix();
        mtx.postRotate(rotate);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }
		
}
/*


*/
