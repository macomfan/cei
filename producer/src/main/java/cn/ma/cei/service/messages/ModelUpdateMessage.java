package cn.ma.cei.service.messages;

import com.alibaba.fastjson.JSONObject;

public class ModelUpdateMessage extends MessageWithParam<ModelUpdateMessage.Param> {
    public static class Param {
        public String exchangeName;
        public JSONObject data;
    }
}
