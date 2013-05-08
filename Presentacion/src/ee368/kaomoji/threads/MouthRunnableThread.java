package ee368.kaomoji.threads;

import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;

import ee368.kaomoji.FeatureDetector;
import ee368.kaomoji.TrackedFeature;

public class MouthRunnableThread  implements  Runnable{

	Mat mGray;
	Rect faceRect;
	Point delta;
	private FeatureDetector mouthDetector;
	private TrackedFeature mouth;
	
	private Rect mouthRect;	

	
	
	public MouthRunnableThread(Mat mGray, Rect faceRect, Point delta,
			FeatureDetector mouthDetector, TrackedFeature mouth) {
		super();
		this.mGray = mGray;
		this.faceRect = faceRect;
		this.delta = delta;
		this.mouthDetector = mouthDetector;
		this.mouth = mouth;
	}

	public void run() {
		// Detect mouth.
		///*
		/*Rect mouthROI = new Rect(
		    faceRect.x, (int)(faceRect.y + (faceRect.height / 1.55)),
		    faceRect.width, (int)(faceRect.height / 3.0));*/
		

		Rect mouthROI = new Rect(
		    faceRect.x, (int)(faceRect.y + (faceRect.height / 2)),
		    faceRect.width, (int)(faceRect.height / 2));
		
		//Rect mouthROI = faceRect;
		
		//drawRect(mRgba, mouthROI, FACE_COLOR);
		
		List<Rect> mouths = mouthDetector.detect(mGray, mouthROI);
		
		mouthRect = mouth.update(
		    mouths.size() > 0 ? mouths.get(0) : null, delta);

		/*mouthRect.x += (faceRect.x + faceRect.width / 2) -
		    (mouthRect.x + mouthRect.width / 2);*/
		/*fix = (int)(mouthRect.width / 12.0);
		mouthRect.x -= fix;
		mouthRect.width += fix * 2;*/
		//drawRect(mRgba, mouthRect, MOUTH_COLOR);
		//*/
		
	}

	public Rect getMouthRect() {
		return mouthRect;
	}
	

}
