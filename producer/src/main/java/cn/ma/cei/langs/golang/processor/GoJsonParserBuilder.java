/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.golang.processor;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IJsonCheckerBuilder;
import cn.ma.cei.generator.builder.IJsonParserBuilder;
import cn.ma.cei.generator.buildin.JsonWrapper;
import cn.ma.cei.langs.golang.tools.GoMethod;

/**
 * @author U0151316
 */
public class GoJsonParserBuilder implements IJsonParserBuilder {

    private final GoMethod method;

    public GoJsonParserBuilder(GoMethod method) {
        this.method = method;
    }

    @Override
    public void getJsonString(Variable value, Variable jsonObject, Variable key, boolean optional) {
        method.addAssign(method.useVariable(method.var(value)), method.invoke(jsonObject.getDescriptor() + ".GetString", method.var(key)));
    }

    @Override
    public void getJsonInteger(Variable value, Variable jsonObject, Variable key, boolean optional) {
        method.addAssign(method.useVariable(method.var(value)), method.invoke(jsonObject.getDescriptor() + ".GetInt64", method.var(key)));
    }

    @Override
    public void getJsonBoolean(Variable value, Variable jsonObject, Variable key, boolean optional) {
        method.addAssign(method.useVariable(method.var(value)), method.invoke(jsonObject.getDescriptor() + ".GetBool", method.var(key)));
    }

    @Override
    public void getJsonDecimal(Variable value, Variable jsonObject, Variable key, boolean optional) {
        method.addAssign(method.useVariable(method.var(value)), method.invoke(jsonObject.getDescriptor() + ".GetFloat64", method.var(key)));
    }

    @Override
    public void assignJsonStringArray(Variable value, Variable jsonObject, Variable key, boolean optional) {
        method.addAssign(method.useVariable(method.var(value)), method.invoke(jsonObject.getDescriptor() + ".GetStringArray", method.var(key)));
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
    public void getJsonArray(Variable jsonObject, Variable parentJsonObject, Variable key, boolean optional) {
        method.addAssignAndDeclare(method.useVariable(method.var(jsonObject)), method.invoke(parentJsonObject.getDescriptor() + ".GetArray", method.var(key)));
    }


    @Override
    public void getJsonObject(Variable jsonObject, Variable parentJsonObject, Variable key, boolean optional) {
        method.addAssignAndDeclare(method.useVariable(method.var(jsonObject)), method.invoke(parentJsonObject.getDescriptor() + ".GetObject", method.var(key)));
    }

    @Override
    public void assignModel(Variable to, Variable model) {
        if (to != null) {
            method.addAssign(method.useVariable(method.var(to)), method.useVariable(method.var(model)));
        }
    }

    @Override
    public void startJsonObjectArray(Variable eachItemJsonObject, Variable jsonObject) {
        method.startFor(method.var(eachItemJsonObject), method.invoke(jsonObject.getDescriptor() + ".Array"));
    }

    @Override
    public void endJsonObjectArray(Variable value, Variable model) {
        method.addAssign(method.useVariable(method.var(value)), method.invoke("append", method.var(value), method.var(model)));
        method.endFor();
    }

    @Override
    public void defineModel(Variable model) {
        method.addAssignAndDeclare(method.useVariable(method.var(model)), method.createInstance(model.getType()));
    }

    @Override
    public void defineRootJsonObject(Variable jsonObject, Variable stringVariable) {
        method.addReference(JsonWrapper.getType());
        method.addAssignAndDeclare(method.useVariable(method.var(jsonObject)), method.invoke("impl.ParseJsonFromString", method.var(stringVariable)));
    }

    @Override
    public IJsonCheckerBuilder createJsonCheckerBuilder() {
        return new GoJsonCheckerBuilder(method);
    }

}
