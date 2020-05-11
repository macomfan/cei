package cei.impl;

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

    public String getString() {
        return text;
    }

    public void upgrade(String value) {
        this.text = value;
    }

    public byte[] getBytes() {
        return byteString.toByteArray();
    }
}
