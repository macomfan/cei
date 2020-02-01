/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.service.messages;

import cn.ma.cei.service.ProcessContext;
import cn.ma.cei.service.processors.ProcessorBase;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author u0151316
 */
public abstract class MessageWithParam<T> implements IMessage {
    public T param = null;

    @Override
    public void fromJson(JSONObject json) {
        Class<T> tcls;
        try {
            ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            tcls = (Class<T>) actualTypeArguments[0];
        }catch (Exception e) {
            return;
        }
        param = (T)json.toJavaObject(tcls);
    }
}
