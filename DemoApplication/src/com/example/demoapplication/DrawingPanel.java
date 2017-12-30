package com.example.demoapplication;

import java.util.LinkedList;

import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.View;
import android.widget.TextView;

public class DrawingPanel extends TextView implements OnTouchListener {
private static final String TAG = "DrawView";

private static final float MINP = 0.25f;
private static final float MAXP = 0.75f;

private Canvas  mCanvas;
private Path    mPath;
private Paint       mPaint;   
private LinkedList<Path> paths = new LinkedList<Path>();
boolean colorChanged = false;
boolean mDrawPoint;
public DrawingPanel(Context context, int height) {
    super(context);
    this.setDrawingCacheEnabled(true);
    this.setFocusable(true);
    this.setFocusableInTouchMode(true);
    this.setOnTouchListener(this);
    this.setHeight(height);
    this.setBackgroundColor(Color.WHITE);
    mPaint = new Paint();
    mPaint.setAntiAlias(true);
    mPaint.setDither(true);
    mPaint.setColor(Color.BLACK);
    mPaint.setStyle(Paint.Style.STROKE);
    mPaint.setStrokeJoin(Paint.Join.ROUND);
    mPaint.setStrokeCap(Paint.Cap.ROUND);
    mPaint.setStrokeWidth(6);
    mCanvas = new Canvas();
    mCanvas.drawColor(Color.WHITE);
    mPath = new Path();
    paths.add(mPath);
    mDrawPoint=false;
    LinkedList<LinkedList<Path>> list2 = new LinkedList<LinkedList<Path>>();
}               
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {            
  
        for (Path p : paths){
            canvas.drawPath(p, mPaint);
        }
        }

    

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    private void touch_start(float x, float y) {
    	mDrawPoint = true;
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }
    private void touch_move(float x, float y) {

        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
            mX = x;
            mY = y;
        	mDrawPoint = false;
        }
    }
    private void touch_up() {
    	if(mDrawPoint == true) {
            mCanvas.drawPoint(mX, mY, mPaint);          
        }else{
        mPath.lineTo(mX, mY);
        // commit the path to our offscreen
        mCanvas.drawPath(mPath, mPaint);
        // kill this so we don't double draw            
        mPath = new Path();
        paths.add(mPath);
        }
    }



@Override
public boolean onTouch(View arg0, MotionEvent event) {
      float x = event.getX();
      float y = event.getY();

      switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
              touch_start(x, y);
              invalidate();
              break;
          case MotionEvent.ACTION_MOVE:
              touch_move(x, y);
              invalidate();
              break;
          case MotionEvent.ACTION_UP:
              touch_up();
              invalidate();
              break;
      }
      return true;
}
public void setColor(int c) {
	// TODO Auto-generated method stub
	mPaint.setColor(c);
}
class colorPathArray{
	LinkedList<Path> list;
	int color;
	public colorPathArray(int color, LinkedList<Path> list){
		this.color = color;
		this.list = list;
		
	}
}

}  