package com.example.ejercicio4.views;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Picture;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class CircleView extends View{
	
	public CircleView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public CircleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		}
	
	public CircleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		}
	


	protected void onDraw(Canvas canvas) {
			Paint oPaint = new Paint();			

			int color = Color.YELLOW;
			
			
			super.onDraw(canvas);
			oPaint.setColor(color);
			oPaint.setStrokeWidth(3);
			oPaint.setStyle(Style.STROKE);
			canvas.drawCircle(170, 140, 10, oPaint);
			

			color = Color.BLACK;
			
			
			super.onDraw(canvas);
			oPaint.setColor(color);
			oPaint.setStrokeWidth(3);
			oPaint.setStyle(Style.STROKE);
			canvas.drawCircle(80, 167, 15, oPaint);
			
	}
	

}
