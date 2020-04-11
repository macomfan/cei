package cn.ma.cei.langs.python3;

import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IWebSocketClientBuilder;
import cn.ma.cei.generator.builder.IWebSocketInterfaceBuilder;
import cn.ma.cei.generator.buildin.WebSocketConnection;
import cn.ma.cei.generator.buildin.WebSocketOptions;
import cn.ma.cei.langs.python3.tools.Python3Class;
import cn.ma.cei.langs.python3.tools.Python3File;

public class Python3WebSocketClientBuilder implements IWebSocketClientBuilder {
    private Python3File mainFile;
    private Python3Class clientClass = null;

    public Python3WebSocketClientBuilder(Python3File mainFile) {
        this.mainFile = mainFile;
    }

    @Override
    public void startClient(VariableType client, WebSocketOptions options) {
        clientClass = new Python3Class(client.getDescriptor(), WebSocketConnection.getType());
        clientClass.addMemberVariable(client.getMember("option"));

//        JavaMethod defaultConstructor = new JavaMethod(clientClass);
//        defaultConstructor.startConstructor("");
//        {
//
//        }
//        defaultConstructor.endMethod();
//        clientClass.addMethod(defaultConstructor);
//
//        JavaMethod optionConstructor = new JavaMethod(clientClass);
//        optionConstructor.startConstructor(WebSocketOptions.getType().getDescriptor() + " options");
//        {
//
//        }
//        optionConstructor.endMethod();
//        clientClass.addReference(WebSocketOptions.getType());
//        clientClass.addMethod(optionConstructor);
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
