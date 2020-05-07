/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.python3;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.IMethod;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IRestfulClientBuilder;
import cn.ma.cei.generator.builder.IRestfulInterfaceBuilder;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.langs.python3.tools.Python3Class;
import cn.ma.cei.langs.python3.tools.Python3File;
import cn.ma.cei.langs.python3.tools.Python3Method;

/**
 *
 * @author U0151316
 */
public class Python3RestfulClientBuilder implements IRestfulClientBuilder {
    
    private final Python3File mainFile;
    private Python3Class clientClass = null;
    
    public Python3RestfulClientBuilder(Python3File mainFile) {
        this.mainFile = mainFile;
    }

    @Override
    public void startClient(VariableType client, RestfulOptions option, Variable optionVariable) {
        clientClass = new Python3Class(client.getDescriptor());
        clientClass.addMemberVariable(client.getMember("option"));

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
        }
        defaultConstructor.endMethod();
    }

    @Override
    public IRestfulInterfaceBuilder createRestfulInterfaceBuilder(IMethod method) {
        return new Python3RestfulInterfaceBuilder(clientClass);
    }

    @Override
    public void endClient() {
        mainFile.addInnerClass(clientClass);
    }
    
}
