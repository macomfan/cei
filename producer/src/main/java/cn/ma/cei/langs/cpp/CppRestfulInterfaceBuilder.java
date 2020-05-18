package cn.ma.cei.langs.cpp;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IRestfulInterfaceBuilder;
import cn.ma.cei.langs.cpp.tools.CppClass;
import cn.ma.cei.langs.cpp.tools.CppMethod;

import java.util.List;

public class CppRestfulInterfaceBuilder implements IRestfulInterfaceBuilder {

    private final CppClass clientClass;
    private CppMethod method;

    public CppRestfulInterfaceBuilder(CppClass clientClass) {
        this.clientClass = clientClass;
    }


    @Override
    public void defineRequest(Variable request, Variable option) {
        method.addDefineStackVariable(request, option);
    }


    @Override
    public void invokeQuery(Variable response, Variable request) {
        method.addAssign(method.declareStackVariable(response), method.invoke(request.getDescriptor() + ".query"));
    }

    @Override
    public void onAddReference(VariableType variableType) {

    }

    @Override
    public void startMethod(VariableType returnType, String methodDescriptor, List<Variable> params) {
        method = new CppMethod(clientClass);
        method.startMethod(returnType, methodDescriptor, params);
    }

    @Override
    public IDataProcessorBuilder createDataProcessorBuilder() {
        return new CppDataProcessorBuilder(method);
    }

    @Override
    public void endMethod(Variable returnVariable) {
        method.endMethod();
        clientClass.addMethod(method);
    }

    @Override
    public void addHeader(Variable request, Variable tag, Variable value) {
        method.addInvoke(request.getDescriptor() + ".addHeader", tag, value);
    }

    @Override
    public void addToQueryString(Variable request, Variable queryStringName, Variable variable) {
        method.addInvoke(request.getDescriptor() + ".addQueryString", queryStringName, variable);
    }

    @Override
    public void setRequestTarget(Variable request, Variable target) {
        method.addInvoke(request.getDescriptor() + ".setTarget", target);
    }

    @Override
    public void setRequestMethod(Variable request, Variable requestMethod) {
        method.addInvoke(request.getDescriptor() + ".setMethod", requestMethod);
    }

    @Override
    public void setPostBody(Variable request, Variable postBody) {
        method.addInvoke(request.getDescriptor() + ".setPostBody", postBody);
    }
}
