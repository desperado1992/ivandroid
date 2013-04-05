package com.example.ejercicio4.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class RectView extends View{
	
	public RectView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public RectView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		}
	
	public RectView(Context context, AttributeSet attrs) {
		super(context, attrs);
		}
	


	protected void onDraw(Canvas canvas) {
		Paint oPaint = new Paint();
		int color = Color.RED;
		
		
		super.onDraw(canvas);
		oPaint.setColor(color);
		oPaint.setStrokeWidth(4);
		oPaint.setStyle(Style.STROKE);
		canvas.drawRect(110, 122, 150, 190, oPaint);
		
			
	}
	

}
