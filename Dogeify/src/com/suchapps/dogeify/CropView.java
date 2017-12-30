package com.suchapps.dogeify;
import android.content.*;
import android.graphics.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

public class CropView extends View implements OnTouchListener
{

	
	
	
	//declaration
	boolean beingCached;
	Bitmap image;//bg image 
	float x;//coords of the rectangle
	float y;
	int rectWidth;//width and height of rectangle
	int rectHeight;
	Paint white;//translucent white
	Paint dark;//translucent dark
	Matrix m;//to fit image
	int screenWidth;//screen dims
	int screenHeight;
	Context c;
	Bitmap bit;
	Bitmap temp;
	public int rotate;
	Bitmap bg;
	boolean drawEverything = false;
	int givenHeight;
	//constructor
	public CropView(Context c,Bitmap b){
		super(c);//call superconstructor
		this.setDrawingCacheEnabled(true);
		//setImg(b);
		
		//this.setBackgroundColor(Color.parseColor("#ff4444"));
		
		rotate=0;
		this.c=c;
		bg=b;
		image=b;
		this.setOnTouchListener(this);
		//make dark and white paints
		dark = new Paint();
		dark.setStyle(Paint.Style.FILL);
		dark.setColor(Color.argb(200,0,0,0));
		white = new Paint();
		white.setStyle(Paint.Style.FILL);
		white.setColor(Color.argb(55,255,255,255));
		//set default points
		beingCached=false;
		
	}

	public void rotate()
	{
		// TODO: Implement this method
		Matrix matt = new Matrix();
		matt.postRotate(90);
		bg = Bitmap.createBitmap(bg, 0, 0, bg.getWidth(), bg.getHeight(), matt, false);
		this.setImg(bg);//rewrites the matrix to accomodate the change in rotation
		
		invalidate();
	}
	
	@Override
	public boolean onTouch(View p1, MotionEvent me)
	{
		x = me.getX()-rectWidth/2;
		y = me.getY()-rectHeight/2;

		invalidate();
		
		//simply move the rectangle
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		//draw background			
			canvas.drawBitmap(image,m,dark);
		//draw dark over it
     //draw rectangle
	 drawRect(canvas);
		
	}
	

	
	public void drawRect(Canvas c){
		//draw the rectangle
		if(!beingCached){
			if(x<0){
				x=0;
			}
			if(y<0){
			y=0;	
			}
			if(x+rectWidth>screenWidth){
				x=screenWidth-rectWidth;
			}
			if(y+rectHeight>screenHeight){
				y=screenHeight-rectHeight;
			}
		c.drawRect(x,y,x+rectWidth,y+rectHeight,white);
	}
		}
	
	public void big(){//make rectangle larger
	if(rectWidth<screenWidth){
		rectWidth=(int)( rectWidth + rectWidth*.2);
		rectHeight=(int)(rectHeight + rectHeight *.2);
	//	Toast.makeText(c,rectWidth+","+rectHeight,Toast.LENGTH_SHORT).show();
		invalidate();
		}
	}
	public void small(){//make rectangle smaller
	if(rectWidth>screenWidth/10){
		rectWidth=(int) (rectWidth*.8);
		rectHeight=(int)( rectHeight*.8);
	//	Toast.makeText(c,rectWidth+","+rectHeight,Toast.LENGTH_SHORT).show();
		invalidate();
		}
	}
	
	public void setImg(Bitmap b){
		image=b;
		float	height = this.getHeight()-1;
		float 	width  = this.getWidth()-1;

		float x1 = width / image.getWidth();
		float y1 = height / image.getWidth();
        //only based off of the width so that the aspect ratio is maintained
		m = new Matrix();
		//if it's turned sideways then 
		/*
		if(rotate%2!=0){
			m.postScale(y1,y1);
			
		}
		else{
		*/
			//it's vertical, so should be based off of width
			m.postScale(x1,x1);//also matrix stuff
	
//	m.postRotate(90*rotate);
			invalidate();
	}
	 
	
	
	
	public Bitmap crop(){
		//returns cropped bitmap
		
		//remove all color to everything
		dark=new Paint();
		white.setColor(Color.argb(0,0,0,0));
		invalidate();
		try{
		temp = this.getDrawingCache(true);
	bit = Bitmap.createBitmap(temp,(int)x,(int)y,rectWidth,rectHeight,null,true);
		 //this.setImg(bit);
		//get drawing cache
		}catch(Exception e){
			Toast.makeText(c, e.getClass().toString()+"src dims:"+temp.getWidth() +""+temp.getHeight()+ "Attempt dims:" +(int)(y)+","+(int)(x)+" "+(int)(x+rectWidth)+","+(int)(y+rectHeight)
			,Toast.LENGTH_LONG).show();
		}
		//use rect coords to get cropped bitmap and return it		
		
		dark.setStyle(Paint.Style.FILL);
		dark.setColor(Color.argb(200,0,0,0));
		 white.setColor(Color.argb(55,255,255,255));
		return bit;
	} 
	
	
	
	
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)//set proper width and height
	{
		//WILL HAVE TO ACCOMODATE FOR SIDEWAYS IMAGE AND USE DIFFERENT MATRIX
		setImg(bg);
		//set height of screen dims
		screenWidth =w;
		screenHeight=h;
		x = w/2;
		y=h/2;
		rectWidth=w/5;
		rectHeight=givenHeight/5; 
	//	reMatrix(w,h);
		super.onSizeChanged(w, h, oldw, oldh);
	}

	
	
	public void reMatrix(int w, int h){
	
		Matrix matt = new Matrix();
		matt.postRotate(90*rotate);
		
		image = Bitmap.createBitmap(image,0,0,screenHeight,screenWidth,matt,true);
		
		rectWidth= w/5;
		rectHeight=h/5;
		Toast.makeText(c,w+"",Toast.LENGTH_SHORT).show();
		//create matrix
		try{
	
			float	height = this.getHeight()-1;
			float 	width  = this.getWidth()-1;

			float x1 = width / image.getWidth();
			float y1 = height / image.getHeight();

			m = new Matrix();
			m.postScale(x1,y1);//also matrix stuff
		}catch(Exception e){

		}
		invalidate();
		
	}
}
