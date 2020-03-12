package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.langs.java.JavaWebSocketImplementationBuilder;
import cn.ma.cei.generator.sMethod;

public interface IWebSocketInterfaceBuilder extends IMethodBuilder {

    IWebSocketImplementationBuilder createImplementationBuilderForTrigger();

    IWebSocketImplementationBuilder createImplementationBuilderForResponse();

//    void setTriggerToAction(Variable action, sMethod trigger);
//
//    void setSendToAction(Variable action, sMethod send);

    void setupCallback(Variable callback);

    void send(Variable send);

    IWebSocketActionBuilder createWebSocketActionBuilder();

    IWebSocketImplementationBuilder createOnConnectBuilder();

    void setupOnConnect(sMethod onConnect);

    void connect(Variable url, Variable option);
}
