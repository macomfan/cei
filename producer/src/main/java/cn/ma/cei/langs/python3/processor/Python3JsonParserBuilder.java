/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.python3.processor;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IJsonCheckerBuilder;
import cn.ma.cei.generator.builder.IJsonParserBuilder;
import cn.ma.cei.generator.buildin.TheArray;
import cn.ma.cei.langs.python3.tools.Python3Method;

/**
 *
 * @author U0151316
 */
public class Python3JsonParserBuilder implements IJsonParserBuilder {

    private final Python3Method method;

    public Python3JsonParserBuilder(Python3Method method) {
        this.method = method;
    }

    @Override
    public void getJsonString(Variable value, Variable jsonObject, Variable key, boolean optional) {
        method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".get_string", key));
    }

    @Override
    public void getJsonInteger(Variable value, Variable jsonObject, Variable key, boolean optional) {
        method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".get_long", key));
    }

    @Override
    public void getJsonBoolean(Variable value, Variable jsonObject, Variable key, boolean optional) {
        method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".get_boolean", key));
    }

    @Override
    public void getJsonDecimal(Variable value, Variable jsonObject, Variable key, boolean optional) {
        method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".get_decimal", key));
    }

    @Override
    public void assignJsonStringArray(Variable value, Variable jsonObject, Variable key, boolean optional) {
        method.addAssign(method.useVariable(value), method.invoke(jsonObject.getDescriptor() + ".get_string_array", key));
    }

    @Override
    public void assignJsonDecimalArray(Variable value, Variable jsonObject, Variable key, boolean optional) {

    }

    @Override
    public void assignJsonBooleanArray(Variable value, Variable jsonObject, Variable key, boolean optional) {

    }

    @Override
    public void assignJsonIntArray(Variable value, Variable jsonObject, Variable key, boolean optional) {

    }

    @Override
    public void getJsonArray(Variable jsonWrapperObject, Variable jsonObject, Variable key) {

    }

    @Override
    public void defineJsonObject(Variable jsonObject, Variable parentJsonObject, Variable key, boolean optional) {
        method.addAssign(method.defineVariable(jsonObject), method.invoke(parentJsonObject.getDescriptor() + ".get_object", key));
    }

    @Override
    public void defineJsonArray(Variable jsonObject, Variable parentJsonObject, Variable key) {
        method.addAssign(method.defineVariable(jsonObject), method.invoke(parentJsonObject.getDescriptor() + ".get_array", key));
    }

    @Override
    public void assignModel(Variable to, Variable model) {
        if (to != null) {
            method.addAssign(method.useVariable(to), method.useVariable(model));
        }
    }

    @Override
    public void startJsonObjectArray(Variable eachItemJsonObject, Variable jsonObject) {
        method.startFor(eachItemJsonObject, jsonObject.getDescriptor() + ":");
    }

    @Override
    public void endJsonObjectArray(Variable value, Variable model) {
        method.startIf(value.getDescriptor() + " is None");
        method.addAssign(method.useVariable(value), method.newInstance(TheArray.getType()));
        method.endIf();
        method.addInvoke(value.getDescriptor() + ".append", model);
        method.endFor();
    }

    @Override
    public void defineModel(Variable model) {
        method.addAssign(method.defineVariable(model), method.newInstance(model.getType()));
    }

    @Override
    public void defineRootJsonObject(Variable jsonObject, Variable stringVariable) {
        Variable value = BuilderContext.createStatement(stringVariable.getDescriptor() + ".get_json()");
        method.addAssign(method.defineVariable(jsonObject), method.useVariable(value));
    }

    @Override
    public IJsonCheckerBuilder createJsonCheckerBuilder() {
        return new Python3JsonCheckerBuilder(method);
    }

}
