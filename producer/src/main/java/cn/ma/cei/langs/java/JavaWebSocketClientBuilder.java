package cn.ma.cei.langs.java;

import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IWebSocketClientBuilder;
import cn.ma.cei.generator.builder.IWebSocketInterfaceBuilder;
import cn.ma.cei.generator.buildin.WebSocketConnection;
import cn.ma.cei.generator.buildin.WebSocketOptions;
import cn.ma.cei.langs.java.tools.JavaClass;
import cn.ma.cei.langs.java.tools.JavaMethod;

public class JavaWebSocketClientBuilder implements IWebSocketClientBuilder {

    private final JavaClass mainClass;
    private JavaClass clientClass = null;

    public JavaWebSocketClientBuilder(JavaClass mainClass) {
        this.mainClass = mainClass;

    }

    @Override
    public void startClient(VariableType client, WebSocketOptions options) {
        clientClass = new JavaClass(client.getDescriptor(), WebSocketConnection.getType());
        clientClass.addMemberVariable(JavaClass.AccessType.PRIVATE, client.addPrivateMember(WebSocketOptions.getType(), "option"));

        JavaMethod defaultConstructor = new JavaMethod(clientClass);
        defaultConstructor.startConstructor("");
        {

        }
        defaultConstructor.endMethod();
        clientClass.addMethod(defaultConstructor);

        JavaMethod optionConstructor = new JavaMethod(clientClass);
        optionConstructor.startConstructor(WebSocketOptions.getType().getDescriptor() + " options");
        {

        }
        optionConstructor.endMethod();
        clientClass.addReference(WebSocketOptions.getType());
        clientClass.addMethod(optionConstructor);
    }

    @Override
    public IWebSocketInterfaceBuilder createWebSocketInterfaceBuilder() {
        return new JavaWebSocketInterfaceBuilder(clientClass);
    }

    @Override
    public void endClient() {
        mainClass.addInnerClass(clientClass);
    }
}
