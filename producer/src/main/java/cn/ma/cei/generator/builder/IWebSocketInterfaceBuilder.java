package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.IMethod;
import cn.ma.cei.generator.Variable;

public interface IWebSocketInterfaceBuilder extends IMethodBuilder {

    IWebSocketNestedBuilder createOnConnectBuilder();

    IWebSocketNestedBuilder createOnCloseBuilder();

    IWebSocketEventBuilder createWebSocketEventBuilder();

    IDataProcessorBuilder createDataProcessorBuilder();

    void setupOnConnect(Variable connection, IMethod onConnect);

    void connect(Variable connection, Variable target);

    void setupOnClose(Variable connection, IMethod onClose);

    void close(Variable connection);
}
