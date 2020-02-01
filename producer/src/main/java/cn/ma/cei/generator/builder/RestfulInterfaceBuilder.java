package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.environment.Variable;

public abstract class RestfulInterfaceBuilder extends MethodBuilder {

    public abstract ResponseBuilder getResponseBuilder();
    
    public abstract JsonBuilderBuilder getJsonBuilderBuilder();

    public abstract void setRequestTarget(Variable request, Variable... targets);

    public abstract void addHeader(Variable request, Variable tag, Variable value);

    public abstract void defineRequest(Variable request);

    public abstract void addToQueryString(Variable request, Variable queryStringName, Variable variable);

    public abstract void setPostBody(Variable request, Variable postBody);

    public abstract void invokeQuery(Variable response, Variable request);
    
    public abstract void invokeSignature(Variable request, String methodName);

    public abstract void setUrl(Variable request);

    public abstract void setRequestMethod(Variable request, Variable requestMethod);

    public abstract void returnResult(Variable returnVariable);

}
