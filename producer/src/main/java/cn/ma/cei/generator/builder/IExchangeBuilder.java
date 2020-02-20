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
    RestfulClientBuilder getRestfulClientBuilder(VariableType clientType);

    
    SignatureBuilder getSignatureBuilder();
    
    /***
     * To create the ModelBuilder for each model.
     * 
     * @return The new ModelBuilder.
     */
    ModelBuilder getModelBuilder();

    void endExchange();
}
