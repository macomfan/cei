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
        method.addInvoke(request.getDescriptor() + ".SetTarget", new GoVar(target));
    }

    @Override
    public void addHeader(Variable request, Variable tag, Variable value) {
        method.addInvoke(request.getDescriptor() + ".AddHeader", new GoVar(tag), new GoVar(value));
    }

    @Override
    public void defineRequest(Variable request, Variable option) {
        method.addAssignAndDeclare(method.useVariable(new GoVar(request)), "impl.NewRestfulRequest(inst.options)");
    }

    @Override
    public void addToQueryString(Variable request, Variable queryStringName, Variable variable) {
        method.addInvoke(request.getDescriptor() + ".AddQueryString", new GoVar(queryStringName), new GoVar(variable));
    }

    @Override
    public void setPostBody(Variable request, Variable postBody) {
        method.addInvoke(request.getDescriptor() + ".SetPostBody", new GoVar(postBody));
    }

    @Override
    public void invokeQuery(Variable response, Variable request) {
        method.addAssignAndDeclare(method.useVariable(new GoVar(response)), method.invoke("impl.RestfulQuery", new GoVar(request)));
    }

    @Override
    public void setRequestMethod(Variable request, Variable requestMethod) {
        method.addInvoke(request.getDescriptor() + ".SetMethod", new GoVar(requestMethod));
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
            tmp.add(new GoInterfaceVar(item));
        });
        method.startMethod(new GoPtrType(returnType), methodDescriptor, tmp);
    }

    @Override
    public IDataProcessorBuilder createDataProcessorBuilder() {
        return new GoDataProcessorBuilder(method);
    }

    @Override
    public void endMethod(Variable returnVariable) {
        method.addReturn(new GoVar(returnVariable));
        method.endMethod();
        clientStruct.addMethod(method);
    }

    @Override
    public void endMethod() {
        method.endMethod();
        clientStruct.addMethod(method);
    }
}
