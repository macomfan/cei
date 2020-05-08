package cn.ma.cei.langs.java;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IModelBuilder;
import cn.ma.cei.langs.java.tools.JavaClass;

public class JavaModelBuilder implements IModelBuilder {
    
    private final JavaClass mainClass;
    private JavaClass modelClass;

    public JavaModelBuilder(JavaClass mainClass) {
        this.mainClass = mainClass;
    }
    
    @Override
    public void startModel(VariableType modelType) {
        modelClass = new JavaClass(modelType.getDescriptor());
    }

    @Override
    public void addMember(Variable variable) {
        modelClass.addMemberVariable(variable);
    }

    @Override
    public void endModel() {
        mainClass.addInnerClass(modelClass);
    }

    @Override
    public String getReference(String modelName) {
        return JavaCode.CURRENT_PACKAGE;
    }
}
