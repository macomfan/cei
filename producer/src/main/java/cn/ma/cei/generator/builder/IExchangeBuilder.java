package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.VariableType;

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
    
    ISignatureBuilder createSignatureBuilder();
    
    /***
     * To create the ModelBuilder for each model.
     * 
     * @return The new ModelBuilder.
     */
    IModelBuilder createModelBuilder();

    void endExchange();
}
