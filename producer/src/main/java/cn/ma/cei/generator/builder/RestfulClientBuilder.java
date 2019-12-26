package cn.ma.cei.generator.builder;


public abstract class RestfulClientBuilder {
    public abstract void startClient(String clientDescriptor, String url);

    public abstract RestfulInterfaceBuilder getRestfulInterfaceBuilder();

    public abstract void endClient();
}
