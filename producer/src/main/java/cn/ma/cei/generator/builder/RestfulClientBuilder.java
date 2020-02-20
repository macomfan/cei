package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.sMethod;


public abstract class RestfulClientBuilder {
    public abstract void startClient(String clientDescriptor, RestfulOptions options);

    public abstract RestfulInterfaceBuilder getRestfulInterfaceBuilder(sMethod method);

    public abstract void endClient();
}
