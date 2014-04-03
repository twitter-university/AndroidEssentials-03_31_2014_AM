package com.twitter.university.android.yamba;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by bmeike on 4/3/14.
 */
public class YambaActivity extends Activity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.yamba, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_timeline:
                nextPage(TimelineActivity.class);
                break;

            case R.id.menu_tweet:
                nextPage(TweetActivity.class);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void nextPage(Class<?> target) {
        Intent i = new Intent(this, target);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
    }
}
