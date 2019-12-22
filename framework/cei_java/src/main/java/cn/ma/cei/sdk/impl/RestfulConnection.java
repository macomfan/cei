package cn.ma.cei.sdk.impl;

import javafx.util.Pair;
import okhttp3.*;

public class RestfulConnection {
    private static final OkHttpClient client_ = new OkHttpClient();

    public RestfulConnection() {

    }

    public RestfulResponse query(RestfulRequest restfulRequest) {
        try {
            Request request = null;
            Request.Builder builder = new Request.Builder();
            String url = restfulRequest.url + restfulRequest.target;
            builder.headers(buildHeaders(restfulRequest));
            builder.url(url);
            switch (restfulRequest.method) {
                case GET: {
                    builder.get();
                    break;
                }
                case POST:
                    break;
            }
            Response response = client_.newCall(builder.build()).execute();
            System.out.println(response.body().string());
            return new RestfulResponse();
        } catch (Exception e) {

        }
        return new RestfulResponse();
    }

    private Headers buildHeaders(RestfulRequest restfulRequest) {
        Headers.Builder builder = new Headers.Builder();
        for (Pair<String, String> item: restfulRequest.getHeaders()) {
            builder.add(item.getKey(), item.getValue());
        }
        return builder.build();
    }
}
