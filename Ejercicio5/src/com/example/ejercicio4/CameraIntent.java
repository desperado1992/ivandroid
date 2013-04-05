package com.example.ejercicio4;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.ejercicio5.R;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class CameraIntent extends Activity implements SurfaceHolder.Callback {
	Camera oCamera;
	SurfaceView oSurfaceView;
	SurfaceHolder oSurfaceHolder;
	boolean bPreviewing = false;
	int item = 0;

	private static final String JPEG_FILE_SUFFIX = ".jpg";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera);

		getWindow().setFormat(PixelFormat.UNKNOWN);
		oSurfaceView = (SurfaceView) findViewById(R.id.camerapreview);
		oSurfaceHolder = oSurfaceView.getHolder();
		oSurfaceHolder.addCallback(this);
		oSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			item = extras.getInt("item");
		}

		// oCamera = Camera.open();
		//
		// Camera.Parameters oParameters = oCamera.getParameters();
		// oParameters.setPictureFormat(PixelFormat.JPEG);
		// oCamera.setParameters(oParameters);

		Button buttonTakePicture = (Button) findViewById(R.id.btnIntend);
		buttonTakePicture.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				oCamera.takePicture(oShutterCallback, rawCallback, jpegCallback);

			}
		});

	}

	
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {		
			oCamera = Camera.open();
	}
	
	

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		if (bPreviewing) {
			oCamera.stopPreview();
			bPreviewing = false;
		}

		if (oCamera != null) {

			try {
				setDisplayOrientation();
				oCamera.setPreviewDisplay(oSurfaceHolder);
				oCamera.startPreview();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bPreviewing = true;
		}
	}
	
	protected void setDisplayOrientation(){
		 if (Integer.parseInt(Build.VERSION.SDK) >= 8)
		        setDisplayOrientation(90);
		    else
		    {
		    	Camera.Parameters oParameters = oCamera.getParameters();
		        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
		        {
		        	oParameters.set("orientation", "portrait");
		        	oParameters.set("rotation", 90);
		        }
		        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
		        {
		        	oParameters.set("orientation", "landscape");
		        	oParameters.set("rotation", 90);
		        }
		        oCamera.setParameters(oParameters);
		    }   
	}
	
	protected void setDisplayOrientation( int angle){
	    Method downPolymorphic;
	    try
	    {
	        downPolymorphic = oCamera.getClass().getMethod("setDisplayOrientation", new Class[] { int.class });
	        if (downPolymorphic != null)
	            downPolymorphic.invoke(oCamera, new Object[] { angle });
	    }
	    catch (Exception e1)
	    {
	    }
	}
	

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		oCamera.stopPreview();
		oCamera.release();
		oCamera = null;
		bPreviewing = false;

	}

	ShutterCallback oShutterCallback = new ShutterCallback() {

		@Override
		public void onShutter() {
			// TODO Auto-generated method stub

		}
	};

	PictureCallback rawCallback = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] arg0, Camera arg1) {
			// TODO Auto-generated method stub

		}
	};

	PictureCallback jpegCallback = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] arg0, Camera arg1) {
			// TODO Auto-generated method stub

			File f;
			String mCurrentPhotoPath;
			try {
				f = createImageFile();
				mCurrentPhotoPath = f.getAbsolutePath();

				Bitmap bitmapPicture = BitmapFactory.decodeByteArray(arg0, 0,
						arg0.length);

				FileOutputStream os = new FileOutputStream(f);
				bitmapPicture.compress(Bitmap.CompressFormat.JPEG, 100, os);
				os.flush();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
				f = null;
				mCurrentPhotoPath = null;
			}

		}
	};
	
	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		String imageFileName = timeStamp + "_";
		File albumF = getAlbumDir();
		File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX,
				albumF);
		return imageF;
	}

	private File getAlbumDir() {
		File storageDir = null;

		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			Resources res = getResources();
			String appName = res.getString(R.string.app_name);
			

			String[] mLocations = getResources().getStringArray(R.array.locations);   
	    	
	    	String destineLocation = mLocations[item];
	    	String sLocation = destineLocation.split(";")[FieldNames.LocationName.getValue()];

			storageDir = new File(Environment.getExternalStorageDirectory()
					.toString() + "/" + appName +"/" + sLocation);
			
			if (storageDir != null) {
				if (!storageDir.mkdirs()) {
					if (!storageDir.exists()) {
						Log.d("CameraSample", "failed to create directory");
						return null;
					}
				}
			}

		} else {
			Log.v(getString(R.string.app_name),
					"External storage is not mounted READ/WRITE.");
		}

		return storageDir;
	}



}
