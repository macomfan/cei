package cn.ma.cei.service.messages;


public class ExchangeInfoMessage extends MessageBase<ExchangeInfoMessage.Param> {

    public static class Param {
        public String exchangeName;
    }
}
