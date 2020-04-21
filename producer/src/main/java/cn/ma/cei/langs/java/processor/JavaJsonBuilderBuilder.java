/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.java.processor;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IJsonBuilderBuilder;
import cn.ma.cei.generator.buildin.JsonWrapper;
import cn.ma.cei.langs.java.tools.JavaMethod;

/**
 * @author u0151316
 */
public class JavaJsonBuilderBuilder implements IJsonBuilderBuilder {

    private JavaMethod method;

    public JavaJsonBuilderBuilder(JavaMethod method) {
        this.method = method;
    }

    @Override
    public void defineRootJsonObject(Variable jsonObject) {
        method.addReference(JsonWrapper.getType());
        method.addAssign(method.defineVariable(jsonObject), method.newInstance(jsonObject.getType()));
    }

    @Override
    public void defineJsonObject(Variable jsonObject) {
        method.addAssign(method.defineVariable(jsonObject), method.newInstance(jsonObject.getType()));
    }

    @Override
    public void addJsonString(Variable from, Variable jsonObject, Variable key) {
        method.addInvoke(jsonObject.getDescriptor() + ".addJsonString", key, from);
    }

    @Override
    public void addJsonDecimal(Variable value, Variable jsonObject, Variable key) {
        method.addInvoke(jsonObject.getDescriptor() + ".addJsonNumber", key, value);
    }

    @Override
    public void addJsonBoolean(Variable value, Variable jsonObject, Variable key) {
        method.addInvoke(jsonObject.getDescriptor() + ".addJsonBoolean", key, value);
    }

    @Override
    public void addJsonInt(Variable value, Variable jsonObject, Variable key) {
        method.addInvoke(jsonObject.getDescriptor() + ".addJsonNumber", key, value);
    }

    @Override
    public void addJsonStringArray(Variable value, Variable jsonObject, Variable key) {
        method.addInvoke(jsonObject.getDescriptor() + ".addJsonStringArray", key, value);
    }

    @Override
    public void addJsonIntegerArray(Variable value, Variable jsonObject, Variable key) {
        method.addInvoke(jsonObject.getDescriptor() + ".addJsonNumberArray", key, value);
    }

    @Override
    public void addJsonDecimalArray(Variable value, Variable jsonObject, Variable key) {
        method.addInvoke(jsonObject.getDescriptor() + ".addJsonNumberArray", key, value);
    }

    @Override
    public void addJsonBooleanArray(Variable value, Variable jsonObject, Variable key) {
        method.addInvoke(jsonObject.getDescriptor() + ".addJsonBooleanArray", key, value);
    }

    @Override
    public void addJsonObject(Variable value, Variable jsonObject, Variable key) {
        method.addInvoke(jsonObject.getDescriptor() + ".addJsonObject", key, value);
    }
}
