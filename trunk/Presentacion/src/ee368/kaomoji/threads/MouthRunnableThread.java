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

public class MouthRunnableThread  implements  Runnable{

	Mat mGray;
	Rect faceRect;
	Point delta;
	private FeatureDetector mouthDetector;
	private TrackedFeature mouth;
	
	private Rect mouthRect;	
	
	boolean bDetected;
	private Mat mRgba;
	private CamShifting cs;
	
	private static final Scalar MOUTH_COLOR = new Scalar(255, 200, 0, 255);

	
	
	public MouthRunnableThread(Mat mRgba, Mat mGray, Rect faceRect, Point delta,
			FeatureDetector mouthDetector, TrackedFeature mouth, boolean bDetected, Rect mouthRect, CamShifting csMouth) {
		super();
		this.mRgba = mRgba;
		this.mGray = mGray;
		this.faceRect = faceRect;
		this.delta = delta;
		this.mouthDetector = mouthDetector;
		this.mouth = mouth;
		this.bDetected = bDetected;
		this.mouthRect = mouthRect;
		this.cs = csMouth;
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
		
		List<Rect> mouths = new ArrayList<Rect>();
		if (!bDetected) {
			// for (int i = 0; i < facearray1.length; i++)
			// Core.rectangle(mRgba, facearray1[i].tl(), facearray1[i].br(),
			// FACE_COLOR, 3);
			Log.i("FdView", "Calling create tracked object");

			if (mouthDetector != null) {
				mouths = mouthDetector.detect(mGray, mouthROI);
				if (mouths.size() > 0) {
					for (int i = 0; i < mouths.size(); i++)
						Core.rectangle(mRgba, mouths.get(i).tl(), mouths.get(i).br(), MOUTH_COLOR, 3);

					cs.create_tracked_object(mRgba, mouths, cs);
				}
			}

		} else {
			// track the face in the new frame
			RotatedRect face_box = cs.camshift_track_face(mRgba, Arrays.asList(mouthRect), cs);
			Core.ellipse(mRgba, face_box, MOUTH_COLOR, 6);

			if (face_box != null) {

				mouths = Arrays.asList(face_box.boundingRect());
			}

		}

		
		//Rect mouthROI = faceRect;
		
		//drawRect(mRgba, mouthROI, FACE_COLOR);
		
		//List<Rect> mouths = mouthDetector.detect(mGray, mouthROI);
		
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
