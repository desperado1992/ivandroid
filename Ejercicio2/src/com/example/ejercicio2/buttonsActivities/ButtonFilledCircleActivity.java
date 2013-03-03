package com.example.ejercicio2.buttonsActivities;

import com.example.ejercicio2.MainActivity;
import com.example.ejercicio2.R;
import com.example.ejercicio2.views.CircleView;
import com.example.ejercicio2.views.FilledCircleView;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class ButtonFilledCircleActivity implements OnClickListener {

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
		

		FilledCircleView oFilledCircleView = new FilledCircleView(oActivity);
		oRelativeLayout.addView(oFilledCircleView);
		oActivity.setCurrentView(oFilledCircleView);
		
	}

}
