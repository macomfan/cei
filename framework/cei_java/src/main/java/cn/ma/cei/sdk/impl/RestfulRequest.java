package cn.ma.cei.sdk.impl;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class RestfulRequest {
    public String url;
    public String target;
    public Method method;
    private byte[] requestBody_ = null;
    private List<Pair<String, String>> queryString_ = new LinkedList<>();
    private List<Pair<String, String>> headers_ = new LinkedList<>();

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

    public List<Pair<String, String>> getHeaders() {
        return headers_;
    }

    public void setRequestBody(byte[] body) {
        requestBody_ = body;
    }


    public enum Method {
        GET,
        POST,
        DELETE
    }
}
