package com.twitter.university.android.yamba;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

// If mutable data is accessed from more than one thread
// all access must be performed holding a single lock


public class TweetActivity extends Activity {
    private static final String TAG = "TWEET";

    static class Poster extends AsyncTask<String, Void, Integer> {
        private Context ctxt;
        Poster(Context ctxt) { this.ctxt = ctxt; }

        // PRETEND TO SEND ON THE NETWORK

        @Override
        protected Integer doInBackground(String... strings) {
            String tweet = strings[0];

            try { Thread.sleep(1000 * 30); }
            catch (InterruptedException e) { }

            return Integer.valueOf(R.string.tweet_succeeded);
        }

        @Override
        protected void onPostExecute(Integer status) {
            poster = null;
            Toast.makeText(ctxt, status.intValue(), Toast.LENGTH_LONG).show();
        }
    }

    static Poster poster;

    private int okColor;
    private int warnColor;
    private int errColor;

    private int tweetLenMax;
    private int warnMax;
    private int errMax;

    private Button submitButton;
    private TextView countView;
    private EditText tweetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
        Log.d(TAG, "onCreate: " + this);

        Resources rez = getResources();
        okColor = rez.getColor(R.color.ok);
        warnColor = rez.getColor(R.color.warn);
        errColor = rez.getColor(R.color.error);

        tweetLenMax = rez.getInteger(R.integer.tweet_limit);
        warnMax = rez.getInteger(R.integer.warn_limit);
        errMax = rez.getInteger(R.integer.err_limit);

        countView = (TextView) findViewById(R.id.tweet_count);

        submitButton = (Button) findViewById(R.id.tweet_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { post(); }
        });

        tweetView = ((EditText) findViewById(R.id.tweet_tweet));
        tweetView.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable editable) { setCount(); }

                    @Override
                    public void beforeTextChanged(CharSequence cs, int i, int i2, int i3) {  }

                    @Override
                    public void onTextChanged(CharSequence cs, int i, int i2, int i3) { }
                }
        );
    }

    void setCount() {
        int n = tweetView.getText().length();

        submitButton.setEnabled(checkTweetLen(n));

        n = tweetLenMax - n;

        int color;
        if (n > warnMax) { color = okColor; }
        else if (n > errMax) { color = warnColor; }
        else  { color = errColor; }

        countView.setText(String.valueOf(n));
        countView.setTextColor(color);
    }

    void post() {
        if (null != poster) { return; }

        String tweet = tweetView.getText().toString();
        if (!checkTweetLen(tweet.length())) { return; }

        tweetView.setText("");
        submitButton.setEnabled(false);

        poster = new Poster(getApplicationContext());
        poster.execute(tweet);
    }

    private boolean checkTweetLen(int n) {
        return (errMax <= n) && (tweetLenMax > n);
    }
}
