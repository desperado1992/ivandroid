package com.example.ejercicio4;


import com.example.ejercicio5.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class InterestingPointActivity extends Activity implements OnClickListener{

	String sDescription = null;
	String coord = null;

	int item = 0;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.interestingpoint);
		
		Bundle extras = getIntent().getExtras();
		
		if (extras != null){
			item = extras.getInt("item");
			String[] mLocations = getResources().getStringArray(R.array.locations);   
	    	
	    	String destineLocation = mLocations[item];
	    	coord = destineLocation.split(";")[FieldNames.LocationPosition.getValue()];		    

	    	String[] descLocations = getResources().getStringArray(R.array.descriptions);
		    sDescription = descLocations[item];
			
			
		}
		
		buttonsEvents();
		
	}


	private void buttonsEvents() {
		Button btnDescription = (Button) findViewById(R.id.btnDescription);
		btnDescription.setOnClickListener(this);

		Button btnMap = (Button) findViewById(R.id.btnMap);
		btnMap.setOnClickListener(this);

		Button btnPhotos = (Button) findViewById(R.id.btnPhotos);
		btnPhotos.setOnClickListener(this);

		Button btnTakePhoto = (Button) findViewById(R.id.btnTakePhoto);
		btnTakePhoto.setOnClickListener(this);


		TextView txtDescription = (TextView) findViewById(R.id.txtDescription);
		txtDescription.setBackgroundColor(Color.WHITE);
	}


	@Override
	public void onClick(View v) {
	    Button btn = (Button)v;
	    String buttonText = btn.getText().toString();
	    
	    if(buttonText.contains("Description")){

			TextView txtDescription = (TextView) findViewById(R.id.txtDescription);
			txtDescription.setText(sDescription);

	    }
	    else if(buttonText.contains("Map")){
	    	Intent i_map = new Intent(InterestingPointActivity.this, Map.class);
			i_map.putExtra("tag_coordenadas", coord);
			startActivity(i_map);
	    }
	    else if(buttonText.contains("Photos")){
	    	Bundle extras = getIntent().getExtras();
			item = 0;
			
			if (extras != null){
				item = extras.getInt("item");
				Intent iGallery = new Intent(InterestingPointActivity.this, GalleryView.class);
				iGallery.putExtra("item", item);
				startActivity(iGallery);
			}
	    }
	    else if(buttonText.contains("Take")){
	    	Intent oCameraIntent = new Intent(InterestingPointActivity.this, CameraIntent.class);
	    	oCameraIntent.putExtra("item", item);
			startActivity(oCameraIntent);
	    }
	}

}
