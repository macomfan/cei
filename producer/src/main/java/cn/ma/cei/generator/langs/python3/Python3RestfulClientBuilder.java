/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.python3;

import cn.ma.cei.generator.builder.RestfulClientBuilder;
import cn.ma.cei.generator.builder.RestfulInterfaceBuilder;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.langs.python3.tools.Python3Class;
import cn.ma.cei.generator.langs.python3.tools.Python3File;

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
