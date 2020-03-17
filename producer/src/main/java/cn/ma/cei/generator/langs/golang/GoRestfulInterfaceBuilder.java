/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.golang;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.builder.*;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.langs.golang.tools.*;
import cn.ma.cei.utils.WordSplitter;

import java.util.ArrayList;
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
        method.addAssignAndDeclare(method.useVariable(new GoVar(request)), "ceiimpl.NewRestfulRequest(inst.options)");
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
        method.addAssignAndDeclare(method.useVariable(new GoVar(response)), method.invoke("ceiimpl.RestfulQuery", new GoVar(request)));
    }

    @Override
    public void invokeAuthentication(Variable request, String methodName) {
        Variable option = BuilderContext.createStatement("inst.options");
        method.addInvoke(WordSplitter.getLowerCamelCase(methodName), new GoVar(request), new GoVar(option));
    }

//    @Override
//    public void setUrl(Variable request) {
//        Variable url = VariableFactory.createConstantVariable("inst.options.URL");
//        method.addInvoke(request.getDescriptor() + ".SetURL", new GoVar(url));
//    }

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

//    @Override
//    public IJsonBuilderBuilder createJsonBuilderBuilder() {
//        return new GoJsonBuilderBuilder(method);
//    }
//
//    @Override
//    public IStringBuilderBuilder createStringBuilderBuilder() {
//        return null;
//    }
//
//    @Override
//    public IJsonParserBuilder createJsonParserBuilder() {
//        return new GoJsonParserBuilder(method);
//    }

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
        return null;
    }

    @Override
    public void endMethod() {
        method.endMethod();
        clientStruct.addMethod(method);
    }

}
