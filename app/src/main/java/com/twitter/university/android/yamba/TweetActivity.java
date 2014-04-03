package com.twitter.university.android.yamba;


// If mutable data is accessed from more than one thread
// all access must be performed holding a single lock
public class TweetActivity extends YambaActivity {

    public TweetActivity() {
        super(R.layout.activity_tweet);
    }
}
