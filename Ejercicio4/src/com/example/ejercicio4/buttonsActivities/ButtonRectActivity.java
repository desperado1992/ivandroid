package com.example.ejercicio4.buttonsActivities;


import com.example.ejercicio4.PhotoView;
import com.example.ejercicio4.R;
import com.example.ejercicio4.views.CircleView;
import com.example.ejercicio4.views.RectView;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class ButtonRectActivity implements OnClickListener {

	@Override
	public void onClick(View v) {
		PhotoView oActivity = (PhotoView) v.getContext();
		RelativeLayout  oRelativeLayout  = (RelativeLayout ) oActivity.findViewById(R.id.rLayout);
		
		
		
		View vCurrentView = oActivity.getCurrentView();
		if(vCurrentView != null){
			oRelativeLayout.removeView(vCurrentView);
			oActivity.setCurrentView(null);
		}
		

		RectView oRectView = new RectView(oActivity);
		oRelativeLayout.addView(oRectView);
		oActivity.setCurrentView(oRectView);
		
	}

}
