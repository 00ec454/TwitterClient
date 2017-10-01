package com.dharmesh.twitterclient.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dharmesh.twitterclient.R;
import com.dharmesh.twitterclient.models.Entity;
import com.dharmesh.twitterclient.models.Tweet;
import com.dharmesh.twitterclient.util.CropCircleTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by dgohil on 9/26/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Tweet> tweets;

    public TweetAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tweet_item, parent, false);
            return new ViewHolder(view);

        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tweet_item_with_media, parent, false);
            return new MediaViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == 0) {
            ((ViewHolder) holder).bind(tweets.get(position));
        } else {
            ((MediaViewHolder) holder).bind(tweets.get(position));
        }
    }

    public void addOne(Tweet tweet) {
        tweets.add(0, tweet);
        notifyDataSetChanged();
    }

    public void addAll(List<Tweet> newTweets) {
        tweets.addAll(newTweets);
        notifyDataSetChanged();
    }

    public void refresh(List<Tweet> newTweets) {
        tweets.clear();
        tweets.addAll(newTweets);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Entity entity = tweets.get(position).getEntities();
        if (entity != null && entity.getMedia() != null && !entity.getMedia().isEmpty()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    class MediaViewHolder extends TweetAdapter.ViewHolder {

        private ImageView ivMedia;

        public MediaViewHolder(View view) {
            super(view);
            ivMedia = view.findViewById(R.id.ivMedia);
        }

        @Override
        public void bind(Tweet tweet) {
            super.bind(tweet);
            Picasso.with(context)
                    .load(tweet.getEntities().getMedia().get(0).getMediaUrl())
                    .into(ivMedia);
        }
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
            tvMention.setText(String.format("@%s", tweet.getUser().getScreenName()));
            tvTime.setText(DateUtils.getRelativeTimeSpanString(tweet.getCreatedAt().getTime(),
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL).toString());
            tvTweet.setText(tweet.getText());
            Linkify.addLinks(tvTweet, Linkify.ALL);
            tvReply.setText(String.valueOf(tweet.getReplyCount()));
            tvRetweet.setText(String.valueOf(tweet.getRetweetCount()));
            for (Drawable drawable : tvRetweet.getCompoundDrawables()) {
                if (drawable != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (tweet.getRetweeted()) {
                        drawable.setTint(ContextCompat.getColor(context, android.R.color.holo_green_dark));
                    }
                }
            }
            tvLove.setText(String.valueOf(tweet.getFavoriteCount()));
            Picasso.with(context)
                    .load(tweet.getUser().getProfileImageUrl())
                    .transform(new CropCircleTransformation())
                    .into(ivProfile);

        }
    }
}
