package cn.ma.cei.impl;

import cn.ma.cei.exception.CEIException;
import okhttp3.*;

public class RestfulConnection {
    private static final OkHttpClient client = new OkHttpClient();

    public RestfulConnection() {

    }

    public static RestfulResponse query(RestfulRequest restfulRequest) {
        try {
            Request.Builder builder = new Request.Builder();
            String url = restfulRequest.getUrl() + restfulRequest.getTarget() + restfulRequest.buildQueryString();
            builder.headers(buildHeaders(restfulRequest));
            builder.url(url);
            switch (restfulRequest.getMethod()) {
                case GET:
                    builder.get();
                    break;
                case POST:
                    byte[] postBody = restfulRequest.getPostBody();
                    if (postBody == null) {
                        throw new CEIException("Post request body is null for: " + url);
                    }
                    builder.post(RequestBody.create(null, restfulRequest.getPostBody()));
                    break;
                case DELETE:
                    builder.delete(RequestBody.create(null, restfulRequest.getPostBody()));
                    break;
            }
            Response response = client.newCall(builder.build()).execute();
            //System.out.println(response.body().string());
            return new RestfulResponse(response);
        } catch (CEIException e) {
            throw e;
        } catch (Exception e) {
            throw new CEIException("Query error: " + e.getMessage());
        }
    }

    private static Headers buildHeaders(RestfulRequest restfulRequest) {
        Headers.Builder builder = new Headers.Builder();
        restfulRequest.getHeaders().forEach((item) -> {
            builder.add(item.getKey(), item.getValue());
        });
        return builder.build();
    }
}
