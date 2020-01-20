package cn.ma.cei.service.messages;

public class ExchangeQueryMessage extends MessageWithParam<ExchangeQueryMessage.Param> {
    public static class Param {
        public String exchangeName;
    }
}
