package cn.ma.cei.service;

public abstract class WebSocketMessage {
    private static WebSocketMessageProcessor processor = null;


    public static void setProcessor(WebSocketMessageProcessor processor) {
        WebSocketMessage.processor = processor;
    }

    public WebSocketMessageProcessor getProcessor() {
        return processor;
    }
}
