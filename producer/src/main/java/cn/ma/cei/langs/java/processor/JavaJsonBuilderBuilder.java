/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.java.processor;

import cn.ma.cei.generator.builder.IJsonBuilderBuilder;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.langs.java.tools.JavaMethod;

/**
 *
 * @author u0151316
 */
public class JavaJsonBuilderBuilder implements IJsonBuilderBuilder {

    private JavaMethod method;
    
    public JavaJsonBuilderBuilder(JavaMethod method) {
        this.method = method;
    }
    
    @Override
    public void defineRootJsonObject(Variable jsonObject) {
        method.addAssign(method.defineVariable(jsonObject), method.newInstance(jsonObject.getType()));
    }

    @Override
    public void addJsonString(Variable from, Variable jsonObject, Variable itemName) {
        method.addInvoke(jsonObject.getDescriptor() + ".addJsonString", itemName, from);
    }

    @Override
    public void addJsonNumber(Variable from, Variable jsonObject, Variable itemName) {
        method.addInvoke(jsonObject.getDescriptor() + ".addJsonNumber", itemName, from);
    }

    @Override
    public void addJsonBoolean(Variable from, Variable jsonObject, Variable itemName) {
        method.addInvoke(jsonObject.getDescriptor() + ".addJsonBoolean", itemName, from);
    }    
}
