/*
 *  Copyright (C) 2010 MADSA.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see http://www.gnu.org/licenses/. 
 *
 *  Author:  Jorge Pintado de Santiago
 *          
 */

package com.madsa.AugmentedCompass;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;

public class BrujulaAumentada extends Activity {

	//sensors variables.
    private SensorManager sensorManager;
	private float[] aValues = new float[3];
    private float[] mValues = new float[3];
    //location variables.
    private LocationManager locationManager;
    private String provider;
    //destine variables.
    private double destineAngle;
    private double destineLat, destineLng;
    private double userLat, userLng;
	private String destineDescription;
	private String destineImageName;
    //View variables
	private Preview mPreview;
    private DrawOnTop mOverlay;

    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
            
		//window preferences.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		//Get extra info (parameters)
		Bundle extras  = getIntent().getExtras(); 
		if(extras != null){ 
			String destineLocation = extras.getString("destineLocation");
			destineDescription = extras.getString("destineDescription");
			destineImageName = extras.getString("imageName");
			destineLat = Double.parseDouble(destineLocation.split(",")[0]);
			destineLng = Double.parseDouble(destineLocation.split(",")[1]);
		}else{
			destineDescription = "No hay descripción de esta ubicación.";
			destineImageName = "";
			destineLat = 0.0;
			destineLng = 0.0;
		}

		//initialize sensors
		sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		updateOrientation(new float[] {0, 0, 0});
		
		Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		Sensor magField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		sensorManager.registerListener(sensorEventListener,
										accelerometer,
										SensorManager.SENSOR_DELAY_FASTEST);
		sensorManager.registerListener(sensorEventListener,
									   	magField,
									   	SensorManager.SENSOR_DELAY_FASTEST);
	
		
		//initialize location service.
		//CHANGE: selected in module preferences.
		String context = Context.LOCATION_SERVICE;
		locationManager = (LocationManager)getSystemService(context);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		provider = locationManager.getBestProvider(criteria, true);

		Location location = locationManager.getLastKnownLocation(provider);
		updateWithNewLocation(location);

		locationManager.requestLocationUpdates(provider, 20000, 100,
				locationListener);
		
		//calcule destine angle, to pass it to DrawOnTop class.
		calculateDestineAngle();

		// Create our Preview view and set it as the content of our activity.
		mPreview = new Preview(this);
		mOverlay = new DrawOnTop(this, destineAngle, destineDescription, destineImageName);

		setContentView(mPreview);
        addContentView(mOverlay, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));  
    }
    
	private void updateWithNewLocation(Location location) {
		if (location != null) {		 			
			userLat = location.getLatitude();
			userLng = location.getLongitude();
		} 
		if (!(mOverlay==null)){
			calculateDestineAngle();
			mOverlay.setDestineAngle(destineAngle);			
		}
	}
    
	private final LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			updateWithNewLocation(location);
		}
		public void onProviderDisabled(String provider){
			updateWithNewLocation(null);
		}
		public void onProviderEnabled(String provider){ }
		public void onStatusChanged(String provider, int status,
								Bundle extras){ }
	};
	
    private void calculateDestineAngle(){
    	double x = destineLat - userLat;
    	double y = destineLng - userLng;
    	x*=113.3;
    	y*=111.11;
    	Double ang = Math.atan2(x, y)*180/Math.PI;
    	ang -= 90;  //for landscape mode.
    	ang = -ang;
    	if(ang<0){
    		ang+=360;
    	}
    	destineAngle = ang;
    }
    
    
    private void updateOrientation(float[] values) {
    	if (mOverlay!= null) {
    		mOverlay.setBearing(values[0]);
    		mOverlay.setPitch(values[1]);
    		mOverlay.setRoll(-values[2]);
    		mOverlay.invalidate();
    	  }
    }
    
    private float[] calculateOrientation() {
    	  float[] values = new float[3];
    	  float[] R = new float[9];
    	  SensorManager.getRotationMatrix(R, null, aValues, mValues);
    	  SensorManager.getOrientation(R, values);
    	  // Convert from Radians to Degrees.
    	  values[0] = (float) Math.toDegrees(values[0]);
    	  values[1] = (float) Math.toDegrees(values[1]);
    	  values[2] = (float) Math.toDegrees(values[2]);
    	  return values;
    }
    
    private final SensorEventListener sensorEventListener = new SensorEventListener() {
    	  public void onSensorChanged(SensorEvent event) {
    	    if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
    	      aValues = event.values;
    	    if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
    	      mValues = event.values;
    	    updateOrientation(calculateOrientation());
    	  }
    	  public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    };
    
    protected void onResume(){
    	super.onResume();
		Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		Sensor magField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		sensorManager.registerListener(sensorEventListener,
				accelerometer,
				SensorManager.SENSOR_DELAY_FASTEST);
		sensorManager.registerListener(sensorEventListener,
			   	magField,
			   	SensorManager.SENSOR_DELAY_FASTEST);
		locationManager.requestLocationUpdates(provider, 20000, 100,
				locationListener);
    }
    
    protected void onPause(){
    	super.onPause();
    	sensorManager.unregisterListener(sensorEventListener);
    	locationManager.removeUpdates(locationListener);
    }
    
    protected void onDestroy(){
    	super.onDestroy();    	
    	sensorManager.unregisterListener(sensorEventListener);
    	locationManager.removeUpdates(locationListener);
    }
}