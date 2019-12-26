package cn.ma.cei.sdk.impl;

import cn.ma.cei.sdk.exception.CEIException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class RestfulRequest {

    private String url;
    private String target;
    private Method method;
    private RestfulOption options;
    private byte[] requestBody_ = null;
    private List<Pair<String, String>> queryString_ = new LinkedList<>();
    private List<Pair<String, String>> headers_ = new LinkedList<>();

    public RestfulRequest(RestfulOption options) {
        this.options = options;
    }

    public String getUrl() {
        return url;
    }

    public String getTarget() {
        return target;
    }

    public Method getMethod() {
        return method;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void addHeader(String name, String value) {
        headers_.add(new Pair<>(name, value));
    }

    public void addQueryString(String name, String value) {
        if (value == null) {
            return;
        }
        queryString_.add(new Pair<>(name, value));
    }

    public void addQueryString(String name, Boolean value) {
        addQueryString(name, value.toString());
    }

    public String buildQueryString() {
        StringBuilder builder = new StringBuilder();
        queryString_.forEach((item) -> {
            if (!("").equals(builder.toString())) {
                builder.append('&');
            } else {
                builder.append('?');
            }
            builder.append(item.getKey());
            builder.append("=");
            builder.append(urlEncode(item.getValue()));
        });
        return builder.toString();
    }

    public List<Pair<String, String>> getHeaders() {
        return headers_;
    }

    public void setRequestBody(byte[] body) {
        requestBody_ = body;
    }

    private static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new CEIException("[URL] UTF-8 encoding not supported!");
        }
    }

    public enum Method {
        GET,
        POST,
        DELETE
    }
}
