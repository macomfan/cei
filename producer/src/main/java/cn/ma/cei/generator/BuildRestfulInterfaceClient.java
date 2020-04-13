package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.IRestfulClientBuilder;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.model.restful.xRestful;
import cn.ma.cei.utils.Checker;

public class BuildRestfulInterfaceClient {

    public static void build(xRestful client, IRestfulClientBuilder builder) {
        RestfulOptions options = new RestfulOptions();
        if (client.connection.timeout != null) {
            options.connectionTimeout = client.connection.timeout;
        }
        options.url = client.connection.url;

        builder.startClient(GlobalContext.getCurrentModel(), options);

        if (client.interfaceList != null) {
            client.interfaceList.forEach((restIf) -> restIf.doBuild(() -> {
                sMethod method = GlobalContext.getCurrentModel().createMethod(restIf.name);
                GlobalContext.setCurrentMethod(method);
                BuildRestfulInterface.build(restIf, Checker.checkBuilder(builder.createRestfulInterfaceBuilder(method), builder.getClass(), "RestfulInterfaceBuilder"));
                GlobalContext.setCurrentMethod(null);
            }));
        }
        builder.endClient();
    }
}
