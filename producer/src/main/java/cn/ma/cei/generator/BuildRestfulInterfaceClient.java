package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.IRestfulClientBuilder;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.model.restful.xRestful;

public class BuildRestfulInterfaceClient {

    public static void build(xRestful client, IRestfulClientBuilder builder) {
        if (builder == null) {
            throw new CEIException("[BuildRestfulInterfaceClient] RestfulClientBuilder is null");
        }
        RestfulOptions options = new RestfulOptions();
        if (client.timeout != null) {
            options.connectionTimeout = client.timeout;
        }
        if (client.connection != null) {
            options.url = client.connection.url;
        }

        builder.startClient(GlobalContext.getCurrentModel().getDescriptor(), options);

        if (client.interfaceList != null) {
            client.interfaceList.forEach((restIf) -> {
                sMethod method = GlobalContext.getCurrentModel().createMethod(restIf.name);
                GlobalContext.setCurrentMethod(method);
                restIf.startBuilding();
                BuildRestfulInterface.build(restIf, builder.createRestfulInterfaceBuilder(method));
                restIf.endBuilding();
                GlobalContext.setCurrentMethod(null);
            });
    }
        builder.endClient();
    }
}
