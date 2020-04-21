package cn.ma.cei.impl;

import okio.ByteString;

public class WebSocketMessage {
    private String text;
    private ByteString byteString;

    public WebSocketMessage(String text) {
        this.text = text;
    }

    public WebSocketMessage(ByteString byteString) {
        this.byteString = byteString;
    }

    public JsonWrapper getJson() {
        return JsonWrapper.parseFromString(text);
    }
}
