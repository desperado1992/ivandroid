package com.example.ejercicio2.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

public class SurpriseView extends View{
	
	public SurpriseView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public SurpriseView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		}
	
	public SurpriseView(Context context, AttributeSet attrs) {
		super(context, attrs);
		}
	


	protected void onDraw(Canvas canvas) {
			Paint paint = new Paint();

		    paint.setColor(Color.GREEN);    

		    Point point1_draw = new Point(130, 40);        
		    Point point2_draw = new Point(110, 165);    
		    Point point3_draw = new Point(150, 165);


		    Path path = new Path();
		    path.reset();

		    paint.setStyle(Paint.Style.FILL);
		    path.moveTo(point1_draw.x, point1_draw.y);
		    path.lineTo(point2_draw.x, point2_draw.y);
		    path.lineTo(point3_draw.x, point3_draw.y);
		    path.lineTo(point1_draw.x, point1_draw.y);
		    path.close();

		    canvas.drawPath(path, paint);
		
			
	}
	

}