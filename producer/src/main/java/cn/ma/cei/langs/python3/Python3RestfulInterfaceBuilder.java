/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.python3;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IRestfulInterfaceBuilder;
import cn.ma.cei.langs.python3.tools.Python3Class;
import cn.ma.cei.langs.python3.tools.Python3Method;

import java.util.List;

/**
 *
 * @author U0151316
 */
public class Python3RestfulInterfaceBuilder implements IRestfulInterfaceBuilder {

    private final Python3Method method;
    private final Python3Class clientClass;

    public Python3RestfulInterfaceBuilder(Python3Class clientClass) {
        this.clientClass = clientClass;
        method = new Python3Method(clientClass);
    }

    @Override
    public void setRequestTarget(Variable request, Variable target) {
        method.addInvoke(request.getDescriptor() + ".set_target", target);
    }

    @Override
    public void defineRequest(Variable request, Variable option) {
        //Variable options = BuilderContext.createStatement("self.__options");
        method.addAssign(method.defineVariable(request), method.newInstance(request.getType(), option));
    }

    @Override
    public void invokeQuery(Variable response, Variable request) {
        method.addAssign(method.defineVariable(response), method.invoke("RestfulConnection.query", request));
    }

    @Override
    public void setRequestMethod(Variable request, Variable requestMethod) {
        method.addInvoke(request.getDescriptor() + ".set_method", requestMethod);
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
    public void endMethod(Variable returnVariable) {
        method.addReturn(returnVariable);
        method.endMethod();
        clientClass.addMethod(method);
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
}
