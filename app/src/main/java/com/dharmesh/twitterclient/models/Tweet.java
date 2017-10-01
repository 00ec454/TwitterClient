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
    private int quoteCount;
    @SerializedName("reply_count")
    private int replyCount;
    @SerializedName("retweet_count")
    private int retweetCount;
    @SerializedName("favorite_count")
    private int favoriteCount;
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
    private Entity entities;

    public Entity getEntities() {
        return entities;
    }

    public void setEntities(Entity entities) {
        this.entities = entities;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isTruncated() {
        return truncated;
    }

    public void setTruncated(boolean truncated) {
        this.truncated = truncated;
    }

    public long getInReplyToStatusId() {
        return inReplyToStatusId;
    }

    public void setInReplyToStatusId(long inReplyToStatusId) {
        this.inReplyToStatusId = inReplyToStatusId;
    }

    public String getInReplyToStatusIdStr() {
        return inReplyToStatusIdStr;
    }

    public void setInReplyToStatusIdStr(String inReplyToStatusIdStr) {
        this.inReplyToStatusIdStr = inReplyToStatusIdStr;
    }

    public long getInReplyToUserId() {
        return inReplyToUserId;
    }

    public void setInReplyToUserId(long inReplyToUserId) {
        this.inReplyToUserId = inReplyToUserId;
    }

    public String getInReplyToUserIdStr() {
        return inReplyToUserIdStr;
    }

    public void setInReplyToUserIdStr(String inReplyToUserIdStr) {
        this.inReplyToUserIdStr = inReplyToUserIdStr;
    }

    public String getInReplyToScreenName() {
        return inReplyToScreenName;
    }

    public void setInReplyToScreenName(String inReplyToScreenName) {
        this.inReplyToScreenName = inReplyToScreenName;
    }

    public long getQuotedStatusId() {
        return quotedStatusId;
    }

    public void setQuotedStatusId(long quotedStatusId) {
        this.quotedStatusId = quotedStatusId;
    }

    public String getQuotedStatusIdStr() {
        return quotedStatusIdStr;
    }

    public void setQuotedStatusIdStr(String quotedStatusIdStr) {
        this.quotedStatusIdStr = quotedStatusIdStr;
    }

    public boolean isQuoteStatus() {
        return isQuoteStatus;
    }

    public void setQuoteStatus(boolean quoteStatus) {
        isQuoteStatus = quoteStatus;
    }

    public Tweet getQuotedStatus() {
        return quotedStatus;
    }

    public void setQuotedStatus(Tweet quotedStatus) {
        this.quotedStatus = quotedStatus;
    }

    public Tweet getRetweetedStatus() {
        return retweetedStatus;
    }

    public void setRetweetedStatus(Tweet retweetedStatus) {
        this.retweetedStatus = retweetedStatus;
    }

    public int getQuoteCount() {
        return quoteCount;
    }

    public void setQuoteCount(int quoteCount) {
        this.quoteCount = quoteCount;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public Boolean getFavorited() {
        return favorited;
    }

    public void setFavorited(Boolean favorited) {
        this.favorited = favorited;
    }

    public Boolean getRetweeted() {
        return retweeted;
    }

    public void setRetweeted(Boolean retweeted) {
        this.retweeted = retweeted;
    }

    public Boolean getPossiblySensitive() {
        return possiblySensitive;
    }

    public void setPossiblySensitive(Boolean possiblySensitive) {
        this.possiblySensitive = possiblySensitive;
    }

    public String getFilterLevel() {
        return filterLevel;
    }

    public void setFilterLevel(String filterLevel) {
        this.filterLevel = filterLevel;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
