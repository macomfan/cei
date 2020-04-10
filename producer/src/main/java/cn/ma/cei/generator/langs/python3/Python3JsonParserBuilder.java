/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.python3;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.builder.IJsonCheckerBuilder;
import cn.ma.cei.generator.builder.IJsonParserBuilder;
import cn.ma.cei.generator.buildin.JsonWrapper;
import cn.ma.cei.generator.buildin.TheArray;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.langs.python3.tools.Python3Method;
import io.vertx.core.parsetools.JsonParser;

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
    public void getJsonString(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(to), method.invoke(jsonObject.getDescriptor() + ".get_string", itemName));
    }

    @Override
    public void getJsonInteger(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(to), method.invoke(jsonObject.getDescriptor() + ".get_long", itemName));
    }

    @Override
    public void getJsonBoolean(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(to), method.invoke(jsonObject.getDescriptor() + ".get_boolean", itemName));
    }

    @Override
    public void getJsonDecimal(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(to), method.invoke(jsonObject.getDescriptor() + ".get_decimal", itemName));
    }

    @Override
    public void assignJsonStringArray(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(to), method.invoke(jsonObject.getDescriptor() + ".get_string_array", itemName));
    }

    @Override
    public void assignJsonDecimalArray(Variable to, Variable jsonObject, Variable itemName) {

    }

    @Override
    public void assignJsonBooleanArray(Variable to, Variable jsonObject, Variable itemName) {

    }

    @Override
    public void assignJsonIntArray(Variable to, Variable jsonObject, Variable itemName) {

    }

    @Override
    public void getJsonArray(Variable jsonWrapperObject, Variable jsonObject, Variable itemName) {

    }

    @Override
    public void defineJsonObject(Variable jsonObject, Variable parentJsonObject, Variable itemName) {
        method.addAssign(method.defineVariable(jsonObject), method.invoke(parentJsonObject.getDescriptor() + ".get_object", itemName));
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
    public void endJsonObjectArray(Variable to, Variable model) {
        method.startIf(to.getDescriptor() + " is None");
        method.addAssign(method.useVariable(to), method.newInstance(TheArray.getType()));
        method.endIf();
        method.addInvoke(to.getDescriptor() + ".append", model);
        method.endFor();
    }

    @Override
    public void defineModel(Variable model) {
        method.addAssign(method.defineVariable(model), method.newInstance(model.getType()));
    }

    @Override
    public void defineRootJsonObject(Variable jsonObject, Variable responseVariable) {
        Variable value = BuilderContext.createStatement(responseVariable.getDescriptor() + ".get_json()");
        method.addAssign(method.defineVariable(jsonObject), method.useVariable(value));
    }

    @Override
    public IJsonCheckerBuilder createJsonCheckerBuilder() {
        return new Python3JsonCheckerBuilder(method);
    }

}
