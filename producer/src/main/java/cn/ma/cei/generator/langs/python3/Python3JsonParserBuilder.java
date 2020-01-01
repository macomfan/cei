/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.python3;

import cn.ma.cei.generator.builder.JsonParserBuilder;
import cn.ma.cei.generator.buildin.TheArray;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.langs.python3.tools.Python3Method;

/**
 *
 * @author U0151316
 */
public class Python3JsonParserBuilder extends JsonParserBuilder {

    private final Python3Method method;

    public Python3JsonParserBuilder(Python3Method method) {
        this.method = method;
    }

    @Override
    public void getJsonString(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(to), method.invoke(jsonObject.nameDescriptor + ".get_string", itemName));
    }

    @Override
    public void getJsonInteger(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(to), method.invoke(jsonObject.nameDescriptor + ".get_integer", itemName));
    }

    @Override
    public void getJsonLong(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(to), method.invoke(jsonObject.nameDescriptor + ".get_long", itemName));
    }

    @Override
    public void getJsonBoolean(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(to), method.invoke(jsonObject.nameDescriptor + ".get_boolean", itemName));
    }

    @Override
    public void getJsonDecimal(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(to), method.invoke(jsonObject.nameDescriptor + ".get_decimal", itemName));
    }

    @Override
    public void getJsonStringArray(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(to), method.invoke(jsonObject.nameDescriptor + ".get_string_array", itemName));
    }

    @Override
    public void getJsonObject(Variable to, Variable parentModel, Variable jsonObject, Variable parentJsonObject, Variable itemName) {
        method.addAssign(method.defineVariable(jsonObject), method.invoke(parentJsonObject.nameDescriptor + ".get_object", itemName));
        if (to != null) {
            method.addAssign(method.useVariable(to), method.useVariable(parentModel));
        }
    }

    @Override
    public void startJsonObjectArrayLoop(Variable eachItemJsonObject, Variable parentJsonObject, Variable itemName) {
        method.startFor(eachItemJsonObject, parentJsonObject.nameDescriptor + ".get_items(" + itemName.nameDescriptor + "):");
    }

    @Override
    public void endJsonObjectArrayLoop(Variable to, Variable model) {
        method.startIf(to.nameDescriptor + " == None");
        method.addAssign(method.useVariable(to), method.newInstance(TheArray.getType()));
        method.endIf();
        method.addInvoke(to.nameDescriptor + ".append", model);
        method.endFor();
    }

    @Override
    public void defineModel(Variable model) {
        method.addAssign(method.defineVariable(model), method.newInstance(model.type));
    }

    @Override
    public void defineRootJsonObject(Variable jsonObject, Variable responseVariable) {
        Variable value = VariableFactory.createConstantVariable(responseVariable.nameDescriptor + ".get_json()");
        method.addAssign(method.defineVariable(jsonObject), method.useVariable(value));
    }

}
