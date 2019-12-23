package cn.ma.cei.generator.langs.cpp;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.builder.ModelBuilder;
import cn.ma.cei.generator.langs.cpp.tools.CppClass;

public class CppModelBuilder extends ModelBuilder {

    private CppClass cppClass = null;
    private String exchangeName;
    
    public CppModelBuilder(String exchangeName) {
        this.exchangeName = exchangeName;
    }
    

    @Override
    public void startModel(VariableType modelType) {
        cppClass = new CppClass(exchangeName, modelType.getDescriptor());
    }

    @Override
    public void registerMember(Variable variable) {
        cppClass.addMemberVariable(CppClass.AccessType.PUBLIC, variable);
    }

    @Override
    public void endModel() {
        cppClass.build();
    }

    @Override
    public String getRefrerence(VariableType modelType) {
        // TODO
        return modelType.getDescriptor();
    }
}
