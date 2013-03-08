package com.example.ejercicio3;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class InterestingPointActivity extends Activity implements OnClickListener{

	String sDescription = null;
	String coord = null;

	InterestingPointEnum item = null;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.interestingpoint);
		
		Bundle extras = getIntent().getExtras();
		
		if (extras != null){
			item = (InterestingPointEnum) extras.get("item");
			switch (item) {
				case Puerto:
					sDescription = "Puerto de Altea";
					coord ="38.588292, -0.057013";
					
					
					break;
				case Iglesia:
					sDescription = "Iglesia de Altea";
					coord ="38.598862, -0.051506";
					
					
					break;
				case Plaza:
					sDescription = "Plaza de la Iglesia de Altea";
					coord ="38.598669, -0.051670";
					
					
					break;
				case Ayuntamiento:
					sDescription = "Ayuntamiento de Altea";
					coord ="38.602073, -0.047459";
					
					
					break;
				case Instituto:
					sDescription = "Instituto de Altea";
					coord ="38.604396, -0.045807";
					
					
					break;
	
				default:
					break;
			}
			
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
			InterestingPointEnum item = null;
			
			if (extras != null){
				item = (InterestingPointEnum) extras.get("item");
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
