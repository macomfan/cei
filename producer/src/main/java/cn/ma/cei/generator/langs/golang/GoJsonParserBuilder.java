/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.golang;

import cn.ma.cei.generator.builder.JsonParserBuilder;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.langs.golang.tools.GoMethod;
import cn.ma.cei.generator.langs.golang.tools.GoType;
import cn.ma.cei.generator.langs.golang.tools.GoVar;

/**
 *
 * @author U0151316
 */
public class GoJsonParserBuilder extends JsonParserBuilder {

    private GoMethod method;

    public GoJsonParserBuilder(GoMethod method) {
        this.method = method;
    }

    @Override
    public void getJsonString(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(new GoVar(to)), method.invoke(jsonObject.getDescriptor() + ".GetString", new GoVar(itemName)));
    }

    @Override
    public void getJsonInteger(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(new GoVar(to)), method.invoke(jsonObject.getDescriptor() + ".GetInt", new GoVar(itemName)));
    }

    @Override
    public void getJsonLong(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(new GoVar(to)), method.invoke(jsonObject.getDescriptor() + ".GetInt64", new GoVar(itemName)));
    }

    @Override
    public void getJsonBoolean(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(new GoVar(to)), method.invoke(jsonObject.getDescriptor() + ".GetBool", new GoVar(itemName)));
    }

    @Override
    public void getJsonDecimal(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(new GoVar(to)), method.invoke(jsonObject.getDescriptor() + ".GetFloat64", new GoVar(itemName)));
    }

    @Override
    public void getJsonStringArray(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(new GoVar(to)), method.invoke(jsonObject.getDescriptor() + ".GetStringArray", new GoVar(itemName)));
    }

    @Override
    public void getJsonObject(Variable to, Variable parentModel, Variable jsonObject, Variable parentJsonObject, Variable itemName) {
        method.addAssign(method.useVariable(new GoVar(jsonObject)), method.invoke(parentJsonObject.getDescriptor() + ".GetObject", new GoVar(itemName)));
        if (to != null) {
            method.addAssign(method.useVariable(new GoVar(to)), method.useVariable(new GoVar(parentModel)));
        }
    }

    @Override
    public void startJsonObjectArrayLoop(Variable eachItemJsonObject, Variable parentJsonObject, Variable itemName) {
        method.startFor(new GoVar(eachItemJsonObject), method.invoke(parentJsonObject.getDescriptor() + ".getObjectArray", new GoVar(itemName)));
    }

    @Override
    public void endJsonObjectArrayLoop(Variable to, Variable model) {
        method.addAssign(method.useVariable(new GoVar(to)), method.invoke("append", new GoVar(to), new GoVar(model)));
        method.endFor();
    }

    @Override
    public void defineModel(Variable model) {
        method.addAssignAndDeclare(method.useVariable(new GoVar(model)), method.newInstance(new GoType(model.getType())));
    }

    @Override
    public void defineRootJsonObject(Variable jsonObject, Variable responseVariable) {
        Variable value = VariableFactory.createConstantVariable(responseVariable.getDescriptor() + ".GetJson()");
        method.addAssignAndDeclare(method.useVariable(new GoVar(jsonObject)), method.useVariable(new GoVar(value)));
    }

}