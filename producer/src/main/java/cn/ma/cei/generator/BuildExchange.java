package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.ExchangeBuilder;
import cn.ma.cei.generator.builder.ModelBuilder;
import cn.ma.cei.generator.builder.RestfulClientBuilder;
import cn.ma.cei.generator.builder.SignatureBuilder;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.model.xSDK;

public class BuildExchange {

    public static void build(xSDK sdk, ExchangeBuilder builder) {
        if (builder == null) {
            throw new CEIException("[BuildExchange] ExchangeBuilder is null");
        }

        builder.startExchange(sdk.name);
        RestfulOptions.registryMember();

        if (sdk.modelList != null) {
            sdk.modelList.forEach((model) -> {
                model.startBuilding();
                ModelBuilder modelBuilder = builder.getModelBuilder();
                if (modelBuilder == null) {
                    throw new CEIException("[BuildExchange] ModelBuilder is null");
                }
                BuildModel.build(model, modelBuilder);
                model.endBuilding();
            });
        }

        if (sdk.restfulList != null) {
            sdk.restfulList.forEach((restful) -> {
                restful.startBuilding();
                RestfulClientBuilder clientBuilder = builder.getRestfulClientBuilder();
                if (clientBuilder == null) {
                    throw new CEIException("[BuildExchange] RestfulClientBuilder is null");
                }
                BuildRestfulInterfaceClient.build(restful, builder.getRestfulClientBuilder());
                restful.endBuilding();
            });
        }

        if (sdk.signatureList != null) {
            sdk.signatureList.forEach(signature -> {
                signature.startBuilding();
                SignatureBuilder signatureBuilder = builder.getSignatureBuilder();
                if (signatureBuilder == null) {
                    throw new CEIException("[BuildExchange] SignatureBuilder is null");
                }
                BuildSignature.build(signature, signatureBuilder);
                signature.endBuilding();
            });
        }

        builder.endExchange();
    }
}
