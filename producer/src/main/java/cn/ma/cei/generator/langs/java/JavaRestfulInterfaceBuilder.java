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
    private JavaClass javaClass;

    public JavaRestfulInterfaceBuilder(JavaClass javaClass) {
        this.javaClass = javaClass;
        method = new JavaMethod(javaClass);
    }

    @Override
    public ResponseBuilder getResponseBuilder() {
        return new JavaResponseBuilder(method);
    }

    @Override
    public void startInterface(String restIfName) {

    }

    @Override
    public void defineMethod(VariableType returnType, String methodDescriptor, VariableList params, MethodImplementation impl) {
        method.getCode().defineMethod(returnType, methodDescriptor, params, impl);
    }

    @Override
    public void setRequestTarget(Variable request, String target) {
        method.getCode().appendStatementln(request.nameDescriptor + ".setTarget(" + method.getCode().toJavaString(target) + ")");
    }

    @Override
    public void defineRequest(Variable request) {
        method.getCode().appendStatementWordsln(request.type.getDescriptor(), request.nameDescriptor, "=", "new", request.type.getDescriptor() + "(this.option)");
    }

    @Override
    public void addToQueryStringByVariable(Variable request, String queryStringName, Variable variable) {
        method.getCode().appendStatementln(request.nameDescriptor + "." + "addQueryString(" + method.getCode().toJavaString(queryStringName) + ", " + variable.nameDescriptor + ")");
    }

    @Override
    public void addToQueryStringByHardcode(Variable request, String queryStringName, String value) {
        method.getCode().appendStatementln(request.nameDescriptor + "." + "addQueryString(" + method.getCode().toJavaString(queryStringName) + ", " + method.getCode().toJavaString(value) + ")");
    }

    @Override
    public void invokeQuery(Variable request, Variable response) {
        method.getCode().appendStatementWordsln(response.type.getDescriptor(), response.nameDescriptor, "=", "RestfulConnection.query(" + request.nameDescriptor + ")");
    }

    @Override
    public void setRequestMethod(Variable request, String requestMethodDescriptor) {
        method.getCode().appendStatementWordsln(request.nameDescriptor + ".setMethod(" + requestMethodDescriptor + ")");
    }

    @Override
    public void returnResult(Variable returnVariable) {
        method.getCode().appendStatementWordsln("return", returnVariable.nameDescriptor);
    }

    @Override
    public void endInterface() {
        javaClass.addMethod(method.getCode());
    }

    @Override
    public void onAddReference(VariableType variableType) {
        javaClass.addReference(variableType);
    }

    @Override
    public void setUrl(Variable request) {
        method.getCode().appendStatementWordsln(request.nameDescriptor + ".setUrl(this.option.url)");
    }
}
