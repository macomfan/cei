/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.golang.processor;

import cn.ma.cei.generator.builder.IJsonBuilderBuilder;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.langs.golang.tools.GoMethod;
import cn.ma.cei.langs.golang.tools.GoType;
import cn.ma.cei.langs.golang.tools.GoVar;

/**
 *
 * @author U0151316
 */
public class GoJsonBuilderBuilder implements IJsonBuilderBuilder {
    
    private GoMethod method;
    
    public GoJsonBuilderBuilder(GoMethod method) {
        this.method = method;
    }

    @Override
    public void defineRootJsonObject(Variable jsonObject) {
        method.addAssignAndDeclare(method.useVariable(new GoVar(jsonObject)), method.newInstance(new GoType(jsonObject.getType())));
    }

    @Override
    public void addJsonString(Variable from, Variable jsonObject, Variable itemName) {
        method.addInvoke(jsonObject.getDescriptor() + ".AddJsonString", new GoVar(itemName), new GoVar(from));
    }

    @Override
    public void addJsonDecimal(Variable from, Variable jsonObject, Variable itemName) {
        method.addInvoke(jsonObject.getDescriptor() + ".AddJsonNumber", new GoVar(itemName), new GoVar(from));
    }


    @Override
    public void addJsonBoolean(Variable from, Variable jsonObject, Variable itemName) {
        method.addInvoke(jsonObject.getDescriptor() + ".AddJsonBoolean", new GoVar(itemName), new GoVar(from));
    }

    @Override
    public void addJsonInt(Variable from, Variable jsonObject, Variable itemName) {

    }

}
