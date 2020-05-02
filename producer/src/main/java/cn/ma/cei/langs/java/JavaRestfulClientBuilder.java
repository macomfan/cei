package cn.ma.cei.langs.java;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.IMethod;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IRestfulClientBuilder;
import cn.ma.cei.generator.builder.IRestfulInterfaceBuilder;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.buildin.WebSocketOptions;
import cn.ma.cei.langs.java.tools.JavaClass;
import cn.ma.cei.langs.java.tools.JavaMethod;

public class JavaRestfulClientBuilder implements IRestfulClientBuilder {

    private final JavaClass mainClass;
    private JavaClass clientClass = null;

    public JavaRestfulClientBuilder(JavaClass mainClass) {
        this.mainClass = mainClass;
    }

    @Override
    public void startClient(VariableType client, RestfulOptions option, Variable optionVariable) {
        clientClass = new JavaClass(client.getDescriptor());
        clientClass.addMemberVariable(JavaClass.AccessType.PRIVATE, client.getMember("option"));

        JavaMethod defaultConstructor = new JavaMethod(clientClass);
        defaultConstructor.startConstructor("");
        {
            defaultConstructor.addAssign(defaultConstructor.useVariable(optionVariable), defaultConstructor.newInstance(optionVariable.getType()));
            Variable url = optionVariable.getMember("url");
            defaultConstructor.addAssign(url.getDescriptor(), BuilderContext.createStringConstant(option.url).getDescriptor());
        }
        defaultConstructor.endMethod();
        clientClass.addMethod(defaultConstructor);

        JavaMethod optionConstructor = new JavaMethod(clientClass);
        optionConstructor.startConstructor(RestfulOptions.getType().getDescriptor() + " option");
        {
            optionConstructor.addInvoke("this");
            optionConstructor.addInvoke(optionVariable.getDescriptor() + ".setFrom", BuilderContext.createStatement("option"));
        }
        optionConstructor.endMethod();
        clientClass.addMethod(optionConstructor);
    }

    @Override
    public IRestfulInterfaceBuilder createRestfulInterfaceBuilder(IMethod method) {
        return new JavaRestfulInterfaceBuilder(clientClass);
    }

    @Override
    public void endClient() {
        mainClass.addInnerClass(clientClass);
    }

}
