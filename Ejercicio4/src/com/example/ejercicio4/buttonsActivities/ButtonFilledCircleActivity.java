package com.example.ejercicio4.buttonsActivities;

import com.example.ejercicio4.PhotoView;
import com.example.ejercicio4.R;
import com.example.ejercicio4.views.CircleView;
import com.example.ejercicio4.views.FilledCircleView;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class ButtonFilledCircleActivity implements OnClickListener {

	@Override
	public void onClick(View v) {
		PhotoView oActivity = (PhotoView) v.getContext();
		RelativeLayout  oRelativeLayout  = (RelativeLayout ) oActivity.findViewById(R.id.rLayout);
		
		
		
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
