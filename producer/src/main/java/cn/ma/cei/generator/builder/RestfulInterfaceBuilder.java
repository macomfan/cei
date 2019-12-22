package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableList;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.model.xInterface;

public abstract class RestfulInterfaceBuilder extends MethodBuilder {

    public enum RequestMethod{
        GET,
        POST
    }

    public abstract ResponseBuilder getResponseBuilder();

    public abstract void startInterface(String restIfName);

    public abstract void defineMethod(VariableType returnType, String methodDescriptor, VariableList params, MethodImplementation impl);

    public abstract void setRequestTarget(Variable request, String target);

    public abstract void defineRequest(Variable request);

    public abstract void addToQueryStringByVariable(Variable request, String queryStringName, Variable variable);

    public abstract void addToQueryStringByHardcode(Variable request, String queryStringName, String value);

    public abstract void invokeConnection(Variable request, Variable response);

    public abstract void setRequestMethod(Variable request, String requestMethodDescriptor);

    public abstract void returnResult(Variable returnVariable);

    public abstract void endInterface();
}
