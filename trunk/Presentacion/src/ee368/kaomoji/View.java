package ee368.kaomoji;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.SurfaceHolder;

class View extends BaseView {
  private static final String TAG = "Kaomoji::View";
  private Mat mRgba;
  private Mat mGray;

  private FaceTracker faceTracker;

  public View(Context context) {
    super(context);
    faceTracker = new FaceTracker(context);
  }

  @Override
  public void surfaceChanged(SurfaceHolder _holder, int format, int width,
      int height) {
    super.surfaceChanged(_holder, format, width, height);

    synchronized (this) {
      // initialize Mats before usage
      mGray = new Mat();
      mRgba = new Mat();
    }
  }

  @Override
  protected Bitmap processFrame(VideoCapture capture) {
    capture.retrieve(mRgba, Highgui.CV_CAP_ANDROID_COLOR_FRAME_RGBA);
    capture.retrieve(mGray, Highgui.CV_CAP_ANDROID_GREY_FRAME);

    faceTracker.processFrame(mRgba, mGray);

    Bitmap bmp = Bitmap.createBitmap(mRgba.cols(), mRgba.rows(),
       /*Bitmap.Config.RGB_565*/ Bitmap.Config.ARGB_8888);

    boolean b = false;
    try {
      b = Utils.matToBitmap(mRgba, bmp);
      return bmp;
    } catch (Exception e) {
      Log.e(TAG, "Utils.matToBitmap() throws an exception: " + e.getMessage());
      bmp.recycle();
      return null;
    }
  }
  
  
  @Override
  public void run() {
    super.run();

    synchronized (this) {
      // Explicitly deallocate Mats
      if (mRgba != null) {
        mRgba.release();
      }
      if (mGray != null) {
        mGray.release();
      }
      mRgba = null;
      mGray = null;
      faceTracker.release();
    }
  }
}
