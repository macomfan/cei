package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.builder.ModelBuilder;
import cn.ma.cei.generator.environment.Environment;
import cn.ma.cei.generator.environment.Reference;
import cn.ma.cei.generator.langs.java.tools.JavaClass;

public class JavaModelBuilder extends ModelBuilder {
    JavaClass javaClass = null;

    @Override
    public void startModel(VariableType modelType) {
        javaClass = new JavaClass(modelType.getDescriptor(), JavaKeyword.CURRENT_PACKAGE + Environment.getCurrentExchange() + ".models");
    }

    @Override
    public void registerMember(Variable variable) {
        javaClass.addMemberVariable(JavaClass.AccessType.PUBLIC, variable);
    }

    @Override
    public void endModel() {
        javaClass.build();
    }

    @Override
    public String getRefrerence(VariableType modelType) {
        return JavaKeyword.CURRENT_PACKAGE + Environment.getCurrentExchange() + ".models." + modelType.getDescriptor();
    }
}
