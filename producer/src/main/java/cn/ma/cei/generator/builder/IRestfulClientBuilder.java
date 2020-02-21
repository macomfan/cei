package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.sMethod;


public interface IRestfulClientBuilder {
    void startClient(String clientDescriptor, RestfulOptions options);

    IRestfulInterfaceBuilder createRestfulInterfaceBuilder(sMethod method);

    void endClient();
}
