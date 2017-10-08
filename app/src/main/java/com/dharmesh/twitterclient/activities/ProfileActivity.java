package com.dharmesh.twitterclient.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.dharmesh.twitterclient.R;
import com.dharmesh.twitterclient.fragments.TweetFragment;
import com.dharmesh.twitterclient.models.User;
import com.dharmesh.twitterclient.network.TwitterClient;
import com.dharmesh.twitterclient.util.CropCircleTransformation;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.dharmesh.twitterclient.network.TwitterClient.TimelineType.FAVORITE;
import static com.dharmesh.twitterclient.network.TwitterClient.TimelineType.USER_TIMELINE;

public class ProfileActivity extends AppCompatActivity {

    public static final String SCREEN_NAME = "SCREEN_NAME";
    private static final String TAG = ProfileActivity.class.getSimpleName();

    private GsonBuilder builder = new GsonBuilder()
            .setDateFormat("EEE MMM dd HH:mm:ss Z yyyy")
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ViewPager viewPager = findViewById(R.id.vpPager);
        String screenName = getIntent().getStringExtra(SCREEN_NAME);
        screenName = screenName != null ? screenName : "DharmeshGohil";
        ProfileAdapter adapter = new ProfileAdapter(getSupportFragmentManager(), screenName);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        new TwitterClient(this)
                .getUser(screenName, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        User user = builder.create().fromJson(response.toString(), User.class);
                        populateView(user);
                    }
                });

    }

    private void populateView(User user) {
        Picasso.with(getBaseContext())
                .load(user.getProfileImageUrl())
                .transform(new CropCircleTransformation())
                .into((ImageView) findViewById(R.id.ivProfileImage));
        Picasso.with(getBaseContext())
                .load(user.getProfileBackgroundImageUrl())
                .into((ImageView) findViewById(R.id.ivCoverImage));
        TextView tvCountFollowers = findViewById(R.id.tvCountFollowers);
        TextView tvCountFollowing = findViewById(R.id.tvCountFollowing);
        TextView tvName = findViewById(R.id.tvName);
        TextView tvUserName = findViewById(R.id.tvUserName);
        TextView tvInfo = findViewById(R.id.tvInfo);

        tvCountFollowers.setText(String.valueOf(user.getFollowersCount()));
        tvCountFollowing.setText(String.valueOf(user.getFriendsCount()));
        tvName.setText(String.valueOf(user.getName()));
        tvUserName.setText(user.getScreenName());
        tvInfo.setText(user.getDescription());
    }

    public static class ProfileAdapter extends FragmentPagerAdapter {
        private static int PAGE_COUNT = 2;

        private String titles[] = {"Tweets", "Favorites"};
        private String screenName;

        public ProfileAdapter(FragmentManager fm, String screenName) {
            super(fm);
            this.screenName = screenName;
        }


        @Override
        public Fragment getItem(int position) {
            Log.i(TAG, "getItem: position" + position);
            switch (position) {
                case 0:
                    return TweetFragment.newInstance(screenName, USER_TIMELINE);
                default:
                    return TweetFragment.newInstance(screenName, FAVORITE);
            }
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
