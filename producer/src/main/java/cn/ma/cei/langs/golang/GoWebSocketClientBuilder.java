package cn.ma.cei.langs.golang;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IWebSocketClientBuilder;
import cn.ma.cei.generator.builder.IWebSocketInterfaceBuilder;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.buildin.WebSocketOptions;
import cn.ma.cei.langs.golang.tools.GoFile;
import cn.ma.cei.langs.golang.tools.GoMethod;
import cn.ma.cei.langs.golang.tools.GoStruct;
import cn.ma.cei.langs.java.tools.JavaClass;

public class GoWebSocketClientBuilder implements IWebSocketClientBuilder {

    private final GoFile clientFile;
    private GoStruct clientStruct;

    public GoWebSocketClientBuilder(GoFile clientFile) {
        this.clientFile = clientFile;
    }

    @Override
    public void startClient(VariableType client, WebSocketOptions option, Variable connectionVariable, Variable optionVariable) {
        clientStruct = new GoStruct(client.getDescriptor());
        clientStruct.addPrivateMember(clientStruct.varPtr(client.getMember("option")));
        clientStruct.addPrivateMember(clientStruct.varPtr(client.getMember("connection")));

        GoMethod constructor = new GoMethod(clientStruct);
        constructor.getCode().appendWordsln("func", "New" + client.getDescriptor() + "(option *" + WebSocketOptions.getType().getDescriptor() + ")", "*" + client.getDescriptor(), "{");
        constructor.getCode().newBlock(() -> {
            constructor.getCode().appendWordsln("inst", ":=", "new(" + client.getDescriptor() + ")");
            Variable url = BuilderContext.createStringConstant(option.url);
            constructor.getCode().appendWordsln("if", "option", "!=", "nil", "{");
            constructor.getCode().newBlock(() -> {
                constructor.getCode().appendln("inst.option = option");
            });
            constructor.getCode().appendln("} else {");
            constructor.getCode().newBlock(() -> {
                constructor.getCode().appendWordsln("inst.option.Url", "=", url.getDescriptor());
                if (option.connectionTimeout != null) {
                    constructor.getCode().appendWordsln("inst.option.connectionTimeout", "=", option.connectionTimeout.toString());
                }
            });
            constructor.getCode().appendln("}");
            constructor.getCode().appendWordsln("return", "inst");
        });
        constructor.getCode().appendWordsln("}");
        clientStruct.addMethod(constructor);
    }

    @Override
    public IWebSocketInterfaceBuilder createWebSocketInterfaceBuilder() {
        return new GoWebSocketInterfaceBuilder(clientStruct);
    }

    @Override
    public void endClient() {
        clientFile.addStruct(clientStruct);
    }
}
