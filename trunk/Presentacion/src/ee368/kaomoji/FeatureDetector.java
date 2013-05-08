package ee368.kaomoji;

import java.util.ArrayList;
import java.util.List;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
//import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

import android.content.Context;
import android.util.Log;

public class FeatureDetector {

  private static final String TAG = "Kaomoji::FeatureDetector";

  private CascadeClassifier mCascade;
  private double minWidth = 0.4;
  private double minHeight = 0.4;
  private double maxWidth = 1;
  private double maxHeight = 1;
  private double scale = 1.1;
  private int minNeighbors = 2;

  private FeatureDetector(Context context, int resourceId) {
    
    String path = Utils.exportResource(context, resourceId);
    
    mCascade = new CascadeClassifier(path);
    
    if (mCascade.empty()) {
      Log.e(TAG, "Failed to load cascade classifier: " + resourceId);
      mCascade = null;
    } else
      Log.i(TAG, "Loaded cascade classifier from " + path);
  }

  public FeatureDetector setMinSize(double minWidth, double minHeight) {
    this.minWidth = minWidth;
    this.minHeight = minHeight;
    return this;
  }
  
  public FeatureDetector setMaxSize(double maxWidth, double maxHeight) {
    this.maxWidth = maxWidth;
    this.maxHeight = maxHeight;
    return this;
  }

  public FeatureDetector setScale(double scale) {
    this.scale = scale;
    return this;
  }

  public FeatureDetector setMinNeighbors(int minNeighbors) {
    this.minNeighbors = minNeighbors;
    return this;
  }
  
  private int getScaledWidth(Mat image, double s) {
    return (int)(s > 1 ? s : Math.round(image.cols() * s));
  }
  
  private int getScaledHeight(Mat image, double s) {
    return (int)(s > 1 ? s : Math.round(image.rows() * s));
  }

  public List<Rect> detect(Mat image) {
    int minW = getScaledWidth(image, minWidth);
    int minH = getScaledHeight(image, minHeight);
    int maxW = getScaledWidth(image, maxWidth);
    int maxH = getScaledHeight(image, maxHeight);
    List<Rect> objects = new ArrayList<Rect>(); 
    mCascade.detectMultiScale(image, objects, scale, minNeighbors,
        Objdetect.CASCADE_DO_CANNY_PRUNING,
        new Size(minW, minH), new Size(maxW, maxH));
    return objects;
  }
  
  public List<Rect> detect(Mat image, Rect roi) {
    if (roi.x < 0) {
      roi.width += roi.x;
      roi.x = 0;
    }
    if (roi.y < 0) {
      roi.height += roi.y;
      roi.y = 0;
    }
    if (roi.x + roi.width > image.cols()) {
      roi.width = image.cols() - roi.x;
    }
    if (roi.y + roi.height > image.rows()) {
      roi.height = image.rows() - roi.y;
    }
    List<Rect> rects = detect(image.submat(roi));
    for (Rect rect : rects) {
      rect.x += roi.x;
      rect.y += roi.y;
    }
    return rects;
  }
  
  public static FeatureDetector getFaceDetector(Context context) {
    return new FeatureDetector(context, R.raw.lbpcascade_frontalface);
  }
  
  public static FeatureDetector getEyeDetector(Context context) {
    //return new FeatureDetector(context, R.raw.haarcascade_eye_tree_eyeglasses)
    return new FeatureDetector(context, R.raw.haarcascade_eye)
        .setMinSize(25, 15)
        .setMaxSize(0.5, 1)
        .setScale(1.15)
        .setMinNeighbors(4);
  }

  public static FeatureDetector getMouthDetector(Context context) {
    return new FeatureDetector(context, R.raw.haarcascade_mcs_mouth)
        .setMinSize(60, 20)
        .setMaxSize(1, 1)
        .setScale(1.15)
        .setMinNeighbors(4);
  }
}
