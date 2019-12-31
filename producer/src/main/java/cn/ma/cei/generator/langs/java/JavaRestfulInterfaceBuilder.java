package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableList;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.builder.ResponseBuilder;
import cn.ma.cei.generator.builder.RestfulInterfaceBuilder;
import cn.ma.cei.generator.langs.java.tools.JavaClass;
import cn.ma.cei.generator.langs.java.tools.JavaMethod;

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
    public void setRequestTarget(Variable request, Variable target) {
        method.addInvoke(request.nameDescriptor + ".setTarget", target);
    }

    @Override
    public void defineRequest(Variable request) {
        
        method.addAssign(method.defineVariable(request), method.newInstance(request.type, this.queryVariable("options")));
    }

    @Override
    public void addToQueryString(Variable request, Variable queryStringName, Variable variable) {
        method.addInvoke(request.nameDescriptor + ".addQueryString", queryStringName, variable);
    }

    @Override
    public void invokeQuery(Variable response, Variable request) {
        method.addAssign(method.defineVariable(response), method.invoke("RestfulConnection.query", request));
    }

    @Override
    public void setRequestMethod(Variable request, Variable requestMethod) {
        method.addInvoke(request.nameDescriptor + ".setMethod", requestMethod);
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
    public void setUrl(Variable request, Variable url) {
        method.addInvoke(request.nameDescriptor + ".setUrl", url);
    }

    @Override
    public void addHeader(Variable request, Variable tag, Variable value) {
        method.addInvoke(request.nameDescriptor + ".addHeader", tag, value);
    }

    @Override
    public void startMethod(VariableType returnType, String methodDescriptor, VariableList params) {
        method.startMethod(returnType, methodDescriptor, params);
    }

    @Override
    public void endMethod() {
        method.endMethod();
        clientClass.addMethod(method);
    }
}
