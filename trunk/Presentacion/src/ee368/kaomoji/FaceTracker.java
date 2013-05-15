package ee368.kaomoji;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
//import org.opencv.core.MatOfFloat;
//import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import ee368.kaomoji.threads.EyeRunnableThread;
import ee368.kaomoji.threads.MouthRunnableThread;
import ee368.kaomoji.tracking.CamShifting;
import ee368.kaomoji.tracking.TrackedFeature;

import android.content.Context;
import android.util.Log;

public class FaceTracker {

	private static final String TAG = "Kaomoji::FaceTracker";

	private static final Scalar FACE_COLOR = new Scalar(0, 255, 0, 255);
	private static final Scalar EYE_COLOR = new Scalar(255, 0, 0, 255);
	private static final Scalar MOUTH_COLOR = new Scalar(255, 200, 0, 255);

	private FeatureDetector faceDetector;
	private FeatureDetector eyeDetector;
	private FeatureDetector mouthDetector;

	private TrackedFeature face = new TrackedFeature();
	private TrackedFeature leftEye = new TrackedFeature();
	private TrackedFeature rightEye = new TrackedFeature();
	private TrackedFeature mouth = new TrackedFeature();

	private Mat hist;
	private Mat mask;

	public boolean faceDetected;
	public boolean mouthDetected;
	public boolean leftEyeDetected;
	public boolean rightEyeDetected;

	CamShifting cs;
	CamShifting csMouth;
	CamShifting csLeftEye;
	CamShifting csRightEye;
	
	List<Rect> faces;
	Rect mouthRect;
	Rect leftEyeRect;
	Rect rightEyeRect;

	private Context context;

	public static ArrayList<Kaomoji> kaomojiList = new ArrayList<Kaomoji>();

	public FaceTracker(Context context) {
		
		this.context = context;
		faceDetector = FeatureDetector.getFaceDetector(context);
		eyeDetector = FeatureDetector.getEyeDetector(context);
		mouthDetector = FeatureDetector.getMouthDetector(context);

		kaomojiList.add(new Kaomoji("\u2267\u25BD\u2266", Utils.exportResource(
				context, R.raw.kaomoji1)));
		kaomojiList.add(new Kaomoji("\uFF1E\u03C9\uFF1C", Utils.exportResource(
				context, R.raw.kaomoji2)));
		kaomojiList.add(new Kaomoji("\uFFE3\uFF5E\uFFE3", Utils.exportResource(
				context, R.raw.kaomoji3)));
		kaomojiList.add(new Kaomoji("\u2190_\u2190", Utils.exportResource(
				context, R.raw.kaomoji4)));
		kaomojiList.add(new Kaomoji("\u256F\uFF3E\u2570", Utils.exportResource(
				context, R.raw.kaomoji5)));

		hist = new Mat();
		mask = new Mat();

		faceDetected = false;
		mouthDetected = false;
		leftEyeDetected = false;
		rightEyeDetected = false;

		cs = new CamShifting();
		csMouth = new CamShifting();
		csLeftEye = new CamShifting();
		csRightEye = new CamShifting();
		
		faces = new ArrayList<Rect>();
		

	}

	public void processFrame(Mat mRgba, Mat mGray) {
		if (Options.activeKaomoji < 0) {
			return;
		}

		getFacesList(mRgba, mGray);

		
		if(faces.size() == 0){
			face.update(null);
			Log.i("FaceTracker", "faces.size() == 0");
		}

		for (Rect faceRect : faces) {
			Log.i("FaceTracker", "faces.size() > 0");
			Point delta = face.getDelta();
			
			

			MouthRunnableThread rMouthThread = new MouthRunnableThread(mRgba, mGray,
					faceRect, delta, mouthDetector, mouth, mouthDetected, mouthRect, csMouth);
			Thread oMouthThread = new Thread(rMouthThread);
			

			Rect leftEyeROI = new Rect(faceRect.x, faceRect.y
					+ (int) (faceRect.height / 5),
					(int) (faceRect.width * 2 / 3),
					(int) (faceRect.height * 2 / 5));

			EyeRunnableThread rLeftEyeThread = new EyeRunnableThread( mGray,
					faceRect, delta, eyeDetector, leftEye, leftEyeROI);
			Thread oLeftEyeThread = new Thread(rLeftEyeThread);

			Rect rightEyeROI = new Rect(
					faceRect.x + (int) (faceRect.width / 3), faceRect.y
							+ (int) (faceRect.height / 5),
					(int) (faceRect.width * 2 / 3),
					(int) (faceRect.height * 2 / 5));

			EyeRunnableThread rRightEyeThread = new EyeRunnableThread(mGray,
					faceRect, delta, eyeDetector, rightEye, rightEyeROI);
			Thread oRightEyeThread = new Thread(rRightEyeThread);

			boolean bmultiThreading = context.getResources().getBoolean(R.bool.multiThreading);
			if(bmultiThreading){
				List<Thread> lThreads = new ArrayList<Thread>();
				lThreads.add(oMouthThread);
				lThreads.add(oLeftEyeThread);
				lThreads.add(oRightEyeThread);
			
				//Multithreading
				for (Thread oThread : lThreads) {
					oThread.start();
					//oThread.join();
				}
				Log.i("FaceTracker", "started");
	
				for (Thread oThread : lThreads) {
					try {
						oThread.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
	
						Log.e("FaceTracker", e.getMessage());
					}
	
				}
				
				
				Log.i("FaceTracker", "joined");
			}
			else{			
				rMouthThread.run();
				rLeftEyeThread.run();
				rRightEyeThread.run();
			}
			


			mouthRect = rMouthThread.getMouthRect();
			if (mouthRect == null) {

				Log.i("FaceTracker", "NO mouth Detected");
				mouthDetected = false;
				return;

			}
			else{

				Log.i("FaceTracker", "mouth Detected");
				mouthDetected = true;
			}


			leftEyeRect = rLeftEyeThread.getEyeRect();
			rightEyeRect = rRightEyeThread.getEyeRect();
			if (leftEyeRect == null || rightEyeRect == null) {
				leftEyeDetected = false;
				rightEyeDetected = false;

				Log.i("FaceTracker", "NO Eyes Detected");
				return;
			}
			else{
				if(leftEyeRect != null){

					Log.i("FaceTracker", "leftEye Detected");
					leftEyeDetected = true;			
				}
				if(rightEyeRect != null){
					Log.i("FaceTracker", "rightEye Detected");
					rightEyeDetected = true;
				}
			}

			// setSkinColor(mRgba, mouthRect, leftEyeRect, rightEyeRect);

			Kaomoji selected = kaomojiList.get(Options.activeKaomoji);
			selected.apply(mRgba, leftEyeRect, rightEyeRect, mouthRect);


			face.update(faceRect);
			faceDetected = true;

			Log.i("FaceTracker", "Face Detected");
		}

	}

