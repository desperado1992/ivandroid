package com.example.ejercicio2.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class FilledRectView extends View{
	
	public FilledRectView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public FilledRectView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		}
	
	public FilledRectView(Context context, AttributeSet attrs) {
		super(context, attrs);
		}
	


	protected void onDraw(Canvas canvas) {
			Paint oPaint = new Paint();
			int color = Color.RED;
			
			
			super.onDraw(canvas);
			oPaint.setColor(color);
			oPaint.setStyle(Style.FILL);
			canvas.drawRect(110, 122, 150, 190, oPaint);
			
	}
	

}