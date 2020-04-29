package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.IMethod;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.buildin.RestfulOptions;


public interface IRestfulClientBuilder extends IBuilderBase {
    void startClient(VariableType clientType, RestfulOptions options);

    IRestfulInterfaceBuilder createRestfulInterfaceBuilder(IMethod method);

    void endClient();
}
