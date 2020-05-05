package cn.ma.cei.langs.golang;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IWebSocketClientBuilder;
import cn.ma.cei.generator.builder.IWebSocketInterfaceBuilder;
import cn.ma.cei.generator.buildin.WebSocketOptions;
import cn.ma.cei.langs.golang.tools.GoFile;
import cn.ma.cei.langs.golang.tools.GoStruct;

public class GoWebSocketClientBuilder implements IWebSocketClientBuilder {

    private final GoFile clientFile;
    private GoStruct clientStruct;

    public GoWebSocketClientBuilder(GoFile clientFile) {
        this.clientFile = clientFile;
    }

    @Override
    public void startClient(VariableType client, WebSocketOptions option, Variable connectionVariable, Variable optionVariable) {
        clientStruct = new GoStruct(client.getDescriptor());
    }

    @Override
    public IWebSocketInterfaceBuilder createWebSocketInterfaceBuilder() {
        return new GoWebSocketInterfaceBuilder(clientStruct);
    }

    @Override
    public void endClient() {

    }
}
