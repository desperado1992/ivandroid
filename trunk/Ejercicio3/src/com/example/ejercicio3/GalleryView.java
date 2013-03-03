package com.example.ejercicio3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;

public class GalleryView extends Activity{

	Gallery myGallery;
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.gallery);
		
		Bundle extras = getIntent().getExtras();
		InterestingPointEnum item = null;
		
		if (extras != null){
			item = (InterestingPointEnum) extras.get("item");
			switch (item) {
				case Puerto:
					
					
					break;
				case Iglesia:
					
					
					break;
				case Plaza:
					
					
					break;
				case Ayuntamiento:
					
					
					break;
				case Instituto:
					
					
					break;
	
				default:
					break;
			}
		}

		myGallery = (Gallery) findViewById(R.id.gallery);

		
		
	}
}
