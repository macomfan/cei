package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.RestfulClientBuilder;
import cn.ma.cei.generator.environment.Naming;
import cn.ma.cei.model.xRestful;

public class BuildRestfulInterfaceClient {

    public static void build(xRestful client, RestfulClientBuilder builder) {
        if (builder == null) {
            throw new CEIException("[BuildRestfulInterfaceClient] RestfulClientBuilder is null");
        }
        
        builder.startClient(Naming.get().getClientDescriptor(client.clientName));

        client.interfaceList.forEach((restIf) -> {
            BuildRestfulInterface.build(restIf, builder.getRestfulInterfaceBuilder());
        });

        builder.endClient();
    }
}
