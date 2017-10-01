package com.dharmesh.twitterclient.network;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.api.BaseApi;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by dgohil on 9/26/17.
 */

public class TwitterClient extends OAuthBaseClient {
    public static final BaseApi REST_API_INSTANCE = TwitterApi.instance();
    public static final String REST_URL = "https://api.twitter.com/1.1";
    public static final String REST_CONSUMER_KEY = "9vPva6Az8QrZhozhhXB37FC0C";
    public static final String REST_CONSUMER_SECRET = "JFo7PyWUAFO9sHb0h1vGHdEw5hVwLlfaSRu2efVuf0m8OAJaRJ";
    public static final String REST_CALLBACK_URL = "x-oauthflow-twitter://vistrav.com";

    public TwitterClient(Context context) {
        super(context, REST_API_INSTANCE, REST_URL,
                REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }

    public void getHomeTimeline(long maxId, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        if (maxId > 0) {
            params.put("max_id", String.valueOf(maxId));
        }
        client.get(apiUrl, params, handler);
    }

    public void tweet(String status, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", status);
        client.post(apiUrl, params, handler);
    }

    public void getUser(String screenName, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("users/show.json");
        RequestParams params = new RequestParams();
        params.put("screen_name", screenName);
        client.get(apiUrl, params, handler);
    }
}