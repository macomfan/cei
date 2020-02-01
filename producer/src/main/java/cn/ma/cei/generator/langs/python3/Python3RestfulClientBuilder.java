/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.python3;

import cn.ma.cei.generator.builder.RestfulClientBuilder;
import cn.ma.cei.generator.builder.RestfulInterfaceBuilder;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.langs.python3.tools.Python3Class;
import cn.ma.cei.generator.langs.python3.tools.Python3File;
import cn.ma.cei.generator.langs.python3.tools.Python3Method;

/**
 *
 * @author U0151316
 */
public class Python3RestfulClientBuilder extends RestfulClientBuilder {
    
    private Python3File mainFile;
    private Python3Class clientClass = null;
    
    public Python3RestfulClientBuilder(Python3File mainFile) {
        this.mainFile = mainFile;
    }

    @Override
    public void startClient(String clientDescriptor, RestfulOptions options) {
        clientClass = new Python3Class(clientDescriptor);

        Python3Method defaultConstructor = new Python3Method(clientClass);
        defaultConstructor.getCode().appendln("def __init__(self, options=None):");
        defaultConstructor.getCode().newBlock(() -> {
            defaultConstructor.getCode().appendWordsln("self.__options", "=", RestfulOptions.getType().getDescriptor() + "()");
            Variable url = VariableFactory.createHardcodeStringVariable(options.url);
            defaultConstructor.getCode().appendWordsln("self.__options.url", "=", url.getDescriptor());
            if (options.connectionTimeout != null) {
                defaultConstructor.getCode().appendWordsln("self__.options.connectionTimeout", "=", options.connectionTimeout.toString());
            }
            defaultConstructor.getCode().appendln("if options is not None:");
            defaultConstructor.getCode().newBlock(() -> defaultConstructor.getCode().appendln("self.__options.set_from(options)"));
        });
        clientClass.addReference(RestfulOptions.getType());
        clientClass.addMethod(defaultConstructor);
    }

    @Override
    public RestfulInterfaceBuilder getRestfulInterfaceBuilder() {
        return new Python3RestfulInterfaceBuilder(clientClass);
    }

    @Override
    public void endClient() {
        mainFile.addInnerClass(clientClass);
    }
    
}
