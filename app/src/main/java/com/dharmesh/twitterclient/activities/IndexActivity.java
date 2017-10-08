package com.dharmesh.twitterclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dharmesh.twitterclient.R;
import com.dharmesh.twitterclient.fragments.TweetFragment;

import static com.dharmesh.twitterclient.network.TwitterClient.TimelineType.HOME_TIMELINE;
import static com.dharmesh.twitterclient.network.TwitterClient.TimelineType.MENTION;

public class IndexActivity extends AppCompatActivity {

    private static final String TAG = IndexActivity.class.getSimpleName();
    private ViewPager pager;
    private TabLayout tabs;
    private TweetsTimelineAdapter timelineAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        tabs = findViewById(R.id.tabs);
        pager = findViewById(R.id.pager);
        timelineAdapter = new TweetsTimelineAdapter(getSupportFragmentManager());
        pager.setAdapter(timelineAdapter);
        pager.setOffscreenPageLimit(0);
        tabs.setupWithViewPager(pager);
    }

    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(layoutResId);
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Twitter");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_index, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void showProfile(MenuItem item) {
        startActivity(new Intent(this, ProfileActivity.class));
    }

    public class TweetsTimelineAdapter extends FragmentPagerAdapter {

        public TweetsTimelineAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TweetFragment.newInstance("DharmeshGohil", position == 0 ? HOME_TIMELINE : MENTION);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return position == 0 ? "Home" : "Mention";
        }
    }
}
