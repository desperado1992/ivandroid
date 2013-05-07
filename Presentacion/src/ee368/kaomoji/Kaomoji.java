package ee368.kaomoji;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;


public class Kaomoji {
  
  private static final String TAG = "Kaomoji::Kaomoji";
  
  Mat image;
  Mat leftEye;
  Mat rightEye;
  Mat mouth;
  Mat temp;
  
  String name;
  public Kaomoji(String name, String path) {
    image = Highgui.imread(path);
    int width = image.width() / 3;
    leftEye = image.submat(new Rect(0, 0, width, image.height()));
    rightEye = image.submat(new Rect(2 * width, 0, width, image.height()));
    mouth = image.submat(new Rect(width, 0, width, image.height()));
    temp = new Mat();

    this.name = name;
  }
  
  public String getLabel() {
    return name;
  }
  
  public void apply(
      Mat image, Rect leftEyeRect, Rect rightEyeRect, Rect mouthRect) {
    draw(image, leftEye, leftEyeRect, 1.5, -0.15, 0.15);
    draw(image, rightEye, rightEyeRect, 1.5, 0.15, 0.15);
    draw(image, mouth, mouthRect, 1.4, 0, -0.18);
  }
  
  private void draw(Mat bg, Mat fg, Rect location,
      double scale, double offsetX, double offsetY) {
    resize(fg, temp, location, scale);
    
    Point center = Util.getCenter(location);
    int t = (int)(center.y - temp.height() * (0.5 - offsetY));
    int b = t + temp.height();
    int l = (int)(center.x - temp.width() * (0.5 - offsetX));
    int r = l + temp.width();
    double[] color = {0, 0, 0, 255};
    for (int y = t; y < b; y++) {
      for (int x = l; x < r; x++) {
        if (temp.get(y - t, x - l)[0] < 200) {
          bg.put(y, x, color);
        }
      }
    }
  }
  
  private void resize(Mat src, Mat dst, Rect target, double s) {
    double r = (double)src.width() / src.height();
    Size size = new Size(target.height * r * s, target.height * s);
    Imgproc.resize(src, dst, size);
  }
  
  public void release() {
    image.release();
    temp.release();
  }

}
