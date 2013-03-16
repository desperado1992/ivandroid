package com.example.ejercicio3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class CameraIntent extends Activity implements SurfaceHolder.Callback {
	Camera oCamera;
	SurfaceView surfaceView;
	SurfaceHolder surfaceHolder;
	boolean previewing = false;
	InterestingPointEnum item = null;

	private static final String JPEG_FILE_SUFFIX = ".jpg";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera);

		getWindow().setFormat(PixelFormat.UNKNOWN);
		surfaceView = (SurfaceView) findViewById(R.id.camerapreview);
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			item = (InterestingPointEnum) extras.get("item");
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
		// TODO Auto-generated method stub
		oCamera = Camera.open();

	}
	

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
//		if (previewing) {
//			oCamera.stopPreview();
//			previewing = false;
//		}
//
//		if (oCamera != null) {
//			try {
//				oCamera.setPreviewDisplay(surfaceHolder);
//				oCamera.startPreview();
//				previewing = true;
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
		Camera.Parameters parameters = oCamera.getParameters();
		parameters.setPreviewSize(800, 480); // no ponemos el ancho y el alto si no estos numeros porque si no
		// no funciona, para la versi—n 2.1 aœn no funciona bien. Aunque el telefono tenga otra resoluci—n funciona bien.
        oCamera.setParameters(parameters);
        oCamera.startPreview();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		oCamera.stopPreview();
		oCamera.release();
		oCamera = null;
		previewing = false;

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

			storageDir = new File(Environment.getExternalStorageDirectory()
					.toString() + "/Ejercicio3/" + item.name());

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
