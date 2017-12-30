package com.suchapps.dogeify;
import android.annotation.SuppressLint;
import android.content.*;
import android.graphics.*;
import android.view.*;
import java.io.*;
import org.xml.sax.helpers.*;
import android.widget.*;
import android.view.View.*;
import java.util.*;
import android.support.v4.view.*;

@SuppressLint("NewApi")
public class SelectView extends View implements OnTouchListener
{
	public static final int CROPPED = 420;
	
	boolean dragged = false;
	boolean dragged2= false;
	int dx=0;
	int dy=0;
	int rectX=0;
	int rectY=0;
    boolean fuckErThing;
	int rectX2 =0;
	int rectY2=0;
	Timer t;
	Bitmap logo;
	Context c;
	int width;
	int height;
	Bitmap doge;
	InputStream inp;
	Matrix m;
	Paint red;
	Context con;
	Paint defaultp;
	Bitmap bit;
	int rectWid;
	int rectHigh;
	Bitmap ChoosePicture;
	Bitmap Default;
	Paint blue;
	Paint regular;
	String topText ="chose\n pickture";
	String botText="deffault";
	int randx;
	int randy;
	int randx1;
	int randy1;
	Paint regular2;
	public SelectView(Context c){
		super(c);//call superconstructor with applications context
	this.c=c;
		 con=c;//make universal
		 //make it able to be touched	 
		 this.setClickable(true);
		 this.setOnTouchListener(this);
		 
		try{
			 bit = BitmapFactory.decodeStream(c.getAssets().open("dogefull.png"));
			
		}catch(Exception e){
			Toast.makeText(c,e.getMessage()+ "420",Toast.LENGTH_SHORT).show();
		}
		m= new Matrix();
		
		 
		 
		 int ar = Color.argb(50,100,255,100);
		defaultp=new Paint();//never instantiate a new paint again in ondraw();
		red=new Paint();
		red.setAntiAlias(true);
		 
		red.setColor(ar);
		if(android.os.Build.VERSION.SDK_INT>=11){
			red.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
			 }else{
				 red = new Paint();
			 }
		
		regular = new Paint();
		regular.setTextSize(50+new Random().nextInt(20));	
		regular.setColor(randomColor());
		
		regular2 = new Paint();
		regular2.setTextSize(50+new Random().nextInt(20));
		regular2.setColor(randomColor());
		//SET RECT DIMS
		
		//each time that the view is instantaited the coords of the text will change, lol
	
		
		
		
		blue=new Paint();
		ar = Color.argb(50,255,100,100); 
		blue=new Paint();
		blue.setAntiAlias(true);
		//blue.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
		blue.setColor(ar);
		if(android.os.Build.VERSION.SDK_INT>=11){
			blue.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
			 }else{
				 blue = new Paint();
			 }
	t = new Timer();
}
	
	
	
	@Override
	public void onDraw(Canvas c){//draws stuff
		c.drawBitmap(bit,m,defaultp);
		drawTopRect(c);
		drawBotRect(c);

	}
	
	@Override
	public boolean onTouch(View p1, MotionEvent me)
	{


		if(isInBounds1(me)&&!dragged2){
			//if finger is touching top rect and the other one isnt being dragged
			if(me.getAction()==MotionEvent.ACTION_DOWN){
				dragged = true;//bool lets know now were getting dragged
				dx = (int) me.getX()-rectX;
				dy = (int) me.getY()-rectY;
				//set distances from finger 

			}
			if (me.getAction()==MotionEvent.ACTION_MOVE){

				rectX = (int) me.getX() - dx;
				rectY = (int) me.getY() - dy;
				invalidate();
			}
			if(me.getAction()==MotionEvent.ACTION_UP){
				dragged = false;//ok no longer being draggdd
				if(Math.abs(rectX)<10&&Math.abs(rectY)<10){//maybe start cropping?
					actionTop();
				}
				startReturn();//put dat rectangle back
			}

		}else if(isInBounds2(me)&&!dragged){

			if(me.getAction()==MotionEvent.ACTION_DOWN){
				dragged2 = true;
				dx = (int) me.getX() - rectX2;
				dy = (int) me.getY() - rectY2;

			}
			if (me.getAction()==MotionEvent.ACTION_MOVE){

				rectX2 = (int) me.getX() - dx;
				rectY2 = (int) me.getY() - dy;
				invalidate();
			}
			if(me.getAction()==MotionEvent.ACTION_UP){
				dragged2 = false;
				
				if(Math.abs(rectX2)<10&&Math.abs(rectY2)<rectHigh+10){//maybe start cropping?
					actionBottom();
				
				}
				//Toast.makeText(con,rectX2+","+rectY2,Toast.LENGTH_SHORT).show();
				//actionBottom();
				startReturn2();

			}



		}





		return true;
	}

