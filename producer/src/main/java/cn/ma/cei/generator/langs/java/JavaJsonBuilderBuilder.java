/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.builder.JsonBuilderBuilder;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.langs.java.tools.JavaMethod;

/**
 *
 * @author u0151316
 */
public class JavaJsonBuilderBuilder extends JsonBuilderBuilder {

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
    public void addJsonInteger(Variable from, Variable jsonObject, Variable itemName) {
        method.addInvoke(jsonObject.getDescriptor() + ".addJsonInteger", itemName, from);
    }

    @Override
    public void addJsonLong(Variable from, Variable jsonObject, Variable itemName) {
        method.addInvoke(jsonObject.getDescriptor() + ".addJsonLong", itemName, from);
    }

    @Override
    public void addJsonBoolean(Variable from, Variable jsonObject, Variable itemName) {
        method.addInvoke(jsonObject.getDescriptor() + ".addJsonBoolean", itemName, from);
    }

    @Override
    public void addJsonDecimal(Variable from, Variable jsonObject, Variable itemName) {
        method.addInvoke(jsonObject.getDescriptor() + ".addJsonDecimal", itemName, from);
    }
    
}
