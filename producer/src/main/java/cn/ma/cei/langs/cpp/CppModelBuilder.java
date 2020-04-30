package cn.ma.cei.langs.cpp;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IModelBuilder;
import cn.ma.cei.langs.cpp.tools.CppClass;

public class CppModelBuilder implements IModelBuilder {

    private CppClass cppClass = null;
    private final String exchangeName;
    
    public CppModelBuilder(String exchangeName) {
        this.exchangeName = exchangeName;
    }
    

    @Override
    public void startModel(VariableType modelType) {
        cppClass = new CppClass(exchangeName, modelType.getDescriptor());
    }

    @Override
    public void addMember(Variable variable) {
        cppClass.addMemberVariable(CppClass.AccessType.PUBLIC, variable);
    }

    @Override
    public void endModel() {
        cppClass.build();
    }

    @Override
    public String getReference(String modelName) {
        // TODO
        return modelName;
    }
}
