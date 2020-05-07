package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.IMethod;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.buildin.RestfulOptions;


public interface IRestfulClientBuilder extends IBuilderBase {
    void startClient(VariableType client, RestfulOptions option, Variable optionVariable);

    IRestfulInterfaceBuilder createRestfulInterfaceBuilder(IMethod method);

    void endClient();
}
