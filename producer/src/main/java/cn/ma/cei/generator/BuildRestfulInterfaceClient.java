package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.RestfulClientBuilder;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.model.xRestful;

public class BuildRestfulInterfaceClient {

    public static void build(xRestful client, RestfulClientBuilder builder) {
        if (builder == null) {
            throw new CEIException("[BuildRestfulInterfaceClient] RestfulClientBuilder is null");
        }
        RestfulOptions options = new RestfulOptions();
        if (client.timeout != null) {
            options.connectionTimeout = client.timeout;
        }
        if (client.definition != null) {
            options.url = client.definition.url;
        }

        builder.startClient(GlobalContext.getCurrentModel().getDescriptor(), options);

        if (client.interfaceList != null) {
            client.interfaceList.forEach((restIf) -> {
                sMethod method = GlobalContext.getCurrentModel().createMethod(restIf.name);
                GlobalContext.setCurrentMethod(method);
                restIf.startBuilding();
                BuildRestfulInterface.build(restIf, builder.getRestfulInterfaceBuilder(method));
                restIf.endBuilding();
                GlobalContext.setCurrentMethod(null);
            });
    }
        builder.endClient();
    }
}
