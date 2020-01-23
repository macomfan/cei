/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.golang;

import cn.ma.cei.generator.builder.JsonBuilderBuilder;
import cn.ma.cei.generator.builder.ResponseBuilder;
import cn.ma.cei.generator.builder.RestfulInterfaceBuilder;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.langs.golang.tools.*;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.utils.WordSplitter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author U0151316
 */
public class GoRestfulInterfaceBuilder extends RestfulInterfaceBuilder {

    private final GoStruct clientStruct;
    private GoMethod method;

    public GoRestfulInterfaceBuilder(GoStruct clientStruct) {
        this.clientStruct = clientStruct;
    }

    @Override
    public ResponseBuilder getResponseBuilder() {
        return new GoResponseBuilder(method);
    }

    @Override
    public JsonBuilderBuilder getJsonBuilderBuilder() {
        return new GoJsonBuilderBuilder(method);
    }

    @Override
    public void setRequestTarget(Variable request, Variable... targets) {
        List<GoVar> paramList = new ArrayList<>();
        for (Variable variable : targets) {
            paramList.add(new GoVar(variable));
        }
        GoVar[] params = new GoVar[paramList.size()];
        paramList.toArray(params);
        method.addInvoke(request.getDescriptor() + ".SetTarget", params);
    }

    @Override
    public void addHeader(Variable request, Variable tag, Variable value) {
        method.addInvoke(request.getDescriptor() + ".AddHeader", new GoVar(tag), new GoVar(value));
    }

    @Override
    public void defineRequest(Variable request) {
        Variable options = VariableFactory.createConstantVariable("this.options");
        method.addAssignAndDeclare(method.useVariable(new GoVar(request)), "restful.NewRestfulRequest(&this.options)");
    }

    @Override
    public void addToQueryString(Variable request, Variable queryStringName, Variable variable) {
        if (variable.getType() == xString.inst.getType()) {
            method.addInvoke(request.getDescriptor() + ".AddQueryString", new GoVar(queryStringName), new GoVar(variable));
        } else {
            method.addInvoke(request.getDescriptor() + ".AddQueryStringTODO", new GoVar(queryStringName), new GoVar(variable));
        }
    }

    @Override
    public void setPostBody(Variable request, Variable postBody) {
        method.addInvoke(request.getDescriptor() + ".SetPostBody", new GoVar(postBody));
    }

    @Override
    public void invokeQuery(Variable response, Variable request) {
        method.addAssignAndDeclare(method.useVariable(new GoVar(response)), method.invoke("restful.Query", new GoVar(request)));
    }

    @Override
    public void invokeSignature(Variable request, String methodName) {
        Variable option = VariableFactory.createConstantVariable("this.options");
        method.addInvoke(WordSplitter.getLowerCamelCase(methodName), new GoVar(request), new GoVar(option));
    }

    @Override
    public void setUrl(Variable request) {
        Variable url = VariableFactory.createConstantVariable("this.options.URL");
        method.addInvoke(request.getDescriptor() + ".SetURL", new GoVar(url));
    }

    @Override
    public void setRequestMethod(Variable request, Variable requestMethod) {
        method.addInvoke(request.getDescriptor() + ".SetMethod", new GoVar(requestMethod));
    }

    @Override
    public void returnResult(Variable returnVariable) {
        method.addReturn(new GoVar(returnVariable));
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
    public void endMethod() {
        method.endMethod();
        clientStruct.addMethod(method);
    }

}
