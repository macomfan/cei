package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.ExchangeBuilder;
import cn.ma.cei.generator.builder.ModelBuilder;
import cn.ma.cei.generator.builder.RestfulClientBuilder;
import cn.ma.cei.generator.builder.SignatureBuilder;
import cn.ma.cei.generator.buildin.RestfulOptions;
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

        if (sdk.modelList != null) {
            sdk.modelList.forEach((model) -> {
                ModelBuilder modelBuilder = builder.getModelBuilder();
                if (modelBuilder == null) {
                    throw new CEIException("[BuildExchange] ModelBuilder is null");
                }
                BuildModel.build(model, modelBuilder);
            });
        }

        if (sdk.restfulList != null) {
            sdk.restfulList.forEach((restful) -> {
                RestfulClientBuilder clientBuilder = builder.getRestfulClientBuilder();
                if (clientBuilder == null) {
                    throw new CEIException("[BuildExchange] RestfulClientBuilder is null");
                }
                BuildRestfulInterfaceClient.build(restful, builder.getRestfulClientBuilder());
            });
        }

        if (sdk.signatureList != null) {
            sdk.signatureList.forEach(signature -> {
                SignatureBuilder signatureBuilder = builder.getSignatureBuilder();
                if (signatureBuilder == null) {
                    throw new CEIException("[BuildExchange] SignatureBuilder is null");
                }
                BuildSignature.build(signature, signatureBuilder);
            });
        }

        builder.endExchange();
    }
}
