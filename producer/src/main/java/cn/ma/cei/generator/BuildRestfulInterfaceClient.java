package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.RestfulClientBuilder;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.environment.Environment;
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
        if (client.url != null) {
            options.url = client.url;
        }
        builder.startClient(Environment.getCurrentDescriptionConverter().getClientDescriptor(client.clientName), options);

        client.interfaceList.forEach((restIf) -> {
            BuildRestfulInterface.build(restIf, builder.getRestfulInterfaceBuilder());
        });

        builder.endClient();
    }
}
