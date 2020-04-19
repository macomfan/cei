package cn.ma.cei.service;


import io.vertx.core.buffer.Buffer;

public interface IWebSocketHandler {
    WebSocketService.MessageResult handle(String text, Buffer buffer);
}
