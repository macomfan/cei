package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.ExchangeBuilder;
import cn.ma.cei.generator.builder.ModelBuilder;
import cn.ma.cei.generator.builder.RestfulClientBuilder;
import cn.ma.cei.generator.buildin.RestfulOption;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.model.xSDK;

public class BuildExchange {

    public static void build(xSDK sdk, ExchangeBuilder builder) {
        if (builder == null) {
            throw new CEIException("[BuildExchange] ExchangeBuilder is null");
        }

        
        
        builder.startExchange(sdk.exchange);

        sdk.modelList.forEach((model) -> {
            ModelBuilder modelBuilder = builder.getModelBuilder();
            if (modelBuilder == null) {
                throw new CEIException("[BuildExchange] ModelBuilder is null");
            }
            BuildModel.build(model, modelBuilder);
        });

        sdk.restfulList.forEach((restful) -> {
            RestfulClientBuilder clientBuilder = builder.getRestfulClientBuilder();
            if (clientBuilder == null) {
                throw new CEIException("[BuildExchange] RestfulClientBuilder is null");
            }
            BuildRestfulInterfaceClient.build(restful, builder.getRestfulClientBuilder());
        });
    }
}
