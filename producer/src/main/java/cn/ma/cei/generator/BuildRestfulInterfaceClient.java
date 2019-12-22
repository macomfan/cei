package cn.ma.cei.generator;

import cn.ma.cei.generator.builder.RestfulInterfaceBuilder;
import cn.ma.cei.generator.builder.RestfulClientBuilder;
import cn.ma.cei.generator.database.Naming;
import cn.ma.cei.model.xInterface;
import cn.ma.cei.model.xRestful;

public class BuildRestfulInterfaceClient {
    public static void build(xRestful client, RestfulClientBuilder builder) {
        builder.startClient(Naming.get().getClientDescriptor(client.clientName));

        for (xInterface restIf : client.interfaceList) {
            RestfulInterfaceBuilder restfulInterfaceBuilder = builder.getRestfulInterfaceBuilder();
            BuildRestfulInterface.build(restIf, restfulInterfaceBuilder);
        }

        builder.endClient();
    }
}
