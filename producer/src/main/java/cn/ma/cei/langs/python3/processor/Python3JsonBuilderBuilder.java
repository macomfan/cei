/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.python3.processor;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IJsonBuilderBuilder;
import cn.ma.cei.langs.python3.tools.Python3Method;

/**
 *
 * @author u0151316
 */
public class Python3JsonBuilderBuilder implements IJsonBuilderBuilder {

    private final Python3Method method;

    public Python3JsonBuilderBuilder(Python3Method method) {
        this.method = method;
    }

    @Override
    public void defineRootJsonObject(Variable jsonObject) {
        method.addAssign(method.defineVariable(jsonObject), method.newInstance(jsonObject.getType()));
    }

    @Override
    public void defineJsonObject(Variable jsonObject) {

    }

    @Override
    public void addJsonString(Variable from, Variable jsonObject, Variable key) {
        method.addInvoke(jsonObject.getDescriptor() + ".add_json_string", key, from);
    }

    @Override
    public void addJsonDecimal(Variable value, Variable jsonObject, Variable key) {
        method.addInvoke(jsonObject.getDescriptor() + ".add_json_number", key, value);
    }

    @Override
    public void addJsonBoolean(Variable value, Variable jsonObject, Variable key) {
        method.addInvoke(jsonObject.getDescriptor() + ".add_json_boolean", key, value);
    }

    @Override
    public void addJsonInt(Variable value, Variable jsonObject, Variable key) {
        method.addInvoke(jsonObject.getDescriptor() + ".add_json_number", key, value);
    }

    @Override
    public void addJsonStringArray(Variable value, Variable jsonObject, Variable key) {

    }

    @Override
    public void addJsonIntegerArray(Variable value, Variable jsonObject, Variable key) {

    }

    @Override
    public void addJsonDecimalArray(Variable value, Variable jsonObject, Variable key) {

    }

    @Override
    public void addJsonBooleanArray(Variable value, Variable jsonObject, Variable key) {

    }

    @Override
    public void addJsonObject(Variable value, Variable jsonObject, Variable key) {

    }
}
