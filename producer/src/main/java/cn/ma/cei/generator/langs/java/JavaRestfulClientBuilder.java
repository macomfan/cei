package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IRestfulClientBuilder;
import cn.ma.cei.generator.builder.IRestfulInterfaceBuilder;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.langs.java.tools.JavaClass;
import cn.ma.cei.generator.langs.java.tools.JavaMethod;
import cn.ma.cei.generator.sMethod;

public class JavaRestfulClientBuilder implements IRestfulClientBuilder {

    private final JavaClass mainClass;
    private JavaClass clientClass = null;

    public JavaRestfulClientBuilder(JavaClass mainClass) {
        this.mainClass = mainClass;
    }

    @Override
    public void startClient(VariableType clientType, RestfulOptions options) {
        clientClass = new JavaClass(clientType.getDescriptor());
        clientClass.addMemberVariable(JavaClass.AccessType.PRIVATE, clientType.addPrivateMember(RestfulOptions.getType(), "options"));

        JavaMethod defaultConstructor = new JavaMethod(clientClass);
        defaultConstructor.startConstructor("");
        {
            defaultConstructor.getCode().appendJavaLine("this.options", "=", "new", RestfulOptions.getType().getDescriptor() + "()");
            Variable url = BuilderContext.createStringConstant(options.url);
            defaultConstructor.getCode().appendJavaLine("this.options.url", "=", url.getDescriptor());
            if (options.connectionTimeout != null) {
                defaultConstructor.getCode().appendJavaLine("this.options.connectionTimeout", "=", options.connectionTimeout.toString());
            }
        }
        defaultConstructor.endMethod();
        clientClass.addMethod(defaultConstructor);

        JavaMethod optionConstructor = new JavaMethod(clientClass);
        optionConstructor.startConstructor(RestfulOptions.getType().getDescriptor() + " options");
        {
            optionConstructor.getCode().appendJavaLine("this()");
            optionConstructor.getCode().appendJavaLine("this.options.setFrom(options)");
        }
        optionConstructor.endMethod();
        clientClass.addMethod(optionConstructor);
    }

    @Override
    public IRestfulInterfaceBuilder createRestfulInterfaceBuilder(sMethod method) {
        return new JavaRestfulInterfaceBuilder(clientClass);
    }

    @Override
    public void endClient() {
        mainClass.addInnerClass(clientClass);
    }

}
