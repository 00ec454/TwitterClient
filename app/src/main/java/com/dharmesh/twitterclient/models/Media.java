package com.dharmesh.twitterclient.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dgohil on 9/30/17.
 */

public class Media {
    @SerializedName("media_url")
    private String mediaUrl;
    private String type;

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
