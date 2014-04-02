package com.twitter.university.android.yamba.svc;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.twitter.university.android.yamba.R;

/**
 * Created by bmeike on 4/2/14.
 */
public class YambaService extends IntentService {
    private static final String TAG = "SVC";

    private static final String PARAM_TWEET = "YambaService.TWEET";

    // Marshal post request into an intent
    public static void post(Context ctxt, String tweet) {
        Intent i = new Intent(ctxt, YambaService.class);
        i.putExtra(PARAM_TWEET, tweet);
        ctxt.startService(i);
    }


    private Handler hdlr;

    public YambaService() { super(TAG); }

    @Override
    public void onCreate() {
        super.onCreate();
        hdlr = new Handler();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String tweet = intent.getStringExtra(PARAM_TWEET);

        try { Thread.sleep(1000 * 30); }
        catch (InterruptedException e) { }

        // Use a handler to post a toast from the UI thread
        hdlr.post(
            new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(
                        YambaService.this,
                        R.string.tweet_succeeded,
                        Toast.LENGTH_LONG)
                        .show();
                }
        } );
    }
}
