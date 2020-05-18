package cn.ma.cei.langs.cpp;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IWebSocketClientBuilder;
import cn.ma.cei.generator.builder.IWebSocketInterfaceBuilder;
import cn.ma.cei.generator.buildin.WebSocketOptions;
import cn.ma.cei.langs.cpp.tools.CppClass;
import cn.ma.cei.langs.cpp.tools.CppExchangeFile;

public class CppWebSocketClientBuilder implements IWebSocketClientBuilder {
    private CppClass clientClass = null;

    public CppExchangeFile mainFile;

    public CppWebSocketClientBuilder(CppExchangeFile mainFile) {
        this.mainFile = mainFile;
    }

    @Override
    public void startClient(VariableType client, WebSocketOptions option, Variable connectionVariable, Variable optionVariable) {
        clientClass = new CppClass(client.getDescriptor());
    }

    @Override
    public IWebSocketInterfaceBuilder createWebSocketInterfaceBuilder() {
        return new CppWebSocketInterfaceBuilder(clientClass);
    }

    @Override
    public void endClient() {
        mainFile.addClass(clientClass);
    }
}
