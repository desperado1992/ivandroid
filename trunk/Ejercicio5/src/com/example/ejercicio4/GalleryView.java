package com.example.ejercicio4;

import java.io.File;

import com.example.ejercicio4.ImageAdapters.ImageAdapter;
import com.example.ejercicio5.R;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.Toast;

public class GalleryView extends Activity{

	Gallery myGallery;

    File folder;
    String[] allFiles;
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);       
		setContentView(R.layout.gallery);
		
		Bundle extras = getIntent().getExtras();
		int item = 0;
		
		if (extras != null){
			item = extras.getInt("item");			
			String[] mLocations = getResources().getStringArray(R.array.locations);   
	    	
	    	String destineLocation = mLocations[item];
	    	String sLocation = destineLocation.split(";")[FieldNames.LocationName.getValue()];	

			Resources res = getResources();
			String appName = res.getString(R.string.app_name);

			folder = new File(Environment.getExternalStorageDirectory()
	                .toString() + "/" + appName +"/" + sLocation);
	        allFiles = folder.list();
	        if(allFiles == null){
	        	allFiles = new String[0];
	        }

			myGallery = (Gallery) findViewById(R.id.gallery);
	
			myGallery.setAdapter(new ImageAdapter(this, item));
	
			myGallery.setOnItemClickListener(new OnItemClickListener() {
		        public void onItemClick(AdapterView parent, View v, int position, long id) {
		        	String sImagePath = folder + "/" + allFiles[position];
		        	Intent oPhotoView = new Intent(GalleryView.this, PhotoView.class);
		        	oPhotoView.putExtra("ImagePath", sImagePath);
					startActivity(oPhotoView);
		        }
		        
			});	
		
		}
	}
}
