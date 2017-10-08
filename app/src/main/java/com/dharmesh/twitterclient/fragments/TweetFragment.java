package com.dharmesh.twitterclient.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dharmesh.twitterclient.R;
import com.dharmesh.twitterclient.activities.LoginActivity;
import com.dharmesh.twitterclient.adapters.TweetAdapter;
import com.dharmesh.twitterclient.models.Tweet;
import com.dharmesh.twitterclient.models.User;
import com.dharmesh.twitterclient.network.TwitterClient;
import com.dharmesh.twitterclient.network.TwitterClient.TimelineType;
import com.dharmesh.twitterclient.util.CropCircleTransformation;
import com.dharmesh.twitterclient.util.EndlessRecyclerViewScrollListener;
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

import static com.dharmesh.twitterclient.network.TwitterClient.TimelineType.HOME_TIMELINE;
import static com.dharmesh.twitterclient.network.TwitterClient.TimelineType.MENTION;

/**
 * Created by dgohil on 10/3/17.
 */

public class TweetFragment extends Fragment {
    private RecyclerView rvTweets;
    private GsonBuilder builder = new GsonBuilder()
            .setDateFormat("EEE MMM dd HH:mm:ss Z yyyy")
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
    private TweetAdapter tweetAdapter;
    private TwitterClient twitterClient;
    private boolean hasSwiped;
    private long maxId = -1;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AlertDialog tweetDialog;
    private EditText etTweet;
    private FloatingActionButton fabTweet;
    private EndlessRecyclerViewScrollListener scrollListener;
    private static final String TIME_LINE_TYPE = "TimelineType";
    private static final String SCREEN_NAME = "SCREEN_NAME";
    private static final String TAG = TweetFragment.class.getSimpleName();
    private String screenName = "DharmeshGohil";
    private String timeLineType;

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

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            Log.e(TAG, errorResponse.toString(), throwable);
        }
    };

    public static TweetFragment newInstance(String screenName, TimelineType type) {
        Bundle args = new Bundle();
        args.putString(SCREEN_NAME, screenName);
        args.putString(TIME_LINE_TYPE, type.toString());
        TweetFragment fragment = new TweetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticateUser();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tweets, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        rvTweets = view.findViewById(R.id.rvTweets);
        fabTweet = view.findViewById(R.id.fabTweet);
        fabTweet.setOnClickListener(this::composeTweet);
        initializeRecyclerView();
        refreshOnSwipe();
        timeLineType = getArguments().getString(TIME_LINE_TYPE);
        screenName = getArguments().getString(SCREEN_NAME, "DharmeshGohil");
        twitterClient.getTimeline(screenName, TimelineType.valueOf(timeLineType), 0, responseHandler);
    }

    public void refreshOnSwipe() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            hasSwiped = true;
            twitterClient.getTimeline(screenName, TimelineType.valueOf(timeLineType), 0, responseHandler);
        });
    }

    public void composeTweet(View view) {
        tweetDialog = Pop.on(getActivity())
                .layout(R.layout.activity_create_tweet)
                .show(view13 -> {
                    etTweet = view13.findViewById(R.id.etTweet);
                    if (!Arrays.asList(MENTION, HOME_TIMELINE).contains(TimelineType.valueOf(timeLineType))) {
                        etTweet.setText(String.format("@%s", screenName));
                    }
                    final ImageView ivClose = view13.findViewById(R.id.ivClose);
                    ivClose.setOnClickListener(TweetFragment.this::closeDialog);
                    final Button btTweet = view13.findViewById(R.id.btTweet);
                    btTweet.setOnClickListener(TweetFragment.this::tweet);
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
                            Picasso.with(getContext())
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
            Toast.makeText(getContext(), R.string.valdate_msg, Toast.LENGTH_LONG).show();
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

    private void initializeRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvTweets.setLayoutManager(linearLayoutManager);
        tweetAdapter = new TweetAdapter(getContext(), new ArrayList<>());
        rvTweets.setAdapter(tweetAdapter);
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                twitterClient.getTimeline(screenName, TimelineType.valueOf(timeLineType), maxId, responseHandler);
            }
        };
        rvTweets.addOnScrollListener(scrollListener);
    }

    private void authenticateUser() {
        twitterClient = new TwitterClient(getContext());
        if (!twitterClient.isAuthenticated()) {
            startActivity(new Intent(getContext(), LoginActivity.class));
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
