package com.dharmesh.twitterclient.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by dgohil on 9/25/17.
 */

public class Tweet {
    @SerializedName("created_at")
    private Date createdAt;
    private long id;
    @SerializedName("id_str")
    private String idStr;
    @SerializedName("text")
    private String text;
    @SerializedName("user")
    private User user;
    @SerializedName("truncated")
    private boolean truncated;
    @SerializedName("in_reply_to_status_id")
    private long inReplyToStatusId;
    @SerializedName("in_reply_to_status_id_str")
    private String inReplyToStatusIdStr;
    @SerializedName("in_reply_to_user_id")
    private long inReplyToUserId;
    @SerializedName("in_reply_to_user_id_str")
    private String inReplyToUserIdStr;
    @SerializedName("in_reply_to_screen_name")
    private String inReplyToScreenName;
    @SerializedName("quoted_status_id")
    private long quotedStatusId;
    @SerializedName("quoted_status_id_str")
    private String quotedStatusIdStr;
    @SerializedName("is_quote_status")
    private boolean isQuoteStatus;
    @SerializedName("quoted_status")
    private Tweet quotedStatus;
    @SerializedName("retweeted_status")
    private Tweet retweetedStatus;
    @SerializedName("quote_count")
    private Integer quoteCount;
    @SerializedName("reply_count")
    private Integer replyCount;
    @SerializedName("retweet_count")
    private Integer retweetCount;
    @SerializedName("favorite_count")
    private Integer favoriteCount;
    @SerializedName("favorited")
    private Boolean favorited;
    @SerializedName("retweeted")
    private Boolean retweeted;
    @SerializedName("possibly_sensitive")
    private Boolean possiblySensitive;
    @SerializedName("filter_level")
    private String filterLevel;
    @SerializedName("lang")
    private String lang;
}
