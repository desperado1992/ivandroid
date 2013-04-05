/*
 *  Copyright (C) 2010 MADSA.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see http://www.gnu.org/licenses/. 
 *
 *  Author:  Jorge Pintado de Santiago
 *          
 */

package com.madsa.AugmentedCompass;

import java.text.DecimalFormat;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ejercicio5.R;
import com.madsa.AugmentedCompass.option.GeneralOptions;

public	class DrawOnTop extends View implements OnTouchListener{
    
	private Context context;
	//Draw variables.
    protected final Paint myArcPaint = new Paint();
    public boolean isDirection = false;
    private float scale_inc = 0.005f;
    private float scale_x, scale_y = 1.0f;
//    private float width, height;   //percent
    private DecimalFormat df = new DecimalFormat("0.00"); 
    private Paint textPaint, pathPaint, buttonPaint;
    private Path arrowPath;
    private RectF arcRectF;
    //Compass variables.
	private float bearing=0;
	private float PrevBearing=180;
	float pitch = 0;
	float roll = 0;
	//Destine info variables.
    private double destineAngle;	
	private String destineDescription;
	private String imageName;
	private String infoTitle;
	//TouchEvent variable
    private long lastTime = 0;  //for touch events too fast.
    private long touchMargin = 750;
    private String debugText = "";
    
	
	public DrawOnTop(Context context, double angle, String destineDescription, String imageName) {
		super(context);
		this.context = context;
		this.destineAngle = angle;
		this.destineDescription = destineDescription;
		this.imageName = imageName;
		
//		
//		this.width = (float) (this.getWidth()/100.0);
//		this.height = (float) (this.getHeight()/100.0);
		
		DecimalFormat df = new DecimalFormat("0.00"); 
		debugText = "" + df.format(destineAngle);
		
		this.arcRectF = new RectF(this.getWidth()/2-100,
				this.getHeight()/2-100,
				this.getWidth()/2+100,
				this.getHeight()/2+100); 		  

		
        this.myArcPaint.setARGB(150, 255, 130, 20); 
        this.myArcPaint.setAntiAlias(true);
        this.myArcPaint.setStyle(Style.STROKE);
        this.myArcPaint.setStrokeWidth(20); 
        
		textPaint = new Paint();
		textPaint.setStyle(Paint.Style.FILL);
		textPaint.setColor(Color.BLACK);
		textPaint.setTextSize(20);

		pathPaint = new Paint();
		pathPaint.setStyle(Paint.Style.FILL);
		pathPaint.setColor(Color.BLACK);
		pathPaint.setTextSize(30);
		pathPaint.setStrokeWidth(2);
		pathPaint.setARGB(200, 255, 0, 0);
		
		buttonPaint = new Paint();
		buttonPaint.setColor(Color.CYAN);
		buttonPaint.setAlpha(50);

		arrowPath = new Path();
		arrowPath.moveTo(this.getWidth()/2, this.getHeight()/2-125);
		arrowPath.lineTo(this.getWidth()/2+25,this.getHeight()/2-110);
		arrowPath.lineTo(this.getWidth()/2, this.getHeight()/2-150);
		arrowPath.lineTo(this.getWidth()/2-25,this.getHeight()/2-110);
		arrowPath.close();

        updateOrientation(new float[] {0, 0, 0});
	}
	
	public void setInfoTitle(String title){
		this.infoTitle = title;
	}
	
