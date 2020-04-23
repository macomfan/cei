package cn.ma.cei.generator.builder;

public interface IExchangeBuilder extends IBuilderBase {
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
    
    IMethodBuilder createFunctionBuilder();
    
    /***
     * To create the ModelBuilder for each model.
     * 
     * @return The new ModelBuilder.
     */
    IModelBuilder createModelBuilder();

    void endExchange();
}
