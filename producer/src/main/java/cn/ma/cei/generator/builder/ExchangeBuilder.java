package cn.ma.cei.generator.builder;

public abstract class ExchangeBuilder {
    /***
     * 
     * 
     * @param exchangeName 
     */
    public abstract void startExchange(String exchangeName);

    
    /***
     * To create the RestfulClientBuilder for each Restful Client.
     * 
     * @return The new RestfulClientBuilder.
     */
    public abstract RestfulClientBuilder getRestfulClientBuilder();

    
    public abstract SignatureBuilder getSignatureBuilder();
    
    /***
     * To create the ModelBuilder for each model.
     * 
     * @return The new ModelBuilder.
     */
    public abstract ModelBuilder getModelBuilder();

    public abstract void endExchange();
}
