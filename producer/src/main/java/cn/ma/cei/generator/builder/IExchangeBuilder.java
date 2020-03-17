package cn.ma.cei.generator.builder;

public interface IExchangeBuilder {
    /***
     * 
     * 
     * @param exchangeName 
     */
    void startExchange(String exchangeName);

    
    /***
     * To create the RestfulClientBuilder for each Restful Client.
     * 
     * @return The new RestfulClientBuilder.
     */
    IRestfulClientBuilder createRestfulClientBuilder();

    IWebSocketClientBuilder createWebSocketClientBuilder();
    
    IAuthenticationBuilder createAuthenticationBuilder();
    
    /***
     * To create the ModelBuilder for each model.
     * 
     * @return The new ModelBuilder.
     */
    IModelBuilder createModelBuilder();

    void endExchange();
}
