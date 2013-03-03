package com.example.ejercicio1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View {
	public DrawView(Context context){
		super(context);
	}
	public DrawView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	public DrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	Paint pincel = new Paint();
	float x = -1;
	float y = -1;
	
	@Override
	protected void onDraw(Canvas canvas) {
	super.onDraw(canvas);
	if(0 < x && x < canvas.getWidth() && 0 < y && y < canvas.getHeight()){
		pincel.setColor(Color.BLUE);
		pincel.setStrokeWidth(8);
		pincel.setStyle(Style.STROKE);
		canvas.drawRect(x-25, y-25, x+25, y+25, pincel);
	}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	x = event.getX();
	y = event.getY();
	//Fuerzo el repintado
	this.invalidate();
	return super.onTouchEvent(event);
	}
}
