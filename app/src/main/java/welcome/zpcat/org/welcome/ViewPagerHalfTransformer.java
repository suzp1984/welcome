package welcome.zpcat.org.welcome;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by moses on 7/13/15.
 */
public class ViewPagerHalfTransformer extends Activity {

    private ViewPager mPager;

    private final ViewPager.PageTransformer mPageTransformer = new ViewPager.PageTransformer() {
        @Override
        public void transformPage(View view, float v) {
            if (v < 0.0f) {
                view.setTranslationX(mPager.getWidth() * -v);
                view.setAlpha(Math.max(1.0f + v, 0.0f));
            } else {
                view.setTranslationX(0.0f);
                view.setAlpha(1.0f);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_slider);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlidePageAdapter());
        mPager.setPageTransformer(false, mPageTransformer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_pager_slider, menu);
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

    private class SlidePageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater inflater = (LayoutInflater) container.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = new ImageView(container.getContext());
            int resid = -1;

            switch (position) {
                case 0:
                    resid = R.drawable.guide_01;
                    break;
                case 1:
                    resid = R.drawable.guide_02;
                    break;
                default:
                    break;
            }

            view.setBackgroundResource(resid);
            ((ViewPager) container).addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public float getPageWidth(int position) {
            return position == 1 ? 0.5f : 1.0f;
        }
    }
}