	private void getFacesList(Mat mRgba, Mat mGray) {
		if (!faceDetected) {
			// for (int i = 0; i < facearray1.length; i++)
			// Core.rectangle(mRgba, facearray1[i].tl(), facearray1[i].br(),
			// FACE_COLOR, 3);

			if (faceDetector != null) {
				faces = faceDetector.detect(mGray);
				if (faces.size() > 0) {
					for (int i = 0; i < faces.size(); i++)
						/*Core.rectangle(mRgba, faces.get(i).tl(), faces.get(i)
								.br(), FACE_COLOR, 3);*/

					Log.i("FaceTracker", "Calling create tracked object");
					cs.create_tracked_object(mRgba, faces, cs);
				}
			}

		} else {
			// track the face in the new frame

			RotatedRect face_box = cs.camshift_track_face(mRgba, faces, cs);
			//Core.ellipse(mRgba, face_box, FACE_COLOR, 6);
			//Core.rectangle(mRgba, face_box.boundingRect().tl(), face_box.boundingRect().br(), FACE_COLOR, 3);

			if (face_box != null && face_box.center.x > 0 && face_box.center.y > 0 
					&& face_box.size.width > 10 && face_box.size.height > 10) {
				Log.i("FaceTracker", "Calling tracke face");
				faces = Arrays.asList(face_box.boundingRect());
			}

			faceDetected = false;

		}

	}

	private void setSkinColor(Mat mRgba, Rect mouthRect, Rect leftEyeRect,
			Rect rightEyeRect) {
		// Replace features with Kaomoji if selected.
		Rect sample;
		sample = new Rect(leftEyeRect.x + leftEyeRect.width,
				leftEyeRect.y + 15, rightEyeRect.x - leftEyeRect.x
						- leftEyeRect.width, leftEyeRect.height - 30);
		double[] skinColor = getSkinColor(mRgba, sample);

		if (skinColor[0] < 50) {
			Log.e(TAG, "Failed to get skin color!");
			return;
		}
		setToSkinColor(mRgba, leftEyeRect, skinColor, skinColor, 30, 75);
		setToSkinColor(mRgba, rightEyeRect, skinColor, skinColor, 30, 75);

		sample = mouthRect.clone();
		sample.y += mouthRect.height;
		sample.height /= 4;
		skinColor = getSkinColor(mRgba, sample);

		sample.y = mouthRect.y - mouthRect.height / 4;
		double[] skinColor2 = getSkinColor(mRgba, sample);
		for (int i = 0; i < 3; i++) {
			skinColor[i] = (skinColor[i] + skinColor2[i]) / 2;
		}
		setToSkinColor(mRgba, mouthRect, skinColor, skinColor, 20, 60);
	}

	private Rect detectEye(Mat mGray, Rect faceRect, Point delta, Rect eyeROI,
			TrackedFeature oTrackedFeature) {
		// Detect eyes.
		/*
		 * Rect eyeROI = new Rect( faceRect.x, (int)(faceRect.y +
		 * (faceRect.height / 5.5)), faceRect.width, (int)(faceRect.height /
		 * 3.0));
		 */

		/*
		 * Rect eyeROI = new Rect( faceRect.x, faceRect.y, faceRect.width,
		 * (int)(faceRect.height / 2));
		 */

		// Rect eyeROI = faceRect;
		List<Rect> eyes = eyeDetector.detect(mGray, eyeROI);

		Rect eyeRect = eyes.size() > 0 ? eyes.get(0) : null;

		eyeRect = oTrackedFeature.update(eyeRect, delta);

		if (eyeRect == null) {
			return null;
		}

		/*
		 * int fix = (int)(eyeRect.width / 5.0); if(eyeRect.x > fix){ eyeRect.x
		 * -= fix; eyeRect.width += fix; }
		 */

		return eyeRect;

	}

