package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableList;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.builder.ResponseBuilder;
import cn.ma.cei.generator.builder.RestfulInterfaceBuilder;
import cn.ma.cei.generator.langs.java.tools.JavaClass;
import cn.ma.cei.generator.langs.java.tools.JavaMethod;

public class JavaRestfulInterfaceBuilder extends RestfulInterfaceBuilder {

    private JavaMethod method;
    private JavaClass clientClass;

    public JavaRestfulInterfaceBuilder(JavaClass javaClass) {
        this.clientClass = javaClass;
        method = new JavaMethod(javaClass);
    }

    @Override
    public ResponseBuilder getResponseBuilder() {
        return new JavaResponseBuilder(method);
    }

    @Override
    public void setRequestTarget(Variable request, Variable target) {
        method.invoke(request.nameDescriptor + ".setTarget", target);
        //method.getCode().appendStatementln(request.nameDescriptor + ".setTarget(" + method.getCode().toJavaString(target) + ")");
    }

    @Override
    public void defineRequest(Variable request) {
        method.getCode().appendStatementWordsln(request.type.getDescriptor(), request.nameDescriptor, "=", "new", request.type.getDescriptor() + "(this.options)");
    }

    @Override
    public void addToQueryString(Variable request, Variable queryStringName, Variable variable) {
        method.invoke(request.nameDescriptor + ".addQueryString", queryStringName, variable);
    }

    @Override
    public void invokeQuery(Variable response, Variable request) {
        method.getCode().appendStatementWordsln(response.type.getDescriptor(), response.nameDescriptor, "=", "RestfulConnection.query(" + request.nameDescriptor + ")");
    }

    @Override
    public void setRequestMethod(Variable request, Variable requestMethod) {
        method.invoke(request.nameDescriptor + ".setMethod", requestMethod);
    }

    @Override
    public void returnResult(Variable returnVariable) {
        method.getCode().appendStatementWordsln("return", returnVariable.nameDescriptor);
    }

    @Override
    public void onAddReference(VariableType variableType) {
        clientClass.addReference(variableType);
    }

    @Override
    public void setUrl(Variable request, Variable url) {
        method.invoke(request.nameDescriptor + ".setUrl", url);
    }

    @Override
    public void addHeader(Variable request, Variable tag, Variable value) {
        method.invoke(request.nameDescriptor + ".addHeader", tag, value);
        //method.getCode().appendStatementWordsln(request.nameDescriptor + ".addHeader(" + method.getCode().toJavaString(tag) + ",", value.nameDescriptor + ")");
    }

    @Override
    public void startMethod(VariableType returnType, String methodDescriptor, VariableList params) {
        method.startMethod(returnType, methodDescriptor, params);
    }

    @Override
    public void endMethod() {
        method.endMethod();
        clientClass.addMethod(method.getCode());
    }
}
