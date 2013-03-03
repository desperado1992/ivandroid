package com.example.ejercicio2.buttonsActivities;

import com.example.ejercicio2.MainActivity;
import com.example.ejercicio2.R;
import com.example.ejercicio2.R.drawable;
import com.example.ejercicio2.R.id;
import com.example.ejercicio2.views.CircleView;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ButtonCircleActivity implements OnClickListener{

	@Override
	public void onClick(View v) {
		MainActivity oActivity = (MainActivity) v.getContext();
		RelativeLayout  oRelativeLayout  = (RelativeLayout ) oActivity.findViewById(R.id.rLayout);
		
		oRelativeLayout.setBackgroundResource(R.drawable.carwallpaper);
		
		
		
		View vCurrentView = oActivity.getCurrentView();
		if(vCurrentView != null){
			oRelativeLayout.removeView(vCurrentView);
			oActivity.setCurrentView(null);
		}
		

		CircleView oCircleView = new CircleView(oActivity);
		oRelativeLayout.addView(oCircleView);
		oActivity.setCurrentView(oCircleView);
		
		
		
	}


	
	

}
