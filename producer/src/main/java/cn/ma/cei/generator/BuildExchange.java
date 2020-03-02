package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.IExchangeBuilder;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.buildin.SignatureTool;
import cn.ma.cei.model.xSDK;

public class BuildExchange {

    public static void build(xSDK sdk, IExchangeBuilder builder) {
        if (builder == null) {
            throw new CEIException("[BuildExchange] ExchangeBuilder is null");
        }

        builder.startExchange(sdk.name);
        RestfulOptions.registryMember();

        if (sdk.modelList != null) {
            sdk.modelList.forEach((model) -> {
                model.startBuilding();
                BuildModel.build(model, builder.getModelBuilder());
                model.endBuilding();
            });
        }

        if (sdk.clients != null) {
            if (sdk.clients.restfulList != null) {
                sdk.clients.restfulList.forEach((restful) -> {
                    restful.startBuilding();
                    GlobalContext.setupRunTimeVariableType(restful.name, BuilderContext.NO_REF);
                    VariableType clientType = GlobalContext.variableType(restful.name);
                    GlobalContext.setCurrentModel(clientType);
                    BuildRestfulInterfaceClient.build(restful, builder.getRestfulClientBuilder(clientType));
                    restful.endBuilding();
                    GlobalContext.setCurrentModel(null);
                });
            }
        } else {

        }



        VariableType signatureType = GlobalContext.variableType(SignatureTool.typeName);
        if (sdk.signatureList != null) {
            GlobalContext.setCurrentModel(signatureType);
            sdk.signatureList.forEach(signature -> {
                signature.startBuilding();
                sMethod signatureMethod = signatureType.createMethod(signature.name);
                GlobalContext.setCurrentMethod(signatureMethod);
                BuildSignature.build(signature, builder.getSignatureBuilder());
                signature.endBuilding();
                GlobalContext.setCurrentMethod(null);
            });
            GlobalContext.setCurrentModel(null);
        }

        builder.endExchange();
    }
}
