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
import cn.ma.cei.generator.environment.VariableList;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.langs.golang.tools.GoMethod;
import cn.ma.cei.generator.langs.golang.tools.GoStruct;
import cn.ma.cei.model.types.xString;

/**
 *
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
        return new GoJsonBuilderBuilder();
    }

    @Override
    public void setRequestTarget(Variable request, Variable target) {
        method.addInvoke(request.nameDescriptor + ".SetTarget", target);
    }

    @Override
    public void addHeader(Variable request, Variable tag, Variable value) {
        method.addInvoke(request.nameDescriptor + ".AddHeader", tag, value);
    }

    @Override
    public void defineRequest(Variable request) {
        Variable options = VariableFactory.createConstantVariable("this.options");
        method.addAssignAndDeclare(method.useVariable(request), method.newInstacneByConstructor("restful", request.type, options));
    }

    @Override
    public void addToQueryString(Variable request, Variable queryStringName, Variable variable) {
        if (variable.type == xString.inst.getType()) {
            method.addInvoke(request.nameDescriptor + ".AddQueryString", queryStringName, variable);
        } else {
            method.addInvoke(request.nameDescriptor + ".AddQueryStringTODO", queryStringName, variable);
        }
    }

    @Override
    public void setPostBody(Variable request, Variable postBody) {
        method.addInvoke(request.nameDescriptor + ".SetPostBody", postBody);
    }

    @Override
    public void invokeQuery(Variable response, Variable request) {
        method.addAssign(method.useVariable(response), method.invoke("restful.Query", request));
    }

    @Override
    public void invokeSignature(Variable request, String methodName) {
        Variable option = VariableFactory.createConstantVariable("this.options");
        method.addInvoke("signature." + methodName, request, option);
    }

    @Override
    public void setUrl(Variable request) {
        Variable url = VariableFactory.createConstantVariable("this.options.URL");
        method.addInvoke(request.nameDescriptor + ".SetURL", url);
    }

    @Override
    public void setRequestMethod(Variable request, Variable requestMethod) {
        method.addInvoke(request.nameDescriptor + ".SetMethod", requestMethod);
    }

    @Override
    public void returnResult(Variable returnVariable) {
        method.addReturn(returnVariable);
    }

    @Override
    public void onAddReference(VariableType variableType) {
        clientStruct.addReference(variableType);
    }

    @Override
    public void startMethod(VariableType returnType, String methodDescriptor, VariableList params) {
        method = new GoMethod(clientStruct);
        method.startMethod(returnType, methodDescriptor, params);
    }

    @Override
    public void endMethod() {
        method.endMethod();
        clientStruct.addMethod(method);
    }
    
}
