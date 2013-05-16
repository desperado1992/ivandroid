package ee368.kaomoji.threads;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;

import android.util.Log;

import ee368.kaomoji.FeatureDetector;
import ee368.kaomoji.tracking.CamShifting;
import ee368.kaomoji.tracking.TrackedFeature;

public class EyeRunnableThread  implements  Runnable{
	Mat mGray;
	Rect faceRect;
	Point delta;
	private FeatureDetector eyeDetector;
	private TrackedFeature eye;	
	Rect eyeROI;	

	boolean bDetected;
	private Mat mRgba;
	private CamShifting cs;
	
	private static final Scalar EYE_COLOR = new Scalar(255, 0, 0, 255);

	public EyeRunnableThread(Mat mRgba, Mat mGray, Rect faceRect, Point delta,
			FeatureDetector eyeDetector, TrackedFeature eye, Rect eyeROI, boolean bDetected, Rect eyeRect, CamShifting csRect) {
		super();
		this.mRgba = mRgba;
		this.mGray = mGray;
		this.faceRect = faceRect;
		this.delta = delta;
		this.eyeDetector = eyeDetector;
		this.eye = eye;
		this.eyeROI = eyeROI;
		this.bDetected = bDetected;
		this.eyeRect = eyeRect;
		this.cs = csRect;
	}


	private Rect eyeRect;	

	public void run() { 
			// Detect eyes.
	    /*Rect eyeROI = new Rect(
	        faceRect.x, (int)(faceRect.y + (faceRect.height / 5.5)),
	        faceRect.width, (int)(faceRect.height / 3.0));*/
	
	    
	    /*Rect eyeROI = new Rect(
	    faceRect.x, faceRect.y,
	    faceRect.width, (int)(faceRect.height / 2));*/  
	


		//Rect eyeROI = faceRect;
		List<Rect> eyes = new ArrayList<Rect>();


		Log.i("EyeRunnableThread", "detect");
		eyes = getEyeList(mRgba, mGray, eyeROI);

		Log.i("EyeRunnableThread", "Finish detect");


		eyeRect = eyes.size() > 0 ? eyes.get(0) : null;


		Log.i("EyeRunnableThread", "End run");
		/*eyeRect = eye.update(eyeRect, delta);*/
	    
	    
	    /*int fix = (int)(eyeRect.width / 5.0);
	    if(eyeRect.x > fix){
	    	eyeRect.x -= fix;
	    	eyeRect.width += fix;
	    }*/
	    
			
	}
	
	private List<Rect> getEyeList(Mat mRgba, Mat mGray, Rect mouthROI) {
		List<Rect> eyes =new ArrayList<Rect>();
			if (!bDetected) {
				// for (int i = 0; i < facearray1.length; i++)

				if (eyeDetector != null) {
					eyes = eyeDetector.detect(mGray, mouthROI);
					if (eyes.size() > 0) {

						 /*Core.rectangle(mRgba, eyes.get(0).tl(), eyes.get(0).br(),
								 EYE_COLOR, 3);*/
						Log.i("EyeRunnableThread", "Calling create tracked object");
						cs.create_tracked_object(mRgba, eyes, Arrays.asList(mouthROI), cs);
					}
				}

			} else {
				// track the face in the new frame
				RotatedRect face_box = cs.camshift_track(mRgba, Arrays.asList(eyeRect), Arrays.asList(mouthROI), cs);
				//Core.ellipse(mRgba, face_box, MOUTH_COLOR, 6);
				
				if (face_box != null && face_box.center.x > 0 && face_box.center.y > 0 
						&& face_box.size.width > 1 && face_box.size.height > 1) {
					Log.i("EyeRunnableThread", "Calling tracke face");
					eyes = Arrays.asList(face_box.boundingRect());

					eyes.get(0).x += mouthROI.x;
					eyes.get(0).y += mouthROI.y;
					
					if(eyes.get(0).width < eyes.get(0).height){
						eyes.get(0).y += eyes.get(0).height /2;
						eyes.get(0).height = eyes.get(0).width;
						eyes.get(0).y -= eyes.get(0).height /2;
					}
					else{
						eyes.get(0).x += eyes.get(0).width /2;
						eyes.get(0).width = eyes.get(0).height;
						eyes.get(0).x -= eyes.get(0).width /2;
					}

					
					
					RotatedRect rrEllipse = face_box;
					rrEllipse.center.x += mouthROI.x;
					rrEllipse.center.y += mouthROI.y;
					//Core.ellipse(mRgba, face_box, EYE_COLOR, 6);
					//Core.rectangle(mRgba, eyes.get(0).tl(), eyes.get(0).br(), EYE_COLOR, 3);

				}
				else{
					eyes = new ArrayList<Rect>();
				}

				bDetected = false;

			}
			
			return eyes;

		}

	public Rect getEyeRect() {
		return eyeRect;
	}
	
	public boolean getDetected() {
		return bDetected;
	}

}
