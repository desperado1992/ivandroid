package ee368.kaomoji.tracking;

import org.opencv.core.Point;
import org.opencv.core.Rect;

import ee368.kaomoji.Options;
import ee368.kaomoji.Util;
import android.util.Log;

public class TrackedFeature {
  private static final String TAG = "Kaomoji::TrackedFeature";
  private Point prevCenter;
  private Rect prevRect;
  private Rect rect;
  private Point center;

  public Rect update(Rect newRect) {
    return update(newRect, null);
  }
  
  public Rect update(Rect newRect, Point refDelta) {
    if (refDelta != null && prevCenter != null) {
      boolean shouldCorrect = false;
      if (newRect == null) {
        newRect = new Rect();
        shouldCorrect = true;
      } else {
        Point newCenter = Util.getCenter(newRect);
        Point delta = computeDelta(newCenter, prevCenter);
        Point d = computeDelta(delta, refDelta);
        if (!d.inside(new Rect(-Options.error, -Options.error,
                               Options.error * 2, Options.error * 2))) {
        
          Log.e(TAG, "Bad detection??" +
          		" refDelta = " + refDelta.toString() +
              " actual = " + delta.toString());
        }
      }
      if (shouldCorrect) {
    	  if(rect != null){
    		newRect.x = rect.x + (int)refDelta.x;
	        newRect.y = rect.y + (int)refDelta.y;
	        newRect.width = rect.width;
	        newRect.height = rect.height;
    	  }
    	  else{
    		newRect.x = prevRect.x + (int)refDelta.x;
  	        newRect.y = prevRect.y + (int)refDelta.y;
  	        newRect.width = prevRect.width;
  	        newRect.height = prevRect.height;
    		  
    	  }
      }
    }
    
    
    prevCenter = center;
    prevRect = rect;
    rect = newRect;
    center = newRect != null ? Util.getCenter(newRect) : null;

    return rect;
  }

  public Point getDelta() {
    if (center != null && prevCenter != null) {
      return computeDelta(center, prevCenter);
    }
    return null;
  }
  
  private static Point computeDelta(Point newCenter, Point prevCenter) {
    return new Point(newCenter.x - prevCenter.x, newCenter.y - prevCenter.y);
  }
}
