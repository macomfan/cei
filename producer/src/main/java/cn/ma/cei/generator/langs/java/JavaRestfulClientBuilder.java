package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.RestfulClientBuilder;
import cn.ma.cei.generator.builder.RestfulInterfaceBuilder;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.langs.java.tools.JavaClass;
import cn.ma.cei.generator.langs.java.tools.JavaMethod;
import cn.ma.cei.generator.sMethod;

public class JavaRestfulClientBuilder extends RestfulClientBuilder {

    private final JavaClass mainClass;
    private JavaClass clientClass = null;
    private VariableType clientType;

    private JavaMethod defaultConstructor = null;
    private JavaMethod optionConstructor = null;

    public JavaRestfulClientBuilder(VariableType clientType, JavaClass mainClass) {
        this.clientType = clientType;
        this.mainClass = mainClass;
    }

    @Override
    public void startClient(String clientDescriptor, RestfulOptions options) {
        clientClass = new JavaClass(clientDescriptor);
        clientClass.addMemberVariable(JavaClass.AccessType.PRIVATE, clientType.addMember(RestfulOptions.getType(), "options"));

        defaultConstructor = new JavaMethod(clientClass);
        defaultConstructor.getCode().appendWordsln("public", clientDescriptor + "() {");
        defaultConstructor.getCode().newBlock(() -> {
            defaultConstructor.getCode().appendJavaLine("this.options", "=", "new", RestfulOptions.getType().getDescriptor() + "()");
            Variable url = BuilderContext.createStringConstant(options.url);
            defaultConstructor.getCode().appendJavaLine("this.options.url", "=", url.getDescriptor());
            if (options.connectionTimeout != null) {
                defaultConstructor.getCode().appendJavaLine("this.options.connectionTimeout", "=", options.connectionTimeout.toString());
            }
        });
        defaultConstructor.getCode().appendln("}");
        clientClass.addMethod(defaultConstructor);

        optionConstructor = new JavaMethod(clientClass);
        optionConstructor.getCode().appendWordsln("public", clientDescriptor + "(" + RestfulOptions.getType().getDescriptor() + " options) {");
        optionConstructor.getCode().newBlock(() -> {
            optionConstructor.getCode().appendJavaLine("this.options", "=", "new", RestfulOptions.getType().getDescriptor() + "()");
            Variable url = BuilderContext.createStringConstant(options.url);
            optionConstructor.getCode().appendJavaLine("this.options.url", "=", url.getDescriptor());
            if (options.connectionTimeout != null) {
                optionConstructor.getCode().appendJavaLine("this.options.connectionTimeout", "=", options.connectionTimeout.toString());
            }
            optionConstructor.getCode().appendJavaLine("this.options.setFrom(options)");
        });
        optionConstructor.getCode().appendln("}");
        clientClass.addMethod(optionConstructor);


    }

    @Override
    public RestfulInterfaceBuilder getRestfulInterfaceBuilder(sMethod method) {
        return new JavaRestfulInterfaceBuilder(clientClass);
    }

    @Override
    public void endClient() {
        mainClass.addInnerClass(clientClass);
    }

}
