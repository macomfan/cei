package cn.ma.cei.service.messages;

import com.alibaba.fastjson.JSONObject;

public interface IMessage {
    void fromJson(JSONObject json);
}
