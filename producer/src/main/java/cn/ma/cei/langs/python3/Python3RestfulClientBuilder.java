/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.python3;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IRestfulClientBuilder;
import cn.ma.cei.generator.builder.IRestfulInterfaceBuilder;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.langs.python3.tools.Python3Class;
import cn.ma.cei.langs.python3.tools.Python3File;
import cn.ma.cei.langs.python3.tools.Python3Method;
import cn.ma.cei.generator.sMethod;

/**
 *
 * @author U0151316
 */
public class Python3RestfulClientBuilder implements IRestfulClientBuilder {
    
    private Python3File mainFile;
    private Python3Class clientClass = null;
    
    public Python3RestfulClientBuilder(Python3File mainFile) {
        this.mainFile = mainFile;
    }

    @Override
    public void startClient(VariableType clientType, RestfulOptions options) {
        clientClass = new Python3Class(clientType.getDescriptor());
        Python3Method defaultConstructor = new Python3Method(clientClass);

        defaultConstructor.getCode().appendln("def __init__(self, option=None):");
        defaultConstructor.getCode().newBlock(() -> {
            clientClass.attachDefaultConstructor(defaultConstructor);
            defaultConstructor.getCode().appendWordsln("self.__option", "=", RestfulOptions.getType().getDescriptor() + "()");
            Variable url = BuilderContext.createStringConstant(options.url);
            defaultConstructor.getCode().appendWordsln("self.__option.url", "=", url.getDescriptor());
            if (options.connectionTimeout != null) {
                defaultConstructor.getCode().appendWordsln("self.__option.connectionTimeout", "=", options.connectionTimeout.toString());
            }
            defaultConstructor.getCode().appendln("if options is not None:");
            defaultConstructor.getCode().newBlock(() -> defaultConstructor.getCode().appendln("self.__options.set_from(option)"));
        });
        clientClass.addReference(RestfulOptions.getType());
    }

    @Override
    public IRestfulInterfaceBuilder createRestfulInterfaceBuilder(sMethod method) {
        return new Python3RestfulInterfaceBuilder(clientClass);
    }

    @Override
    public void endClient() {
        mainFile.addInnerClass(clientClass);
    }
    
}
