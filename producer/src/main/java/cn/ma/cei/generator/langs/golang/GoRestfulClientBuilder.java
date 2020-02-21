/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.golang;

import cn.ma.cei.generator.*;
import cn.ma.cei.generator.builder.IRestfulClientBuilder;
import cn.ma.cei.generator.builder.IRestfulInterfaceBuilder;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.langs.golang.tools.GoFile;
import cn.ma.cei.generator.langs.golang.tools.GoMethod;
import cn.ma.cei.generator.langs.golang.tools.GoPtrVar;
import cn.ma.cei.generator.langs.golang.tools.GoStruct;

/**
 *
 * @author U0151316
 */
public class GoRestfulClientBuilder implements IRestfulClientBuilder {

    private GoFile clientFile;
    private GoStruct clientStruct;
    private VariableType clientType;

    public GoRestfulClientBuilder(VariableType clientType, GoFile clientFile) {
        this.clientFile = clientFile;
        this.clientType = clientType;
    }

    @Override
    public void startClient(String clientDescriptor, RestfulOptions options) {
        clientStruct = new GoStruct(clientDescriptor);
        clientStruct.addPrivateMember(new GoPtrVar(clientType.addMember(RestfulOptions.getType(), "options")));
        GoMethod constructor = new GoMethod(null);
        constructor.getCode().appendWordsln("func", "New" + clientDescriptor + "(options *" + RestfulOptions.getType().getDescriptor() + ")", "*" + clientDescriptor, "{");
        constructor.getCode().newBlock(() -> {
            constructor.getCode().appendWordsln("inst", ":=", "new(" + clientDescriptor + ")");
            Variable url = BuilderContext.createStringConstant(options.url);
            constructor.getCode().appendWordsln("if", "options", "!=", "nil", "{");
            constructor.getCode().newBlock(() -> {
                constructor.getCode().appendln("inst.options = options");
            });
            constructor.getCode().appendln("} else {");
            constructor.getCode().newBlock(() -> {
                constructor.getCode().appendWordsln("inst.options.URL", "=", url.getDescriptor());
                if (options.connectionTimeout != null) {
                    constructor.getCode().appendWordsln("inst.options.connectionTimeout", "=", options.connectionTimeout.toString());
                }
            });
            constructor.getCode().appendln("}");
            constructor.getCode().appendWordsln("return", "inst");
        });
        constructor.getCode().appendWordsln("}");
        clientStruct.addMethod(constructor);
    }

    @Override
    public IRestfulInterfaceBuilder createRestfulInterfaceBuilder(sMethod method) {
        return new GoRestfulInterfaceBuilder(clientStruct);
    }

    @Override
    public void endClient() {
        clientFile.addStruct(clientStruct);
    }

}
