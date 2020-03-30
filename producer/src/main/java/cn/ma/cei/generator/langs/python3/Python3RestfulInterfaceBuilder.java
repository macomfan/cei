/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.python3;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.builder.*;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.langs.python3.tools.Python3Class;
import cn.ma.cei.generator.langs.python3.tools.Python3Method;
import java.util.List;

/**
 *
 * @author U0151316
 */
public class Python3RestfulInterfaceBuilder implements IRestfulInterfaceBuilder {

    private Python3Method method;
    private Python3Class clientClass;

    public Python3RestfulInterfaceBuilder(Python3Class clientClass) {
        this.clientClass = clientClass;
        method = new Python3Method(clientClass);
    }

    @Override
    public void setRequestTarget(Variable request, Variable... targets) {
        method.addInvoke(request.getDescriptor() + ".set_target", targets);
    }

    @Override
    public void defineRequest(Variable request) {
        Variable options = BuilderContext.createStatement("self.__options");
        method.addAssign(method.defineVariable(request), method.newInstance(request.getType(), options));
    }

    @Override
    public void invokeQuery(Variable response, Variable request) {
        method.addAssign(method.defineVariable(response), method.invoke("RestfulConnection.query", request));
    }

//    @Override
//    public void setUrl(Variable request) {
//        Variable url = VariableFactory.createConstantVariable("self.__options.url");
//        method.addInvoke(request.getDescriptor() + ".set_url", url);
//    }

    @Override
    public void setRequestMethod(Variable request, Variable requestMethod) {
        method.addInvoke(request.getDescriptor() + ".set_method", requestMethod);
    }

    @Override
    public void returnResult(Variable returnVariable) {
        method.addReturn(returnVariable);
    }

    @Override
    public void onAddReference(VariableType variableType) {
        clientClass.addReference(variableType);
    }

    @Override
    public void startMethod(VariableType returnType, String methodDescriptor, List<Variable> params) {
        method.startMethod(returnType, methodDescriptor, params);
    }

    @Override
    public IDataProcessorBuilder createDataProcessorBuilder() {
        return new Python3DataProcessorBuilder(method);
    }

    @Override
    public void endMethod() {
        method.endMethod();
        clientClass.addMethod(method);
    }

    @Override
    public void addHeader(Variable request, Variable tag, Variable value) {
        method.addInvoke(request.getDescriptor() + ".add_header", tag, value);
    }

    @Override
    public void addToQueryString(Variable request, Variable queryStringName, Variable variable) {
        method.addInvoke(request.getDescriptor() + ".add_query_string", queryStringName, variable);
    }

    @Override
    public void setPostBody(Variable request, Variable postBody) {
        method.addInvoke(request.getDescriptor() + ".set_post_body", postBody);
    }

    @Override
    public void invokeAuthentication(Variable request, String methodName) {
        Variable option = BuilderContext.createStatement("self.__options");
        method.addInvoke("Authentication." + methodName, request, option);
    }

}
