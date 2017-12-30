package com.example.demoapplication;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.widget.TextView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
public class DoodlePad extends TextView implements OnTouchListener{
Paint p = new Paint();
ArrayList<Dot> arr = new ArrayList<Dot>();
	//constructor
	public DoodlePad(Context c){
		super(c);
		this.setBackgroundColor(Color.RED);
		this.setHeight(120);
		this.setFocusable(true);
		this.setFocusableInTouchMode(true);
		this.setOnTouchListener(this);
		p.setColor(Color.BLUE);
		p.setStyle(Paint.Style.STROKE);
	}
	public void onDraw(Canvas canvas) {
	    Path path = new Path();

	    if(arr.size() > 1){
	        for(int i = arr.size() - 2; i < arr.size(); i++){
	            if(i >= 0){
	                Dot point = arr.get(i);

	                if(i == 0){
	                    Dot next = arr.get(i + 1);
	                    point.dx = ((next.x - point.x) / 3);
	                    point.dy = ((next.y - point.y) / 3);
	                }
	                else if(i == arr.size() - 1){
	                    Dot prev = arr.get(i - 1);
	                    point.dx = ((point.x - prev.x) / 3);
	                    point.dy = ((point.y - prev.y) / 3);
	                }
	                else{
	                    Dot next = arr.get(i + 1);
	                    Dot prev = arr.get(i - 1);
	                    point.dx = ((next.x - prev.x) / 3);
	                    point.dy = ((next.y - prev.y) / 3);
	                }
	            }
	        }
	    }

	    boolean first = true;
	    for(int i = 0; i < arr.size(); i++){
	        Dot point = arr.get(i);
	        if(first){
	            first = false;
	            path.moveTo(point.x, point.y);
	        }
	        else{
	            Dot prev = arr.get(i - 1);
	            path.cubicTo(prev.x + prev.dx, prev.y + prev.dy, point.x - point.dx, point.y - point.dy, point.x, point.y);
	        }
	    }
	    canvas.drawPath(path, p);
	}

	//@Override
	public boolean onTouch(View view, MotionEvent event) {
	    if(event.getAction() != MotionEvent.ACTION_UP){
	        Dot point = new Dot(1,1);
	        point.x = event.getX();
	        point.y = event.getY();
	        arr.add(point);
	        invalidate();
	       // Log.d(TAG, "point: " + point);
	        return true;
	    }
	    return super.onTouchEvent(event);
	}
	class Dot{
		public float dy;
		public float dx;
		private float x;
		private float y;
		
		public Dot(float f, float g){
			this.x =f;
			this.y=g;
			
		}
		
	}
}