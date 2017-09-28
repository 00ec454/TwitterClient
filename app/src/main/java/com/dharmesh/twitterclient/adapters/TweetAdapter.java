package com.dharmesh.twitterclient.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dharmesh.twitterclient.R;
import com.dharmesh.twitterclient.models.Tweet;

import java.util.List;


/**
 * Created by dgohil on 9/26/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    private Context context;
    private List<Tweet> tweets;

    public TweetAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    @Override
    public TweetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tweet_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TweetAdapter.ViewHolder holder, int position) {
        holder.bind(tweets.get(position));
    }

    public void addAll(List<Tweet> newTweets) {
        tweets.addAll(newTweets);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivProfile;
        private TextView tvProfileName;
        private TextView tvMention;
        private TextView tvTweet;
        private TextView tvTime;
        private TextView tvReply;
        private TextView tvRetweet;
        private TextView tvLove;
        private TextView tvSendPrivate;

        public ViewHolder(View view) {
            super(view);
            ivProfile = view.findViewById(R.id.ivProfile);
            tvProfileName = view.findViewById(R.id.tvProfileName);
            tvMention = view.findViewById(R.id.tvMention);
            tvTweet = view.findViewById(R.id.tvTweet);
            tvTime = view.findViewById(R.id.tvTime);
            tvReply = view.findViewById(R.id.tvReply);
            tvRetweet = view.findViewById(R.id.tvRetweet);
            tvLove = view.findViewById(R.id.tvLove);
            tvSendPrivate = view.findViewById(R.id.tvSendPrivate);
        }

        public void bind(Tweet tweet) {
            tvProfileName.setText(tweet.getUser().getName());
            tvMention.setText(tweet.getUser().getScreenName());
            tvTime.setText(DateUtils.getRelativeTimeSpanString(tweet.getCreatedAt().getTime(),
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_TIME).toString());
            tvTweet.setText(tweet.getText());
            tvReply.setText(String.valueOf(tweet.getReplyCount()));
            tvRetweet.setText(String.valueOf(tweet.getRetweetCount()));
            tvLove.setText(String.valueOf(tweet.getFavoriteCount()));
            Glide.with(context)
                    .load(tweet.getUser().getProfileImageUrl())
                    .into(ivProfile);

        }
    }

}
