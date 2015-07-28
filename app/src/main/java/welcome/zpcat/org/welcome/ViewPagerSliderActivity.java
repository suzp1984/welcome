package welcome.zpcat.org.welcome;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import welcome.zpcat.org.welcome.widget.SpringIndicator;

public class ViewPagerSliderActivity extends Activity {

    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_slider);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlidePageAdapter());

        SpringIndicator springIndicator = (SpringIndicator) findViewById(R.id.indicator);
        springIndicator.setViewPager(mPager);
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
            return 4;
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
                case 2:
                    resid = R.drawable.guide_01;
                    break;
                case 3:
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
    }
}
