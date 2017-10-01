package com.dharmesh.twitterclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dharmesh.twitterclient.R;
import com.dharmesh.twitterclient.util.EndlessRecyclerViewScrollListener;
import com.dharmesh.twitterclient.adapters.TweetAdapter;
import com.dharmesh.twitterclient.models.Tweet;
import com.dharmesh.twitterclient.models.User;
import com.dharmesh.twitterclient.network.TwitterClient;
import com.dharmesh.twitterclient.util.CropCircleTransformation;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.vistrav.pop.Pop;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

public class IndexActivity extends AppCompatActivity {

    private static final String TAG = IndexActivity.class.getSimpleName();
    private RecyclerView rvTweets;
    private GsonBuilder builder = new GsonBuilder()
            .setDateFormat("EEE MMM dd HH:mm:ss Z yyyy")
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
    private TweetAdapter tweetAdapter;
    private TwitterClient twitterClient;
    private boolean hasSwiped;
    private long maxId = -1;
    private SwipeRefreshLayout swipeRefreshLayout;
    final private JsonHttpResponseHandler responseHandler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            Tweet tweet = builder.create().fromJson(response.toString(), Tweet.class);
            tweetAdapter.addOne(tweet);
            rvTweets.scrollToPosition(0);
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
            Tweet[] tweets = builder.create().fromJson(response.toString(), Tweet[].class);
            setMaxId(tweets);
            if (hasSwiped) {
                tweetAdapter.refresh(Arrays.asList(tweets));
                swipeRefreshLayout.setRefreshing(false);
                hasSwiped = false;
            } else {
                tweetAdapter.addAll(Arrays.asList(tweets));
            }
        }

    };
    private AlertDialog tweetDialog;
    private EditText etTweet;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        authenticateUser();
        initializeRecyclerView();
        refreshOnSwipe();
        twitterClient.getHomeTimeline(0, responseHandler);
    }

    private void initializeRecyclerView() {
        rvTweets = findViewById(R.id.rvTweets);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvTweets.setLayoutManager(linearLayoutManager);
        tweetAdapter = new TweetAdapter(this, new ArrayList<>());
        rvTweets.setAdapter(tweetAdapter);
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                twitterClient.getHomeTimeline(maxId, responseHandler);
            }
        };
        rvTweets.addOnScrollListener(scrollListener);
    }

    private void authenticateUser() {
        twitterClient = new TwitterClient(this);
        if (!twitterClient.isAuthenticated()) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    public void refreshOnSwipe() {
        swipeRefreshLayout
                = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            hasSwiped = true;
            twitterClient.getHomeTimeline(0, responseHandler);

        });
    }

    public void composeTweet(View view) {
        tweetDialog = Pop.on(this)
                .layout(R.layout.activity_create_tweet)
                .show(view13 -> {
                    etTweet = view13.findViewById(R.id.etTweet);
                    final ImageView ivProfile = view13.findViewById(R.id.ivProfile);
                    final TextView tvCharCounts = view13.findViewById(R.id.tvCharCounts);
                    etTweet.setOnKeyListener((view12, i, keyEvent) -> {
                        if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                            tvCharCounts.setText(String.valueOf(140 - etTweet.getText().toString().length()));
                        }
                        return true;
                    });
                    twitterClient.getUser("DharmeshGohil", new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            User user = builder.create().fromJson(response.toString(), User.class);
                            Picasso.with(IndexActivity.this)
                                    .load(user.getProfileImageUrl())
                                    .transform(new CropCircleTransformation())
                                    .into(ivProfile);
                        }
                    });
                });
    }

    public void tweet(View view) {
        String status = etTweet.getText().toString();
        if (status.length() > 140 || status.length() < 3) {
            Toast.makeText(getBaseContext(), R.string.valdate_msg, Toast.LENGTH_LONG).show();
        } else {
            twitterClient.tweet(status, responseHandler);
            tweetDialog.dismiss();
        }
    }

    public void closeDialog(View view) {
        if (tweetDialog != null && tweetDialog.isShowing()) {
            tweetDialog.dismiss();
        }
    }

    private void setMaxId(Tweet... tweets) {
        if (tweets == null || tweets.length == 0) {
            maxId = -1;
            return;
        }
        maxId = tweets[0].getId();
        for (Tweet tweet : tweets) {
            maxId = Math.min(maxId, tweet.getId());
        }
    }
}
