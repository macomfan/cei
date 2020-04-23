package cn.ma.cei.langs.java;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.builder.*;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.langs.java.tools.JavaClass;
import cn.ma.cei.langs.java.tools.JavaMethod;
import java.util.List;

public class JavaRestfulInterfaceBuilder implements IRestfulInterfaceBuilder {

    private JavaClass clientClass;

    private JavaMethod method;

    public JavaRestfulInterfaceBuilder(JavaClass javaClass) {
        this.clientClass = javaClass;
        method = new JavaMethod(javaClass);
    }

    @Override
    public void setRequestTarget(Variable request, Variable target ) {
        method.addInvoke(request.getDescriptor() + ".setTarget", target);
    }

    @Override
    public void defineRequest(Variable request, Variable option) {
        //Variable options = BuilderContext.createStatement("this.option");
        method.addAssign(method.defineVariable(request), method.newInstance(request.getType(), option));
    }

    @Override
    public void addToQueryString(Variable request, Variable queryStringName, Variable variable) {
        method.addInvoke(request.getDescriptor() + ".addQueryString", queryStringName, variable);
    }

    @Override
    public void invokeQuery(Variable response, Variable request) {
        method.addAssign(method.defineVariable(response), method.invoke("RestfulConnection.query", request));
    }

    @Override
    public void setRequestMethod(Variable request, Variable requestMethod) {
        method.addInvoke(request.getDescriptor() + ".setMethod", requestMethod);
    }

    @Override
    public void returnResult(Variable returnVariable) {
        method.addReturn(returnVariable);
    }

    @Override
    public void onAddReference(VariableType variableType) {
        clientClass.addReference(variableType);
    }

//    @Override
//    public IJsonBuilderBuilder createJsonBuilderBuilder() {
//        return new JavaJsonBuilderBuilder(method);
//    }
//
//    @Override
//    public IStringBuilderBuilder createStringBuilderBuilder() {
//        return new JavaStringBuilderBuilder(method);
//    }
//
//    @Override
//    public IJsonParserBuilder createJsonParserBuilder() {
//        return new JavaJsonParserBuilder(method);
//    }

//    @Override
//    public void setUrl(Variable request) {
//        Variable url = VariableFactory.createConstantVariable("this.options.url");
//        method.addInvoke(request.getDescriptor() + ".setUrl", url);
//    }

    @Override
    public void addHeader(Variable request, Variable tag, Variable value) {
        method.addInvoke(request.getDescriptor() + ".addHeader", tag, value);
    }

    @Override
    public void startMethod(VariableType returnType, String methodDescriptor, List<Variable> params) {
        method.startMethod(returnType, methodDescriptor, params);
    }

    @Override
    public IDataProcessorBuilder createDataProcessorBuilder() {
        return new JavaDataProcessorBuilder(method);
    }

    @Override
    public void endMethod(Variable returnVariable) {
        method.endMethod();
        clientClass.addMethod(method);
    }

    @Override
    public void setPostBody(Variable request, Variable postBody) {
        method.addInvoke(request.getDescriptor() + ".setPostBody", postBody);
    }

    @Override
    public void invokeAuthentication(Variable request, String methodName) {
        Variable option = BuilderContext.createStatement("this.option");
        method.addInvoke("Authentication." + methodName, request, option);
    }
}
