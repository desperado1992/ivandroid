package ee368.kaomoji;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class ControlPanel extends Activity {
  private static final String TAG         = "Kaomoji::Activity";

  private MenuItem disable;
  private ArrayList<MenuItem> kaomojiSelectors;

  public ControlPanel() {
    Log.i(TAG, "Instantiated new " + this.getClass());
  }

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    Log.i(TAG, "onCreate");
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(new View(this));
    kaomojiSelectors = new ArrayList<MenuItem>();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    Log.i(TAG, "onCreateOptionsMenu");
    disable = menu.add("x");
    for (Kaomoji kaomoji : FaceTracker.kaomojiList) {
      kaomojiSelectors.add(menu.add(kaomoji.getLabel()));
    }
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    Log.e(TAG, "Menu Item selected " + item);
    if (item == disable) {
      Options.activeKaomoji = -1;
    } else {
      for (int i = 0; i < kaomojiSelectors.size(); i++) {
        if (kaomojiSelectors.get(i) == item) {
          Options.activeKaomoji = i;
          break;
        }
      }
    }
    return true;
  }
}
