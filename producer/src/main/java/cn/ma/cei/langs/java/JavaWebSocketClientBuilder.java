package cn.ma.cei.langs.java;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IWebSocketClientBuilder;
import cn.ma.cei.generator.builder.IWebSocketInterfaceBuilder;
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
    public void startClient(VariableType client, WebSocketOptions option, Variable connectionVariable, Variable optionVariable) {
        clientClass = new JavaClass(client.getDescriptor());
        clientClass.addMemberVariable(client.getMember("option"));
        clientClass.addMemberVariable(client.getMember("connection"));

        JavaMethod defaultConstructor = new JavaMethod(clientClass, "###defaultConstructor");
        defaultConstructor.startConstructor("");
        {
            defaultConstructor.addAssign(defaultConstructor.useVariable(optionVariable), defaultConstructor.newInstance(optionVariable.getType()));
            Variable url = optionVariable.getMember("url");
            defaultConstructor.addAssign(url.getDescriptor(), BuilderContext.createStringConstant(option.url).getDescriptor());
            defaultConstructor.addAssign(defaultConstructor.useVariable(connectionVariable), defaultConstructor.newInstance(connectionVariable.getType(), optionVariable));
        }
        defaultConstructor.endMethod();
        clientClass.addMethod(defaultConstructor);

        JavaMethod optionConstructor = new JavaMethod(clientClass, "###optionConstructor");

        optionConstructor.startConstructor(WebSocketOptions.getType().getDescriptor() + " option");
        {
            optionConstructor.addInvoke("this");
            optionConstructor.addInvoke(optionVariable.getDescriptor() + ".setFrom", BuilderContext.createStatement("option"));
        }
        optionConstructor.endMethod();
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
