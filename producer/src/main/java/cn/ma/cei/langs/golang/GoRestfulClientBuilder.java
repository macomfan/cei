/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.golang;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.IMethod;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IRestfulClientBuilder;
import cn.ma.cei.generator.builder.IRestfulInterfaceBuilder;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.langs.golang.tools.GoFile;
import cn.ma.cei.langs.golang.tools.GoMethod;
import cn.ma.cei.langs.golang.tools.GoStruct;

/**
 * @author U0151316
 */
public class GoRestfulClientBuilder implements IRestfulClientBuilder {

    private final GoFile clientFile;
    private GoStruct clientStruct;

    public GoRestfulClientBuilder(GoFile clientFile) {
        this.clientFile = clientFile;
    }

    @Override
    public void startClient(VariableType client, RestfulOptions option, Variable optionVariable) {
        clientStruct = new GoStruct(client.getDescriptor());
        clientStruct.addPrivateMember(clientStruct.varPtr(client.getMember("option")));
        GoMethod constructor = new GoMethod(null);
        constructor.getCode().appendWordsln("func", "New" + client.getDescriptor() + "(option *" + RestfulOptions.getType().getDescriptor() + ")", "*" + client.getDescriptor(), "{");
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
    public IRestfulInterfaceBuilder createRestfulInterfaceBuilder(IMethod method) {
        return new GoRestfulInterfaceBuilder(clientStruct);
    }

    @Override
    public void endClient() {
        clientFile.addStruct(clientStruct);
    }

}
