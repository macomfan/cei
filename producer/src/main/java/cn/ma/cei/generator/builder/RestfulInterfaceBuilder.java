package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.environment.Variable;

public abstract class RestfulInterfaceBuilder extends MethodBuilder {



    public abstract ResponseBuilder getResponseBuilder();

//    public abstract void startInterface(String restIfName);
//
//    public abstract void defineMethod(VariableType returnType, String methodDescriptor, VariableList params, MethodImplementation impl);
    public abstract void setRequestTarget(Variable request, String target);

    public abstract void addHeader(Variable request, String tag, Variable value);

    public abstract void defineRequest(Variable request);

    public abstract void addToQueryString(Variable request, String queryStringName, Variable variable);

    public abstract void invokeQuery(Variable request, Variable response);

    public abstract void setUrl(Variable request);

    public abstract void setRequestMethod(Variable request, String requestMethodDescriptor);

    public abstract void returnResult(Variable returnVariable);

//    public abstract void endInterface();
}
