/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.service.processors;

import cn.ma.cei.finalizer.XMLDatabase;
import cn.ma.cei.service.WebSocketClient;
import cn.ma.cei.service.WebSocketMessage;
import cn.ma.cei.service.WebSocketMessageProcessor;
import cn.ma.cei.service.messages.InitMessage;
import cn.ma.cei.service.response.Exchanges;

/**
 *
 * @author u0151316
 */
public class InitProcessor extends WebSocketMessageProcessor {

    @Override
    public <T extends WebSocketMessage> void process(T message, WebSocketClient client) {
        InitMessage msg = (InitMessage)message;
        Exchanges ex = new Exchanges();
        ex.exchanges.addAll(XMLDatabase.getExchangeSet());
        CommonProcessor.response(client, msg.requestID, ex);
    }
}
