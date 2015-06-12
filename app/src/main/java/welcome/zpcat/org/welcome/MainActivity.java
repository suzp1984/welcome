package welcome.zpcat.org.welcome;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements ListView.OnItemClickListener {

    public static final String CATEGORY_SAMPLE_LIST = "org.zpcat.welcome.sample_code";

    private final String TAG = "tag";

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListView = new ListView(this);

        setContentView(mListView);
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String, Object> map = (Map<String, Object>) mListView.getItemAtPosition(position);

        Intent intent = (Intent) map.get("intent");
        startActivity(intent);
    }

    private void initData() {
        List<Map<String, Object>> intentData = new ArrayList<>();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(CATEGORY_SAMPLE_LIST);

        PackageManager pm = getPackageManager();
        List<ResolveInfo> plist = pm.queryIntentActivities(mainIntent, 0);

        for (ResolveInfo info : plist) {
            Log.e(TAG, info.activityInfo.toString());

            CharSequence labelSeq = info.loadLabel(pm);
            String label = labelSeq != null ? labelSeq.toString() :
                    info.activityInfo.name;
            Intent intent = new Intent();
            intent.setClassName(info.activityInfo.applicationInfo.packageName,
                    info.activityInfo.name);

            Map<String, Object> temp = new HashMap<>();
            temp.put("title", label);
            temp.put("intent", intent);

            intentData.add(temp);
        }

        mListView.setAdapter(new SimpleAdapter(this, intentData,
                android.R.layout.simple_list_item_1, new String[] {"title"},
                new int[] { android.R.id.text1 }));

        mListView.setOnItemClickListener(this);
    }
}
