package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.sMethod;

public interface IWebSocketInterfaceBuilder extends IMethodBuilder {

    IWebSocketImplementationBuilder createImplementationBuilderForTrigger();

    IWebSocketImplementationBuilder createImplementationBuilderForResponse();

    IWebSocketImplementationBuilder createOnConnectBuilder();

    void send(Variable send);

    IWebSocketActionBuilder createWebSocketActionBuilder();

    void setupOnConnect(sMethod onConnect);

    void connect(Variable url, Variable option);
}
