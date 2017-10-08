package com.dharmesh.twitterclient.network;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.api.BaseApi;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dgohil on 9/26/17.
 */

public class TwitterClient extends OAuthBaseClient {
    private static final BaseApi REST_API_INSTANCE = TwitterApi.instance();
    private static final String REST_URL = "https://api.twitter.com/1.1/";
    private static final String REST_CONSUMER_KEY = "9vPva6Az8QrZhozhhXB37FC0C";
    private static final String REST_CONSUMER_SECRET = "JFo7PyWUAFO9sHb0h1vGHdEw5hVwLlfaSRu2efVuf0m8OAJaRJ";
    private static final String REST_CALLBACK_URL = "x-oauthflow-twitter://vistrav.com";
    private static final String MENTION_RESOURCE = "statuses/mentions_timeline.json";
    private static final String HOME_RESOURCE = "statuses/home_timeline.json";
    private static final String USER_TIMELINE_RESOURCE = "statuses/user_timeline.json";
    private static final String FAVORITE_TWEETS_RESOURCE = "favorites/list.json";
    private static final String TAG = TwitterClient.class.getSimpleName();

    public enum TimelineType {
        FAVORITE,
        USER_TIMELINE,
        HOME_TIMELINE,
        MENTION
    }

    private static final Map<TimelineType, String> URL_BY_TYPE = new HashMap<>();

    static {
        URL_BY_TYPE.put(TimelineType.FAVORITE, FAVORITE_TWEETS_RESOURCE);
        URL_BY_TYPE.put(TimelineType.USER_TIMELINE, USER_TIMELINE_RESOURCE);
        URL_BY_TYPE.put(TimelineType.HOME_TIMELINE, HOME_RESOURCE);
        URL_BY_TYPE.put(TimelineType.MENTION, MENTION_RESOURCE);
    }

    public TwitterClient(Context context) {
        super(context, REST_API_INSTANCE, REST_URL,
                REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }


    public void getTimeline(String screenName, TimelineType type, long maxId, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl(URL_BY_TYPE.get(type));
        RequestParams params = new RequestParams();
        if (maxId > 0) {
            params.put("max_id", String.valueOf(maxId));
        }
        params.put("screen_name", screenName);
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

    public void getUsersProfile(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("account/verify_credentials.json");
        getClient().get(apiUrl, handler);
    }
}