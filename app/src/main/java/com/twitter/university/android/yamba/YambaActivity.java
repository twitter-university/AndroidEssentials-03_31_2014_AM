package com.twitter.university.android.yamba;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewPropertyAnimator;
import android.widget.Toast;


public abstract class YambaActivity extends Activity {
    private final int rootLayout;

    protected YambaActivity(int rootLayout) {
        this.rootLayout = rootLayout;
    }

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

            case android.R.id.home:
            case R.id.menu_about:
                about();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(rootLayout);

        ActionBar aBar = getActionBar();
        aBar.setHomeButtonEnabled(true);
    }

    private void nextPage(Class<?> target) {
        Intent i = new Intent(this, target);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
    }

    private void about() {
        Toast.makeText(this, R.string.about_yamba, Toast.LENGTH_LONG).show();
    }
}
