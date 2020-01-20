package cn.ma.cei.service.messages;

public class ExchangeQueryMessage extends MessageBase<ExchangeQueryMessage.Param> {
    public static class Param {
        public String exchangeName;
    }
}
