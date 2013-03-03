package com.example.ejercicio2;


import com.example.ejercicio2.buttonsActivities.ButtonCircleActivity;
import com.example.ejercicio2.buttonsActivities.ButtonFilledCircleActivity;
import com.example.ejercicio2.buttonsActivities.ButtonFilledRectActivity;
import com.example.ejercicio2.buttonsActivities.ButtonRectActivity;
import com.example.ejercicio2.buttonsActivities.ButtonSurpriseActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
	private View oCurrentView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
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
