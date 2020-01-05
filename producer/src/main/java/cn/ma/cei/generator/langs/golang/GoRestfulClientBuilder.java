/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.golang;

import cn.ma.cei.generator.builder.RestfulClientBuilder;
import cn.ma.cei.generator.builder.RestfulInterfaceBuilder;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.langs.golang.tools.GoFile;
import cn.ma.cei.generator.langs.golang.tools.GoStruct;

/**
 *
 * @author U0151316
 */
public class GoRestfulClientBuilder extends RestfulClientBuilder {

    private GoFile clinetFile;
    private GoStruct clinetStruct;
    
    public GoRestfulClientBuilder(GoFile clientFile) {
        this.clinetFile = clientFile;
    }
    
    @Override
    public void startClient(String clientDescriptor, RestfulOptions options) {
        clinetStruct = new GoStruct(clientDescriptor);
        // TODO
        // Add new instance method
        
    }

    @Override
    public RestfulInterfaceBuilder getRestfulInterfaceBuilder() {
        return new GoRestfulInterfaceBuilder(clinetStruct);
    }

    @Override
    public void endClient() {
        clinetFile.addStruct(clinetStruct);
    }
    
}
