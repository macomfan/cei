package cn.ma.cei.generator.builder;

public abstract class ExchangeBuilder {
    /***
     * 
     * 
     * @param exchangeName 
     */
    public abstract void startExchange(String exchangeName);

    public abstract RestfulClientBuilder getRestfulClientBuilder();

    public abstract ModelBuilder getModelBuilder();

    public abstract void endExchange();
}
