package cn.ma.cei.langs.java;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.Variable;
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
        clientClass = new JavaClass(client.getDescriptor());
        clientClass.addMemberVariable(JavaClass.AccessType.PRIVATE, client.getMember("option"));
        clientClass.addMemberVariable(JavaClass.AccessType.PRIVATE, client.getMember("connection"));

        JavaMethod defaultConstructor = new JavaMethod(clientClass);
        defaultConstructor.startConstructor("");
        {
            defaultConstructor.getCode().appendJavaLine("this.option", "=", "new", WebSocketOptions.getType().getDescriptor() + "()");
            Variable url = BuilderContext.createStringConstant(options.url);
            defaultConstructor.getCode().appendJavaLine("this.option.url", "=", url.getDescriptor());
            defaultConstructor.getCode().appendJavaLine("this.connection", "=", "new", WebSocketConnection.getType().getDescriptor() + "()");
        }
        defaultConstructor.endMethod();
        clientClass.addMethod(defaultConstructor);

        JavaMethod optionConstructor = new JavaMethod(clientClass);
        optionConstructor.startConstructor(WebSocketOptions.getType().getDescriptor() + " option");
        {
            optionConstructor.getCode().appendJavaLine("this()");
            optionConstructor.getCode().appendJavaLine("this.option.setFrom(option)");
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
