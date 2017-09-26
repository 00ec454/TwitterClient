package com.dharmesh.twitterclient.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by dgohil on 9/25/17.
 */
/*
    "id": 2244994945,
    "id_str": "2244994945",
    "name": "TwitterDev",
    "screen_name": "TwitterDev",
    "location": "Internet",
    "url": "https://dev.twitter.com/",
    "description": "Your official source for Twitter Platform news, updates & events. Need technical help? Visit https://twittercommunity.com/ ⌨️ #TapIntoTwitter",
    "verified": true,
    "followers_count": 477684,
    "friends_count": 1524,
    "listed_count": 1184,
    "favourites_count": 2151,
    "statuses_count": 3121,
    "created_at": "Sat Dec 14 04:35:55 +0000 2013",
    "utc_offset": -25200,
    "time_zone": "Pacific Time (US & Canada)",
    "geo_enabled": true,
    "lang": "en",
    "profile_image_url_https": "https://pbs.twimg.com/profile_images/530814764687949824/npQQVkq8_normal.png"
  }
 */
public class User {

    private long id;
    @SerializedName("id_str")
    private String idStr;
    private String name;
    @SerializedName("screen_name")
    private String screenName;
    @SerializedName("location")
    private String location;
    @SerializedName("url")
    private String url;
    @SerializedName("description")
    private String description;
    @SerializedName("url")
    private String verified;
    @SerializedName("followers_count")
    private long followersCount;
    @SerializedName("listed_count")
    private long listedCount;
    @SerializedName("favourites_count")
    private long favouritesCount;
    @SerializedName("statuses_count")
    private long statusesCount;
    @SerializedName("created_at")
    private Date createdAt;
    @SerializedName("utc_offset")
    private long utcOffset;
    @SerializedName("time_zone")
    private String timeZone;
    @SerializedName("geo_enabled")
    private boolean geoEnabled;
    @SerializedName("lang")
    private String lang;
    @SerializedName("profile_image_url_https")
    private String profileImageUrl;
}
