package com.example.ejercicio2.buttonsActivities;

import com.example.ejercicio2.MainActivity;
import com.example.ejercicio2.R;
import com.example.ejercicio2.views.CircleView;
import com.example.ejercicio2.views.RectView;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class ButtonRectActivity implements OnClickListener {

	@Override
	public void onClick(View v) {
		MainActivity oActivity = (MainActivity) v.getContext();
		RelativeLayout  oRelativeLayout  = (RelativeLayout ) oActivity.findViewById(R.id.rLayout);
		
		oRelativeLayout.setBackgroundResource(R.drawable.empirestate);
		
		
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
