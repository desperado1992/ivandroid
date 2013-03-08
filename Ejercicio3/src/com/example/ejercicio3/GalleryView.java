package com.example.ejercicio3;

import com.example.ejercicio3.ImageAdapters.ImageAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.Toast;

public class GalleryView extends Activity{

	Gallery myGallery;
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.gallery);
		
		Bundle extras = getIntent().getExtras();
		InterestingPointEnum item = null;
		
		if (extras != null){
			item = (InterestingPointEnum) extras.get("item");

			myGallery = (Gallery) findViewById(R.id.gallery);
	
			myGallery.setAdapter(new ImageAdapter(this, item));
	
			myGallery.setOnItemClickListener(new OnItemClickListener() {
		        public void onItemClick(AdapterView parent, View v, int position, long id) {
		            Toast.makeText(GalleryView.this, "" + position, Toast.LENGTH_SHORT).show();
		        }
		        
			});	
		
		}
	}
}
