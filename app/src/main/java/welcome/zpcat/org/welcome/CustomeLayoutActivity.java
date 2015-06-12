package welcome.zpcat.org.welcome;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class CustomeLayoutActivity extends Activity {

    private ImageView mPointOneIv;
    private ImageView mPointTwoIv;

    private ImageView[] mPointViewArray;

    private PageScrollLayout.OnPagescrollLayoutListener scrollListener = new PageScrollLayout.OnPagescrollLayoutListener() {

        @Override
        public void scrollToNext() {
            startNextActivity();
        }

        @Override
        public void scrollTo(int whichScreen) {
            switch (whichScreen) {
                case 0:
                    mPointViewArray[0].setBackgroundResource(R.drawable.ic_guide_orange_point);
                    mPointViewArray[1].setBackgroundResource(R.drawable.ic_guide_gray_point);
                    break;
                case 1:
                    mPointViewArray[0].setBackgroundResource(R.drawable.ic_guide_gray_point);
                    mPointViewArray[1].setBackgroundResource(R.drawable.ic_guide_orange_point);
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_custome_layout);

        mPointOneIv = (ImageView) findViewById(R.id.iv_guide_point_one);
        mPointTwoIv = (ImageView) findViewById(R.id.iv_guide_point_two);

        mPointViewArray = new ImageView[]{mPointOneIv, mPointTwoIv};
        PageScrollLayout guideScrollLayout = (PageScrollLayout) findViewById(
                R.id.sv_page);
        guideScrollLayout.setScrollListener(scrollListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_custome_layout, menu);
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

    private void startNextActivity() {
        finish();
    }
}
