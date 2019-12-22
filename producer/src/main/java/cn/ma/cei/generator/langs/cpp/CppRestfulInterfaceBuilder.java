package cn.ma.cei.generator.langs.cpp;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableList;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.builder.ResponseBuilder;
import cn.ma.cei.generator.builder.RestfulInterfaceBuilder;

public class CppRestfulInterfaceBuilder extends RestfulInterfaceBuilder {
    @Override
    public ResponseBuilder getResponseBuilder() {
        return null;
    }

    @Override
    public void startInterface(String restIfName) {

    }

    @Override
    public void defineMethod(VariableType returnType, String methodDescriptor, VariableList params, MethodImplementation impl) {

    }

    @Override
    public void setRequestTarget(Variable request, String target) {

    }

    @Override
    public void defineRequest(Variable request) {

    }

    @Override
    public void addToQueryStringByVariable(Variable request, String queryStringName, Variable variable) {

    }

    @Override
    public void addToQueryStringByHardcode(Variable request, String queryStringName, String value) {

    }

    @Override
    public void invokeConnection(Variable request, Variable response) {

    }

    @Override
    public void setRequestMethod(Variable request, String requestMethodDescriptor) {

    }

    @Override
    public void returnResult(Variable returnVariable) {

    }

    @Override
    public void endInterface() {

    }

    @Override
    public void onAddReference(VariableType variableType) {

    }
}
