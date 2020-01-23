package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.builder.JsonBuilderBuilder;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.builder.ResponseBuilder;
import cn.ma.cei.generator.builder.RestfulInterfaceBuilder;
import cn.ma.cei.generator.langs.java.tools.JavaClass;
import cn.ma.cei.generator.langs.java.tools.JavaMethod;
import java.util.List;

public class JavaRestfulInterfaceBuilder extends RestfulInterfaceBuilder {

    //private JavaMethod method;
    private JavaClass clientClass;

    private JavaMethod method;

    public JavaRestfulInterfaceBuilder(JavaClass javaClass) {
        this.clientClass = javaClass;
        method = new JavaMethod(javaClass);
    }

    @Override
    public ResponseBuilder getResponseBuilder() {
        return new JavaResponseBuilder(method);
    }

    @Override
    public void setRequestTarget(Variable request, Variable... targets ) {
        method.addInvoke(request.getDescriptor() + ".setTarget", targets);
    }

    @Override
    public void defineRequest(Variable request) {
        Variable options = VariableFactory.createConstantVariable("this.options");
        method.addAssign(method.defineVariable(request), method.newInstance(request.getType(), options));
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

    @Override
    public void setUrl(Variable request) {
        Variable url = VariableFactory.createConstantVariable("this.options.url");
        method.addInvoke(request.getDescriptor() + ".setUrl", url);
    }

    @Override
    public void addHeader(Variable request, Variable tag, Variable value) {
        method.addInvoke(request.getDescriptor() + ".addHeader", tag, value);
    }

    @Override
    public void startMethod(VariableType returnType, String methodDescriptor, List<Variable> params) {
        method.startMethod(returnType, methodDescriptor, params);
    }

    @Override
    public void endMethod() {
        method.endMethod();
        clientClass.addMethod(method);
    }

    @Override
    public void setPostBody(Variable request, Variable postBody) {
        method.addInvoke(request.getDescriptor() + ".setPostBody", postBody);
    }

    @Override
    public JsonBuilderBuilder getJsonBuilderBuilder() {
        return new JavaJsonBuilderBuilder(method);
    }

    @Override
    public void invokeSignature(Variable request, String methodName) {
        Variable option = VariableFactory.createConstantVariable("this.options");
        method.addInvoke("Signature." + methodName, request, option);
    }
}
