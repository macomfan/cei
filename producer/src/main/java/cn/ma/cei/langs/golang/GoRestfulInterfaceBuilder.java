/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.golang;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IRestfulInterfaceBuilder;
import cn.ma.cei.langs.golang.tools.*;
import cn.ma.cei.langs.golang.vars.GoMergedInputVar;
import cn.ma.cei.langs.golang.vars.GoPtrType;
import cn.ma.cei.langs.golang.vars.GoType;
import cn.ma.cei.langs.golang.vars.GoVar;

import java.util.LinkedList;
import java.util.List;

/**
 * @author U0151316
 */
public class GoRestfulInterfaceBuilder implements IRestfulInterfaceBuilder {

    private final GoStruct clientStruct;
    private GoMethod method;

    public GoRestfulInterfaceBuilder(GoStruct clientStruct) {
        this.clientStruct = clientStruct;
    }

    @Override
    public void setRequestTarget(Variable request, Variable target) {
        method.addInvoke(request.getDescriptor() + ".SetTarget", method.var(target));
    }

    @Override
    public void addHeader(Variable request, Variable tag, Variable value) {
        method.addInvoke(request.getDescriptor() + ".AddHeader", method.var(tag), method.var(value));
    }

    @Override
    public void defineRequest(Variable request, Variable option) {
        method.addAssignAndDeclare(method.useVariable(method.var(request)), "impl.NewRestfulRequest(inst.option)");
    }

    @Override
    public void addToQueryString(Variable request, Variable queryStringName, Variable variable) {
        method.addInvoke(request.getDescriptor() + ".AddQueryString", method.var(queryStringName), method.var(variable));
    }

    @Override
    public void setPostBody(Variable request, Variable postBody) {
        method.addInvoke(request.getDescriptor() + ".SetPostBody", method.var(postBody));
    }

    @Override
    public void invokeQuery(Variable response, Variable request) {
        method.addAssignAndDeclare(method.useVariable(method.var(response)), method.invoke("impl.RestfulQuery", method.var(request)));
    }

    @Override
    public void setRequestMethod(Variable request, Variable requestMethod) {
        method.addInvoke(request.getDescriptor() + ".SetMethod", method.var(requestMethod));
    }


    @Override
    public void onAddReference(VariableType variableType) {
        clientStruct.addReference(variableType);
    }

    @Override
    public void startMethod(VariableType returnType, String methodDescriptor, List<Variable> params) {
        method = new GoMethod(clientStruct);
        List<GoVar> tmp = new LinkedList<>();
        params.forEach(item -> {
            tmp.add(method.var(item));
        });
        method.startInterface(new GoType(returnType), methodDescriptor, tmp);
    }

    @Override
    public IDataProcessorBuilder createDataProcessorBuilder() {
        return new GoDataProcessorBuilder(method);
    }

    @Override
    public void endMethod(Variable returnVariable) {
        method.addReturn(method.var(returnVariable));
        method.endMethod();
        clientStruct.addMethod(method);
    }

    @Override
    public void endMethod() {
        method.endMethod();
        clientStruct.addMethod(method);
    }
}
