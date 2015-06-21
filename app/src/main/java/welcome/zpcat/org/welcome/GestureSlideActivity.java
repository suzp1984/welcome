package welcome.zpcat.org.welcome;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import welcome.zpcat.org.welcome.util.SimpleGestureFilter;

public class GestureSlideActivity extends Activity implements SimpleGestureFilter.SimpleGestureListener {

    private SimpleGestureFilter mSimpleGustureDetector;

    private int[] mResources = {
            R.drawable.guide_01,
            R.drawable.guide_02
    };

    private int mIndex = 0;
    private ImageView mGuide;

    // http://androidexample.com/Swipe_screen_left__right__top_bottom/index.php?view=article_discription&aid=95&aaid=118
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_slide);
        mGuide = (ImageView) findViewById(R.id.guide_iv);

        mSimpleGustureDetector = new SimpleGestureFilter(this, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gesture_slide, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        this.mSimpleGustureDetector.onTouchEvent(event);

        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onSwipe(int direction) {
        Log.e("TAG", " " + direction);

        switch (direction) {
            case SimpleGestureFilter.SWIPE_LEFT:
                mIndex --;
                mIndex += mResources.length;
                mIndex = mIndex % 2;
                break;
            case SimpleGestureFilter.SWIPE_RIGHT:
                mIndex++;
                mIndex = mIndex % 2;
                break;
            case SimpleGestureFilter.SWIPE_UP:
                break;
            case SimpleGestureFilter.SWIPE_DOWN:
                break;
            default:
                break;
        }

        mGuide.setBackgroundResource(mResources[mIndex]);
        mGuide.refreshDrawableState();
    }

    @Override
    public void onDoubleTap() {

    }
}
