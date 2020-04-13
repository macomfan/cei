package cn.ma.cei.langs.test;

import cn.ma.cei.generator.builder.*;

public class testExchangeBuilder implements IExchangeBuilder {
    @Override
    public void startExchange(String exchangeName) {

    }

    @Override
    public IRestfulClientBuilder createRestfulClientBuilder() {
        return null;
    }

    @Override
    public IWebSocketClientBuilder createWebSocketClientBuilder() {
        return null;
    }

    @Override
    public IAuthenticationBuilder createAuthenticationBuilder() {
        return null;
    }

    @Override
    public IModelBuilder createModelBuilder() {
        return null;
    }

    @Override
    public void endExchange() {

    }
}
