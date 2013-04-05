package com.example.ejercicio4.ImageAdapters;

import java.io.File;
import java.util.ArrayList;

import com.example.ejercicio4.CameraIntent;
import com.example.ejercicio4.FieldNames;

import com.example.ejercicio5.R;
import com.example.ejercicio5.R.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    

    File folder;
    String[] allFiles;

    public ImageAdapter(Context c, int item) {
        mContext = c;    
        
        String[] mLocations = mContext.getResources().getStringArray(R.array.locations);   
    	
    	String destineLocation = mLocations[item];
    	String sLocation = destineLocation.split(";")[FieldNames.LocationName.getValue()];	

		Resources res = mContext.getResources();
		String appName = res.getString(R.string.app_name);

		folder = new File(Environment.getExternalStorageDirectory()
                .toString() + "/" + appName +"/" + sLocation);
        allFiles = folder.list();
        if(allFiles == null){
        	allFiles = new String[0];
        }
    }

    public int getCount() {
        return allFiles.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView oImageView = new ImageView(mContext);

        //i.setImageResource(mImageIds.get(position));
        oImageView.setLayoutParams(new Gallery.LayoutParams(150, 100));
        oImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        
        Bitmap bitmapImage = BitmapFactory.decodeFile(folder + "/"
                + allFiles[position]);
        BitmapDrawable drawableImage = new BitmapDrawable(bitmapImage);
        oImageView.setImageDrawable(drawableImage);
               

        return oImageView;
    }
}
