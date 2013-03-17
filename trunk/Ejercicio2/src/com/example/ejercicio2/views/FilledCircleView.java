package com.example.ejercicio2.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class FilledCircleView extends View{
	
	public FilledCircleView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public FilledCircleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		}
	
	public FilledCircleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		}
	


	protected void onDraw(Canvas canvas) {
			Paint oPaint = new Paint();	

			int color = Color.YELLOW;			
			
			super.onDraw(canvas);
			oPaint.setColor(color);
			oPaint.setStyle(Style.FILL);
			canvas.drawCircle(170, 140, 10, oPaint);
			

			color = Color.BLACK;			
			
			super.onDraw(canvas);
			oPaint.setColor(color);
			oPaint.setStyle(Style.FILL);
			canvas.drawCircle(80, 167, 15, oPaint);
	}
	

}
