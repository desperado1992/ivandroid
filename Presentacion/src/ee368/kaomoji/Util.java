package ee368.kaomoji;

import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;

public class Util {

  public static Point getCenter(Rect rect) {
    return new Point(rect.x + rect.width / 2, rect.y + rect.height / 2);
  }
  
  public static double dist2(Scalar a, Scalar b) {
    return dist2(a.val, b.val);
  }
  
  public static double dist2(double[] a, double[] b) {
    double d = 0;
    for (int i = 0; i < Math.min(a.length, b.length); i++) {
      d += (a[i] - b[i]) * (a[i] - b[i]);
    }
    return d;
  }
  
  public static double maxDist(double[] a, double[] b) {
    int n = Math.min(3, Math.min(a.length, b.length));
    double d = 0;
    for (int i = 0; i < n; i++) {
      d = Math.max(d, Math.abs(a[i] - b[i]));
    }
    return d;
  }
}
