/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.golang.processor;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IJsonBuilderBuilder;
import cn.ma.cei.generator.buildin.JsonWrapper;
import cn.ma.cei.langs.golang.tools.GoMethod;
import cn.ma.cei.langs.golang.vars.GoVar;

/**
 *
 * @author U0151316
 */
public class GoJsonBuilderBuilder implements IJsonBuilderBuilder {
    
    private final GoMethod method;
    
    public GoJsonBuilderBuilder(GoMethod method) {
        this.method = method;
    }

    @Override
    public void defineJsonObject(Variable jsonObject) {
        method.addAssignAndDeclare(jsonObject.getDescriptor(), method.invoke("impl.NewJsonWrapper"));
    }

    @Override
    public void addJsonString(Variable from, Variable jsonObject, Variable key) {
        method.addInvoke(jsonObject.getDescriptor() + ".AddJsonString", method.var(key), method.var(from));
    }

    @Override
    public void addJsonDecimal(Variable value, Variable jsonObject, Variable key) {
        method.addInvoke(jsonObject.getDescriptor() + ".AddJsonFloat64", method.var(key), method.var(value));
    }


    @Override
    public void addJsonBoolean(Variable value, Variable jsonObject, Variable key) {
        method.addInvoke(jsonObject.getDescriptor() + ".AddJsonBool", method.var(key), method.var(value));
    }

    @Override
    public void addJsonInt(Variable value, Variable jsonObject, Variable key) {
        method.addInvoke(jsonObject.getDescriptor() + ".AddJsonInt64", method.var(key), method.var(value));
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
