package cn.ma.cei.impl;

import cn.ma.cei.exception.CEIException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class RestfulRequest {

    private String url;
    private String target;
    private Method method;
    private RestfulOptions options;
    private byte[] requestBody_ = null;
    private final Map<String, String> queryString_ = new LinkedHashMap<>();
    private final Map<String, String> headers_ = new LinkedHashMap<>();

    public RestfulRequest(RestfulOptions options) {
        this.options = options;
        this.url = options.url;
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
        headers_.put(name, value);
    }

    public void addQueryString(String name, String value) {
        if (value == null) {
            return;
        }
        queryString_.put(name, value);
    }

    public String buildQueryString() {
        StringBuilder builder = new StringBuilder();
        queryString_.forEach((key, value) -> {
            if (!("").equals(builder.toString())) {
                builder.append('&');
            } else {
                builder.append('?');
            }
            builder.append(key);
            builder.append("=");
            builder.append(urlEncode(value));
        });
        return builder.toString();
    }

    public Map<String, String> getQueryString() {
        return queryString_;
    }

    public Map<String, String> getHeaders() {
        return headers_;
    }

    public void setPostBody(byte[] body) {
        requestBody_ = body;
    }

    public void setPostBody(String value) {
        requestBody_ = value.getBytes();
    }

    public byte[] getPostBody() {
        return requestBody_;
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
