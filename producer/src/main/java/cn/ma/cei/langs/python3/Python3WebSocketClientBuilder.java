package cn.ma.cei.langs.python3;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.IMethod;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IWebSocketClientBuilder;
import cn.ma.cei.generator.builder.IWebSocketInterfaceBuilder;
import cn.ma.cei.generator.buildin.WebSocketConnection;
import cn.ma.cei.generator.buildin.WebSocketOptions;
import cn.ma.cei.langs.python3.tools.Python3Class;
import cn.ma.cei.langs.python3.tools.Python3File;
import cn.ma.cei.langs.python3.tools.Python3Method;

public class Python3WebSocketClientBuilder implements IWebSocketClientBuilder {
    private final Python3File mainFile;
    private Python3Class clientClass = null;

    public Python3WebSocketClientBuilder(Python3File mainFile) {
        this.mainFile = mainFile;
    }

    @Override
    public void startClient(VariableType client, WebSocketOptions option, Variable connectionVariable, Variable optionVariable) {
        clientClass = new Python3Class(client.getDescriptor());
        clientClass.addMemberVariable(client.getMember("option"));
        clientClass.addMemberVariable(client.getMember("connection"));

        Python3Method defaultConstructor = new Python3Method(clientClass);
        clientClass.attachDefaultConstructor(defaultConstructor);
        defaultConstructor.startConstructor("self, option=None");
        {
            defaultConstructor.addAssign(optionVariable.getDescriptor(), defaultConstructor.newInstance(optionVariable.getType()));
            Variable url = optionVariable.getMember("url");
            defaultConstructor.addAssign(defaultConstructor.useVariable(url), BuilderContext.createStringConstant(option.url).getDescriptor());
            defaultConstructor.getCode().appendln("if option is not None:");
            defaultConstructor.getCode().newBlock(() -> {
                defaultConstructor.addInvoke(optionVariable.getDescriptor() + ".set_from", BuilderContext.createStatement("option"));
            });
            defaultConstructor.addAssign(connectionVariable.getDescriptor(), defaultConstructor.newInstance(connectionVariable.getType(), optionVariable));
        }
        defaultConstructor.endMethod();
    }

    @Override
    public IWebSocketInterfaceBuilder createWebSocketInterfaceBuilder() {
        return new Python3WebSocketInterfaceBuilder(clientClass);
    }

    @Override
    public void endClient() {
        mainFile.addInnerClass(clientClass);
    }
}
