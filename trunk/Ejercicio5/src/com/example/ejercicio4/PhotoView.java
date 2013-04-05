package com.example.ejercicio4;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.example.ejercicio5.R;
import com.example.ejercicio4.buttonsActivities.ButtonCircleActivity;
import com.example.ejercicio4.buttonsActivities.ButtonFilledCircleActivity;
import com.example.ejercicio4.buttonsActivities.ButtonFilledRectActivity;
import com.example.ejercicio4.buttonsActivities.ButtonRectActivity;
import com.example.ejercicio4.buttonsActivities.ButtonSurpriseActivity;

public class PhotoView extends Activity {
	private View oCurrentView = null;
	private Bitmap oBitmap = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photoview);

		Bundle extras = getIntent().getExtras();
		String sImagePath = "";

		if (extras != null) {
			sImagePath = (String) extras.get("ImagePath");
			ImageView oImageView = (ImageView)  findViewById(R.id.imageView1);
			oImageView.setImageBitmap(BitmapFactory.decodeFile(sImagePath));
		}
		


		ButtonCircleActivity oButtonCircleActivity = new ButtonCircleActivity();

		Button btnCircle = (Button) findViewById(R.id.btnCircle);
		btnCircle.setOnClickListener(oButtonCircleActivity);

		ButtonFilledCircleActivity oButtonFilledCircleActivity = new ButtonFilledCircleActivity();

		Button btnFilledCircle = (Button) findViewById(R.id.btnFilledCircle);
		btnFilledCircle.setOnClickListener(oButtonFilledCircleActivity);

		ButtonRectActivity oButtonRectActivity = new ButtonRectActivity();

		Button btnRect = (Button) findViewById(R.id.btnRect);
		btnRect.setOnClickListener(oButtonRectActivity);

		ButtonFilledRectActivity oButtonFilledRectActivity = new ButtonFilledRectActivity();

		Button btnFilledRect = (Button) findViewById(R.id.btnFilledRect);
		btnFilledRect.setOnClickListener(oButtonFilledRectActivity);

		ButtonSurpriseActivity oButtonSurpriseActivity = new ButtonSurpriseActivity();

		Button btnSurprise = (Button) findViewById(R.id.btnSurprise);
		btnSurprise.setOnClickListener(oButtonSurpriseActivity);

	}
	
	public ImageView getImageView(){
		ImageView oImageView = (ImageView)  findViewById(R.id.imageView1);
		return oImageView;
	}

	public Bitmap getBitmap() {
		return oBitmap;
	}

	public void setBitmap(Bitmap oBitmap) {
		this.oBitmap = oBitmap;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public View getCurrentView() {
		return oCurrentView;
	}

	public void setCurrentView(View oCurrentView) {
		this.oCurrentView = oCurrentView;
	}
}
