package com.example.ejercicio4.buttonsActivities;

import com.example.ejercicio4.PhotoView;

import com.example.ejercicio5.R;
import com.example.ejercicio4.views.RectView;
import com.example.ejercicio4.views.SurpriseView;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class ButtonSurpriseActivity implements OnClickListener {

	@Override
	public void onClick(View v) {
		PhotoView oActivity = (PhotoView) v.getContext();
		RelativeLayout  oRelativeLayout  = (RelativeLayout ) oActivity.findViewById(R.id.rLayout);
		
		
		View vCurrentView = oActivity.getCurrentView();
		if(vCurrentView != null){
			oRelativeLayout.removeView(vCurrentView);
			oActivity.setCurrentView(null);
		}
		

		SurpriseView oSurpriseView = new SurpriseView(oActivity);
		oRelativeLayout.addView(oSurpriseView);
		oActivity.setCurrentView(oSurpriseView);
		
	}

}
