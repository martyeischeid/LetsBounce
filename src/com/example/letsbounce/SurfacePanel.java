package com.example.letsbounce;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfacePanel extends SurfaceView implements SurfaceHolder.Callback {
	  Context context;
	  DrawThread drawThread;
	  Bitmap ball;
	  float x, y = 0;
	  float speed = 10;
	public SurfacePanel(Context ctxt, AttributeSet attrSet) {
		super(ctxt, attrSet);
		context = ctxt;		
		ball = BitmapFactory.decodeResource(context.getResources(), R.drawable.red);
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		drawThread.setRunning(false);
		boolean retry = true;
		while(retry) {
			try {
				drawThread.join();
				retry = false;
			} catch (Exception e) {
				Log.v("Exception Occured", e.getMessage());
			}
		}
        
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, 
			int format,
			int width,
			int height) {
		
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
	    drawThread = new DrawThread(holder, context, this);		
	    drawThread.setRunning(true);
	    drawThread.start();
	}
	
	void doDraw(Canvas canvas) {
		x += speed;
		y += speed;
		Log.d("ASDF", "drawing");
		canvas.drawColor(Color.CYAN);
		canvas.drawBitmap(ball, x, y, null);
	}
}