	protected void onDraw(Canvas canvas) {
		       		
		//Draw text for destine angle and view angle, only for debug mode.
		canvas.drawText("" + df.format(bearing), 20, this.getHeight()-45, textPaint);
		canvas.drawText("" + debugText, this.getWidth()-110, this.getHeight()-45, textPaint);
		
		//Set color and scale for the central circle.
		if (isDirection){
			this.myArcPaint.setARGB(150, 0, 255, 0);
			canvas.scale(scale_x, scale_y, this.getWidth()/2, this.getHeight()/2);
				scale_x += scale_inc;
				scale_y += scale_inc;
			if (scale_x >= 1.35 || scale_x <= 0.85){
				scale_inc = -scale_inc;
			}
		}else{
			canvas.save();
			this.myArcPaint.setARGB(150, 255, 130, 20); 
			canvas.rotate(-bearing, this.getWidth()/2, this.getHeight()/2);
			
			//Debug mode
			Path path = new Path();
			path.moveTo(this.getWidth()/2, this.getHeight()/2-125);
			path.lineTo(this.getWidth()/2+25,this.getHeight()/2-110);
			path.lineTo(this.getWidth()/2, this.getHeight()/2-150);
			path.lineTo(this.getWidth()/2-25,this.getHeight()/2-110);
			path.close();

			canvas.drawPath(path, pathPaint); 
			canvas.restore();
			
			//restore intial values for scale.
		    scale_inc = 0.005f;
		    scale_x = 1.0f;
			scale_y = 1.0f;
		}
		
		//Debug mode
		float width = (float) (this.getWidth()/100.0); //in percent

		//Draw side buttons.
		//Debug mode, with a number as size.
		canvas.drawCircle(0,this.getHeight(), width*23, buttonPaint);
		canvas.drawCircle(this.getWidth(),this.getHeight(), width*23, buttonPaint);

		//Debug mode. It should be remove in final version.
		this.arcRectF = new RectF(this.getWidth()/2-100,
				this.getHeight()/2-100,
				this.getWidth()/2+100,
				this.getHeight()/2+100); 		  
		
		//Draw central circle.
		canvas.drawArc(arcRectF, -90, 360,
						true, this.myArcPaint);
				
		invalidate();
	} 
	
	public void setBearing(float _bearing) {
		if (Math.abs((_bearing + 90 - destineAngle) - PrevBearing) > 4 && !(Math.abs(_bearing) < 5)){
			bearing = (float) (_bearing + 90 - destineAngle);
			PrevBearing = bearing;
			if (Math.abs(bearing) < 7){
				isDirection = true;
			}else{
				isDirection = false;
			}
			if (bearing < 0){
				bearing += 360;
			}
		}
	}
		
	public float getBearing() {
		return bearing;
	}

	public void setDestineAngle(double angle){
		destineAngle = angle;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
	public float getRoll() {
		return roll;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}

	
    private void updateOrientation(float[] values) {
    	setBearing(values[0]);
    	setPitch(values[1]);
    	setRoll(-values[2]);
    	invalidate();
    }
    
	public boolean onTouchEvent(MotionEvent e) {	
		if(System.currentTimeMillis() - lastTime < touchMargin)
		    return false;
		lastTime = System.currentTimeMillis();
		//If the info button was pressed
		if (e.getX() <= 100 & e.getY() >= 240){
			aboutDialog();	
		}else if(e.getX() >= 200 & e.getY() >= 240){
		    
		    Intent intent;
		    intent = new Intent().setClass(context, GeneralOptions.class);
		    context.startActivity(intent);
		}
			
		return true;
	}
	
	
	private void aboutDialog(){
		
		Dialog d = new Dialog(context);
		Window window = d.getWindow();
		window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		
		//CHANGE: PUT TRY-CATCH BLOCK.
		if (!(infoTitle == null)){
			d.setTitle(infoTitle);	
		}else{
			d.setTitle("Sobre este lugar:");
		}
		d.setContentView(R.layout.ar_dialog);
		TextView text_intro = (TextView) d.findViewById(R.id.information_intro);
		
		text_intro.setText(destineDescription);

		ImageView imgView = (ImageView) d.findViewById(R.id.imageMadridTuristico);
		int resID = getResources().getIdentifier(imageName, "drawable", "com.gtebim.madridturistico");
        imgView.setImageDrawable(getResources().getDrawable(resID));
            
		d.show();
	}

	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}

}