	private void startReturn()
	{
		rectX=0;
		rectY=0;
		invalidate();
	}

	private void startReturn2(){
		rectX2 = 0;
		rectY2 = rectHigh;
		invalidate();
	}




	private boolean isInBounds2(MotionEvent me)
	{
		// TODO: Implement this method
		int myx = (int) me.getX();
		int myy = (int) me.getY();

		if(myx>rectX2&&myx<rectX2+rectWid&&myy>rectY2&&myy<rectY2+rectHigh){
			return true;
		}

		return false;
	}

	private boolean isInBounds1(MotionEvent me)
	{

		int myx = (int) me.getX();
		int myy = (int) me.getY();

		if(myx>rectX&&myx<rectX+rectWid&&myy>rectY&&myy<rectY+rectHigh){
			return true;
		}



		return false;
	}
	private void drawBotRect(Canvas c)
	{
		// TODO: Implement this method
		c.drawRect(rectX2,rectY2,rectX2+rectWid,rectY2+rectHigh,blue);
	//	c.drawBitmap(Default,null, new Rect(rectX2+30,rectY2+30,rectX2+rectWid-30,rectY2+rectHigh-30),regular);
		//ADD DRAWING THAT SAYS CHOOSE YOUR OWN PHOTO IN COMIC SANS

		c.drawText(botText,rectX2+randx,rectY2+randy,regular);
		
	}

	
	
	
	private void drawTopRect(Canvas c)
	{
	//	Toast.makeText(con,rectX+ " "+ rectWid, Toast.LENGTH_SHORT).show();
		c.drawRect(rectX,rectY,rectX+rectWid,rectY+rectHigh,red);
		//c.drawBitmap(ChoosePicture,null, new Rect(rectX+30,rectY+30,rectX+rectWid-30,rectY+rectHigh-30),regular);
		//ADD COMIC SANS THAT SAYS USE DEFAULT DOGE PICTURE
		c.drawText(topText,randx1+rectX,randy1+rectY,regular2);
	}

	
	
	
	
	
	
	
	
	
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)//set proper width and height
	{
		rectWid = getWidth();
		rectHigh= getHeight() /2;
		
		
	try{
		
		
		super.onSizeChanged(w, h, oldw, oldh);
		height = this.getHeight()-1;
		width  = this.getWidth()-1;
		rectY2=rectHigh;
		float x = (float)width / bit.getWidth();
		float y = (float)height / bit.getHeight();

		m = new Matrix();
		m.postScale(x,y);//also matrix stuff
		}catch(Exception e){
			
		}
		
		randx = new Random().nextInt(rectWid-200);
		randy = new Random().nextInt(rectHigh);
		randx1 = new Random().nextInt(rectWid-300);
		randy1 = new Random().nextInt(rectHigh);
		
	}
	

	
	
public void	setImage(Bitmap bit){
		//sets background image to thst immage
	this.bit=bit;
	}
	
public void	actionTop(){
	//implement in subclass	
	//start cropping
	
	
	//start intent that will open an activity where you select choose photo from gallery or take it from the camera
	//Intent i = new Intent(c, CropActivity.class);
	//c.startActivity(i);
	
	}
	public void actionBottom(){
		//to be implemented in subclass
	
	}
	
	public void setTopColor(int r,int g, int b){
		
}

	public void setBottomColor(int r,int g, int b){

	}

	private int randomColor() {
		int rand = new Random().nextInt(12) + 1;//random integer between 1 and 10
		switch (rand){
			case 1: return Color.parseColor("#9933CC");
			case 2:return Color.parseColor("#ffffff");
			case 3:return Color.parseColor("#0099CC");
			case 4:return Color.parseColor("#33B5E5");
			case 5:return Color.parseColor("#FF8800");
			case 6: return Color.parseColor("#FFBB33");
			case 7:return Color.parseColor("#99CC00");
			case 8:return Color.parseColor("#669900");
			case 9:return Color.parseColor("#AA66CC");
			case 10:return Color.parseColor("#FF4444");
			case 11: return Color.parseColor("#CC0000");
		}
		return Color.parseColor("#ffffff");
	}
	
	
	}
