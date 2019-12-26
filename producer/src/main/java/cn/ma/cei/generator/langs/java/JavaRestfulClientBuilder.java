package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.CEIPath;
import cn.ma.cei.generator.builder.RestfulClientBuilder;
import cn.ma.cei.generator.builder.RestfulInterfaceBuilder;
import cn.ma.cei.generator.buildin.RestfulOption;
import cn.ma.cei.generator.environment.Environment;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.langs.java.tools.JavaClass;
import cn.ma.cei.generator.langs.java.tools.JavaMethod;

public class JavaRestfulClientBuilder extends RestfulClientBuilder {

    private JavaClass javaClass = null;

    private JavaMethod defauleConstructor = null;
    private JavaMethod optionConstructor = null;

    @Override
    public void startClient(String clientDescriptor, String url) {
        javaClass = new JavaClass(clientDescriptor, JavaKeyword.CURRENT_PACKAGE + Environment.getCurrentExchange() + ".services");
        javaClass.addMemberVariable(JavaClass.AccessType.PRIVATE, VariableFactory.createLocalVariable(RestfulOption.getType(), "option"));

        defauleConstructor = new JavaMethod(javaClass);
        defauleConstructor.getCode().appendWordsln("public", clientDescriptor + "() {");
        defauleConstructor.getCode().newBlock(() -> {
            defauleConstructor.getCode().appendStatementWordsln("this.option", "=", "new", RestfulOption.getType().getDescriptor() + "()");
            defauleConstructor.getCode().appendStatementWordsln("this.option.url", "=", defauleConstructor.getCode().toJavaString(url));
        });
        defauleConstructor.getCode().appendln("}");
        javaClass.addMethod(defauleConstructor.getCode());

        optionConstructor = new JavaMethod(javaClass);
        optionConstructor.getCode().appendWordsln("public", clientDescriptor + "(" + RestfulOption.getType().getDescriptor() + " option) {");
        optionConstructor.getCode().newBlock(() -> {
            optionConstructor.getCode().appendStatementWordsln("this.option ", "=", "option");
        });
        optionConstructor.getCode().appendln("}");
        javaClass.addMethod(optionConstructor.getCode());
    }

    @Override
    public RestfulInterfaceBuilder getRestfulInterfaceBuilder() {
        return new JavaRestfulInterfaceBuilder(javaClass);
    }

    @Override
    public void endClient() {
        CEIPath serviceFileFolder = CEIPath.appendPath(Environment.getExchangeFolder(), "services");
        javaClass.build(serviceFileFolder);
    }

}
