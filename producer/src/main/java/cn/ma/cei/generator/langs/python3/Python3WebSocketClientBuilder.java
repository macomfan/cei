package cn.ma.cei.generator.langs.python3;

import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IWebSocketClientBuilder;
import cn.ma.cei.generator.builder.IWebSocketInterfaceBuilder;
import cn.ma.cei.generator.buildin.WebSocketOptions;
import cn.ma.cei.generator.langs.python3.tools.Python3Class;
import cn.ma.cei.generator.langs.python3.tools.Python3File;

public class Python3WebSocketClientBuilder implements IWebSocketClientBuilder {
    private Python3File mainFile;
    private Python3Class clientClass = null;

    public Python3WebSocketClientBuilder(Python3File mainFile) {
        this.mainFile = mainFile;
    }

    @Override
    public void startClient(VariableType client, WebSocketOptions options) {

    }

    @Override
    public IWebSocketInterfaceBuilder createWebSocketInterfaceBuilder() {
        return null;
    }

    @Override
    public void endClient() {

    }
}
