package ee368.kaomoji.threads;

import java.util.List;


import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;

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
	


	public EyeRunnableThread(Mat mGray, Rect faceRect, Point delta,
			FeatureDetector eyeDetector, TrackedFeature eye, Rect eyeROI) {
		super();
		this.mGray = mGray;
		this.faceRect = faceRect;
		this.delta = delta;
		this.eyeDetector = eyeDetector;
		this.eye = eye;
		this.eyeROI = eyeROI;
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
	    List<Rect> eyes = eyeDetector.detect(mGray, eyeROI);
	    
	
		eyeRect = eyes.size() > 0 ? eyes.get(0) : null;
	    
		eyeRect = eye.update(eyeRect, delta);
	    
	    
	    /*int fix = (int)(eyeRect.width / 5.0);
	    if(eyeRect.x > fix){
	    	eyeRect.x -= fix;
	    	eyeRect.width += fix;
	    }*/
	    
			
	}

	public Rect getEyeRect() {
		return eyeRect;
	}

}
