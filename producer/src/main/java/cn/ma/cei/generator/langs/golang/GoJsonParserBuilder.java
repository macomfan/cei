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
        method.addAssign(method.useVariable(to), method.invoke(jsonObject.nameDescriptor + ".GetString", itemName));
    }

    @Override
    public void getJsonInteger(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(to), method.invoke(jsonObject.nameDescriptor + ".GetInt", itemName));
    }

    @Override
    public void getJsonLong(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(to), method.invoke(jsonObject.nameDescriptor + ".GetUint64", itemName));
    }

    @Override
    public void getJsonBoolean(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(to), method.invoke(jsonObject.nameDescriptor + ".GetBool", itemName));
    }

    @Override
    public void getJsonDecimal(Variable to, Variable jsonObject, Variable itemName) {
        method.addAssign(method.useVariable(to), method.invoke(jsonObject.nameDescriptor + ".GetFloat64", itemName));
    }

    @Override
    public void getJsonStringArray(Variable to, Variable jsonObject, Variable itemName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void getJsonObject(Variable to, Variable parentModel, Variable jsonObject, Variable parentJsonObject, Variable itemName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void startJsonObjectArrayLoop(Variable eachItemJsonObject, Variable parentJsonObject, Variable itemName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void endJsonObjectArrayLoop(Variable to, Variable model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void defineModel(Variable model) {
        method.addAssignAndDeclare(method.useVariable(model), method.newInstance(model.type));
    }

    @Override
    public void defineRootJsonObject(Variable jsonObject, Variable responseVariable) {
        Variable value = VariableFactory.createConstantVariable(responseVariable.nameDescriptor + ".GetJson()");
        method.addAssignAndDeclare(method.useVariable(jsonObject), method.useVariable(value));
    }

}
