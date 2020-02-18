package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.RestfulClientBuilder;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.environment.Environment;
import cn.ma.cei.generator.environment.VariableFactory;
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
        VariableFactory.registerModel(client.name, VariableFactory.NO_REF);
        builder.setClassType(VariableFactory.variableType(client.name));
        builder.startClient(Environment.getCurrentDescriptionConverter().getClientDescriptor(client.name), options);

        if (client.interfaceList != null) {
            client.interfaceList.forEach((restIf) -> {
                restIf.startBuilding();
                BuildRestfulInterface.build(restIf, builder.getRestfulInterfaceBuilder());
                restIf.endBuilding();
            });
        }


        builder.endClient();
    }
}
