package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IWebSocketActionBuilder;
import cn.ma.cei.generator.builder.IWebSocketClientBuilder;
import cn.ma.cei.generator.builder.IWebSocketInterfaceBuilder;
import cn.ma.cei.generator.buildin.WebSocketConnection;
import cn.ma.cei.generator.buildin.WebSocketOptions;
import cn.ma.cei.generator.langs.java.tools.JavaClass;
import cn.ma.cei.generator.langs.java.tools.JavaMethod;
import cn.ma.cei.generator.sMethod;

public class JavaWebSocketClientBuilder implements IWebSocketClientBuilder {

    private final JavaClass mainClass;
    private JavaClass clientClass = null;

    public JavaWebSocketClientBuilder(JavaClass mainClass) {
        this.mainClass = mainClass;

    }

    @Override
    public void startClient(VariableType client, WebSocketOptions options) {
        clientClass = new JavaClass(client.getDescriptor(), WebSocketConnection.getType());
        JavaMethod defaultConstructor = new JavaMethod(clientClass);
        defaultConstructor.startConstructor("");
        {

        }
        defaultConstructor.endMethod();
        clientClass.addMethod(defaultConstructor);

        JavaMethod optionConstructor = new JavaMethod(clientClass);
        optionConstructor.startConstructor("");
        {

        }
        optionConstructor.endMethod();
        clientClass.addMethod(optionConstructor);
    }

    @Override
    public IWebSocketInterfaceBuilder createWebSocketInterfaceBuilder() {
        return new JavaWebSocketInterfaceBuilder(clientClass);
    }

//    @Override
//    public IWebSocketConnectionBuilder createWebSocketConnectionBuilder() {
//        return new JavaWebSocketConnectionBuilder(clientClass);
//    }

    @Override
    public void endClient() {
        mainClass.addInnerClass(clientClass);
    }
}