	private Rect detectMouth(Mat mGray, Rect faceRect, Point delta) {
		// Detect mouth.
		// /*
		/*
		 * Rect mouthROI = new Rect( faceRect.x, (int)(faceRect.y +
		 * (faceRect.height / 1.55)), faceRect.width, (int)(faceRect.height /
		 * 3.0));
		 */

		Rect mouthROI = new Rect(faceRect.x,
				(int) (faceRect.y + (faceRect.height / 2)), faceRect.width,
				(int) (faceRect.height / 2));

		// Rect mouthROI = faceRect;

		// drawRect(mRgba, mouthROI, FACE_COLOR);

		List<Rect> mouths = mouthDetector.detect(mGray, mouthROI);

		Rect mouthRect = mouth.update(mouths.size() > 0 ? mouths.get(0) : null,
				delta);
		if (mouthRect == null) {
			return null;
		}
		/*
		 * mouthRect.x += (faceRect.x + faceRect.width / 2) - (mouthRect.x +
		 * mouthRect.width / 2);
		 */
		/*
		 * fix = (int)(mouthRect.width / 12.0); mouthRect.x -= fix;
		 * mouthRect.width += fix * 2;
		 */
		// drawRect(mRgba, mouthRect, MOUTH_COLOR);
		// */
		return mouthRect;
	}

	private double[] getSkinColor(Mat mRgba, Rect sample) {
		double[] skinValues = { 0, 0, 0, 255 };
		for (int y = sample.y; y < sample.y + sample.height; y++) {
			for (int x = sample.x; x < sample.x + sample.width; x++) {
				double[] v = mRgba.get(y, x);
				if (v.length >= 3) {
					for (int c = 0; c < 3; c++) {
						skinValues[c] += v[c];
					}
				}
			}
		}
		for (int c = 0; c < 3; c++) {
			skinValues[c] /= sample.width * sample.height;
		}
		// drawRect(mRgba, sample, new Scalar(0, 0, 255, 255));
		return skinValues;
	}

	public void setToSkinColor(Mat image, Rect rect, double[] skinColor1,
			double[] skinColor2, double t, double maxD) {
		if (rect.width <= 0 || rect.height <= 0 || rect.x < 0 || rect.y < 0) {
			return;
		}
		double threshold = 0.75;
		Point center = Util.getCenter(rect);

		for (int y = (int) center.y - 1; y >= rect.y; y--) {
			if (setToSkinColorOnRow(image, rect, y, skinColor1, t, maxD) > threshold) {
				break;
			}
		}
		for (int y = (int) center.y; y < rect.y + rect.height; y++) {
			if (setToSkinColorOnRow(image, rect, y, skinColor2, t, maxD) > threshold) {
				break;
			}
		}

		Rect toBlur = rect.clone();
		int n = 13;
		toBlur.x -= n;
		toBlur.y -= n;
		toBlur.width += 2 * n;
		toBlur.height += 2 * n;
		if (toBlur.width <= 0 || toBlur.height <= 0 || toBlur.x < 0
				|| toBlur.y < 0) {
			return;
		}
		// Log.w(TAG, toBlur.toString());
		Mat roi = image.submat(toBlur);
		Imgproc.GaussianBlur(roi, roi, new Size(n, n), 1.5);
	}

	private double setToSkinColorOnRow(Mat mRgba, Rect rect, int y,
			double[] skinValue, double t, double maxD) {
		double skinProb = 0;
		for (int x = rect.x; x < rect.x + rect.width; x++) {
			double[] v = mRgba.get(y, x);
			double d = Util.maxDist(v, skinValue);
			Log.w(TAG, "~ skin value ? " + d);
			if (d < t) {
				skinProb += 1;
			} else {
				double s = Math.min(1.0, d / maxD);
				for (int i = 0; i < 3; i++) {
					v[i] = v[i] * (1 - s) + skinValue[i] * s;
				}
				mRgba.put(y, x, v);
			}
		}
		return skinProb / rect.width;
	}

	private static void drawRect(Mat image, Rect r, Scalar color) {
		if (!Options.drawBoundingBox) {
			return;
		}
		Core.rectangle(image, r.tl(), r.br(), color, 3);
		int s = 6;
		int xc = r.x + r.width / 2;
		int yc = r.y + r.height / 2;
		Core.line(image, new Point(xc - s, yc), new Point(xc + s, yc), color);
		Core.line(image, new Point(xc, yc - s), new Point(xc, yc + s), color);
	}

	public void release() {
		mask.release();
		hist.release();
		for (Kaomoji kaomoji : kaomojiList) {
			kaomoji.release();
		}
	}
}
