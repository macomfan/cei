package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.builder.RestfulClientBuilder;
import cn.ma.cei.generator.builder.RestfulInterfaceBuilder;
import cn.ma.cei.generator.buildin.RestfulOptions;
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
    public void startClient(String clientDescriptor, String url) {
        clientClass = new JavaClass(clientDescriptor);
        clientClass.addMemberVariable(JavaClass.AccessType.PRIVATE, VariableFactory.createLocalVariable(RestfulOptions.getType(), "options"));

        defauleConstructor = new JavaMethod(clientClass);
        defauleConstructor.getCode().appendWordsln("public", clientDescriptor + "() {");
        defauleConstructor.getCode().newBlock(() -> {
            defauleConstructor.getCode().appendStatementWordsln("this.options", "=", "new", RestfulOptions.getType().getDescriptor() + "()");
            defauleConstructor.getCode().appendStatementWordsln("this.options.url", "=", defauleConstructor.getCode().toJavaString(url));
        });
        defauleConstructor.getCode().appendln("}");
        clientClass.addMethod(defauleConstructor.getCode());

        optionConstructor = new JavaMethod(clientClass);
        optionConstructor.getCode().appendWordsln("public", clientDescriptor + "(" + RestfulOptions.getType().getDescriptor() + " options) {");
        optionConstructor.getCode().newBlock(() -> {
            optionConstructor.getCode().appendStatementWordsln("this.options ", "=", "options");
        });
        optionConstructor.getCode().appendln("}");
        clientClass.addMethod(optionConstructor.getCode());
        

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
