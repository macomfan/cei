/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.service.processors;

import cn.ma.cei.service.ProcessContext;
import cn.ma.cei.service.messages.IMessage;
import cn.ma.cei.service.messages.MessageWithParam;
import com.alibaba.fastjson.JSONObject;

/**
 * @param <T>
 * @author u0151316
 */
public abstract class ProcessorBase<T extends IMessage> implements IProcessor<T> {
    public final static String REQ = "req";
    public final static String SUB = "sub";

    public abstract String getType();

    public abstract String getCatalog();

    public abstract T newMessage();

    public void invoke(ProcessContext context, JSONObject json) {
        T msg = this.newMessage();
        ((MessageWithParam<?>)msg).fromJson(json);
        this.process(context, msg);
    }
}
