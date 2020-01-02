package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.buildin.RestfulOptions;


public abstract class RestfulClientBuilder {
    public abstract void startClient(String clientDescriptor, RestfulOptions options);

    public abstract RestfulInterfaceBuilder getRestfulInterfaceBuilder();

    public abstract void endClient();
}
