/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.golang.processor;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IJsonCheckerBuilder;
import cn.ma.cei.generator.builder.IJsonParserBuilder;
import cn.ma.cei.langs.golang.tools.GoGetValueVar;
import cn.ma.cei.langs.golang.tools.GoMethod;
import cn.ma.cei.langs.golang.tools.GoType;
import cn.ma.cei.langs.golang.tools.GoVar;

/**
 *
 * @author U0151316
 */
public class GoJsonParserBuilder implements IJsonParserBuilder {

    private final GoMethod method;

    public GoJsonParserBuilder(GoMethod method) {
        this.method = method;
    }

    @Override
    public void getJsonString(Variable value, Variable jsonObject, Variable key, boolean optional) {
        method.addAssign(method.useVariable(new GoVar(value)), method.invoke(jsonObject.getDescriptor() + ".GetString", new GoVar(key)));
    }

    @Override
    public void getJsonInteger(Variable value, Variable jsonObject, Variable key, boolean optional) {
        method.addAssign(method.useVariable(new GoVar(value)), method.invoke(jsonObject.getDescriptor() + ".GetInt64", new GoVar(key)));
    }

    @Override
    public void getJsonBoolean(Variable value, Variable jsonObject, Variable key, boolean optional) {
        method.addAssign(method.useVariable(new GoVar(value)), method.invoke(jsonObject.getDescriptor() + ".GetBool", new GoVar(key)));
    }

    @Override
    public void getJsonDecimal(Variable value, Variable jsonObject, Variable key, boolean optional) {
        method.addAssign(method.useVariable(new GoVar(value)), method.invoke(jsonObject.getDescriptor() + ".GetFloat64", new GoVar(key)));
    }

    @Override
    public void assignJsonStringArray(Variable value, Variable jsonObject, Variable key, boolean optional) {
        method.addAssign(method.useVariable(new GoVar(value)), method.invoke(jsonObject.getDescriptor() + ".GetStringArray", new GoVar(key)));
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
        method.addAssignAndDeclare(method.useVariable(new GoVar(parentJsonObject)), method.invoke(parentJsonObject.getDescriptor() + ".GetArray", new GoVar(key)));
    }



    @Override
    public void getJsonObject(Variable jsonObject, Variable parentJsonObject, Variable key, boolean optional) {
        method.addAssignAndDeclare(method.useVariable(new GoVar(jsonObject)), method.invoke(parentJsonObject.getDescriptor() + ".GetObject", new GoVar(key)));
    }

    @Override
    public void assignModel(Variable to, Variable model) {
        if (to != null) {
            method.addAssign(method.useVariable(new GoVar(to)), method.useVariable(new GoVar(model)));
        }
    }

    @Override
    public void startJsonObjectArray(Variable eachItemJsonObject, Variable jsonObject) {
        method.startFor(new GoVar(eachItemJsonObject), method.invoke(jsonObject.getDescriptor()));
    }

    @Override
    public void endJsonObjectArray(Variable value, Variable model) {
        method.addAssign(method.useVariable(new GoVar(value)), method.invoke("append", new GoVar(value), new GoGetValueVar(model)));
        method.endFor();
    }

    @Override
    public void defineModel(Variable model) {
        method.addAssignAndDeclare(method.useVariable(new GoVar(model)), method.newInstance(new GoType(model.getType())));
    }

    @Override
    public void defineRootJsonObject(Variable jsonObject, Variable stringVariable) {
        Variable value = BuilderContext.createStatement(stringVariable.getDescriptor() + ".GetJson()");
        method.addAssignAndDeclare(method.useVariable(new GoVar(jsonObject)), method.useVariable(new GoVar(value)));
    }

    @Override
    public IJsonCheckerBuilder createJsonCheckerBuilder() {
        return new GoJsonCheckerBuilder();
    }

}
