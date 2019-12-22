package cn.ma.cei.generator.builder;

import cn.ma.cei.model.xRestful;

public abstract class RestfulClientBuilder {
    public abstract void startClient(String clientDescriptor);

    public abstract RestfulInterfaceBuilder getRestfulInterfaceBuilder();

    public abstract void endClient();
}
