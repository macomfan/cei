package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.builder.RestfulClientBuilder;
import cn.ma.cei.generator.builder.RestfulInterfaceBuilder;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.langs.java.tools.JavaClass;
import cn.ma.cei.generator.langs.java.tools.JavaMethod;

public class JavaRestfulClientBuilder extends RestfulClientBuilder {

    private final JavaClass mainClass;
    private JavaClass clientClass = null;

    private JavaMethod defauleConstructor = null;
    private JavaMethod optionConstructor = null;
    
    public JavaRestfulClientBuilder(JavaClass mainClass) {
        this.mainClass = mainClass;
    }

    @Override
    public void startClient(String clientDescriptor, RestfulOptions options) {
        clientClass = new JavaClass(clientDescriptor);
        clientClass.addMemberVariable(JavaClass.AccessType.PRIVATE, this.createMemberVariable(RestfulOptions.getType(), "options"));

        defauleConstructor = new JavaMethod(clientClass);
        defauleConstructor.getCode().appendWordsln("public", clientDescriptor + "() {");
        defauleConstructor.getCode().newBlock(() -> {
            defauleConstructor.getCode().appendJavaLine("this.options", "=", "new", RestfulOptions.getType().getDescriptor() + "()");
            Variable url = VariableFactory.createHardcodeStringVariable(options.url);
            defauleConstructor.getCode().appendJavaLine("this.options.url", "=", url.getDescriptor());
            if (options.connectionTimeout != null) {
                defauleConstructor.getCode().appendJavaLine("this.options.connectionTimeout", "=", options.connectionTimeout.toString());
            }
        });
        defauleConstructor.getCode().appendln("}");
        clientClass.addMethod(defauleConstructor);

        optionConstructor = new JavaMethod(clientClass);
        optionConstructor.getCode().appendWordsln("public", clientDescriptor + "(" + RestfulOptions.getType().getDescriptor() + " options) {");
        optionConstructor.getCode().newBlock(() -> {
            optionConstructor.getCode().appendJavaLine("this.options", "=", "new", RestfulOptions.getType().getDescriptor() + "()");
            Variable url = VariableFactory.createHardcodeStringVariable(options.url);
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
    public RestfulInterfaceBuilder getRestfulInterfaceBuilder() {
        return new JavaRestfulInterfaceBuilder(clientClass);
    }

    @Override
    public void endClient() {
        mainClass.addInnerClass(clientClass);
    }

}
