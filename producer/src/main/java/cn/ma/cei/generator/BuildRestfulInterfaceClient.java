package cn.ma.cei.generator;

import cn.ma.cei.generator.builder.IRestfulClientBuilder;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.model.restful.xRestful;
import cn.ma.cei.utils.Checker;

public class BuildRestfulInterfaceClient {

    public static void build(xRestful client, IRestfulClientBuilder builder) {
        RestfulOptions option = new RestfulOptions();
        if (client.connection.timeout != null) {
            option.connectionTimeout = client.connection.timeout;
        }
        option.url = client.connection.url;

        VariableType clientModel = GlobalContext.getCurrentModel();
        clientModel.addPrivateMember(RestfulOptions.getType(), "option");

        // The defaultConstructor is used for lookup the option variable only.
        sMethod defaultConstructor = clientModel.createMethod(clientModel.getName() + "DefaultConstructor");
        builder.startClient(GlobalContext.getCurrentModel(), option, defaultConstructor.getVariable("option"));
        if (!Checker.isNull(client.interfaceList)) {
            client.interfaceList.forEach((restIf) -> restIf.doBuild(() -> {
                sMethod method = GlobalContext.getCurrentModel().createMethod(restIf.name);
                GlobalContext.setCurrentMethod(method);
                BuildRestfulInterface.build(restIf, Checker.checkNull(builder.createRestfulInterfaceBuilder(method), builder, "RestfulInterfaceBuilder"));
                GlobalContext.setCurrentMethod(null);
            }));
        }
        builder.endClient();
    }
}
