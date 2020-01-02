package cn.ma.cei.impl;

import javafx.util.Pair;
import okhttp3.*;

public class RestfulConnection {
    private static final OkHttpClient client_ = new OkHttpClient();

    public RestfulConnection() {

    }

    public static RestfulResponse query(RestfulRequest restfulRequest) {
        try {
            Request.Builder builder = new Request.Builder();
            String url = restfulRequest.getUrl() + restfulRequest.getTarget() + restfulRequest.buildQueryString();
            builder.headers(buildHeaders(restfulRequest));
            builder.url(url);
            switch (restfulRequest.getMethod()) {
                case GET: {
                    builder.get();
                    break;
                }
                case POST:
                    builder.post(RequestBody.create(null, restfulRequest.getPostBody()));
                    break;
            }
            Response response = client_.newCall(builder.build()).execute();
            //System.out.println(response.body().string());
            return new RestfulResponse(response);
        } catch (Exception e) {

        }
        return null;
    }

    private static Headers buildHeaders(RestfulRequest restfulRequest) {
        Headers.Builder builder = new Headers.Builder();
        for (Pair<String, String> item: restfulRequest.getHeaders()) {
            builder.add(item.getKey(), item.getValue());
        }
        return builder.build();
    }
}
