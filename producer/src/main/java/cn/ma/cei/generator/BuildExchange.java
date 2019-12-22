package cn.ma.cei.generator;

import cn.ma.cei.generator.builder.ExchangeBuilder;
import cn.ma.cei.model.xModel;
import cn.ma.cei.model.xRestful;
import cn.ma.cei.model.xSDK;

public class BuildExchange {
    public static void build(xSDK sdk, ExchangeBuilder builder) {
        builder.startExchange(sdk.exchange);
        for (xModel model : sdk.modelList) {
            BuildModel.build(model, builder.getModelBuilder());
        }

        for (xRestful restful : sdk.restfulList) {
            BuildRestfulInterfaceClient.build(restful, builder.getRestfulClientBuilder());
        }

    }
}
