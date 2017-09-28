package com.dharmesh.twitterclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dharmesh.twitterclient.R;
import com.dharmesh.twitterclient.adapters.TweetAdapter;
import com.dharmesh.twitterclient.models.Tweet;
import com.dharmesh.twitterclient.network.TwitterClient;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

public class IndexActivity extends AppCompatActivity {

    private static final String TAG = IndexActivity.class.getSimpleName();
    private RecyclerView rvTweets;
    private GsonBuilder builder = new GsonBuilder()
            //Thu Sep 28 06:04:08 +0000 2017
            .setDateFormat("EEE MMM dd HH:mm:ss Z yyyy")
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
    private TweetAdapter tweetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        rvTweets = findViewById(R.id.rvTweets);

        TwitterClient twitterClient = new TwitterClient(this);
        if (!twitterClient.isAuthenticated()) {
            startActivity(new Intent(this, LoginActivity.class));
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvTweets.setLayoutManager(linearLayoutManager);

        tweetAdapter = new TweetAdapter(this, new ArrayList<>());
        rvTweets.setAdapter(tweetAdapter);

        twitterClient.getHomeTimeline(0, responseHandler);
    }

    JsonHttpResponseHandler responseHandler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            Log.i(TAG, "onSuccess: " + response);
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
            try {
                Log.i(TAG, "onSuccess: 1" + response.get(0).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Tweet[] tweets = builder.create().fromJson(response.toString(), Tweet[].class);
            tweetAdapter.addAll(Arrays.asList(tweets));
            Log.i(TAG, "onSuccess: tweets " + tweets.length);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            Log.e(TAG, "onFailure: " + errorResponse);
        }

    };
}
