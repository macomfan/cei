package cn.ma.cei.langs.cpp;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IModelBuilder;
import cn.ma.cei.langs.cpp.tools.CppClass;
import cn.ma.cei.langs.cpp.tools.CppExchangeFile;
import cn.ma.cei.langs.cpp.tools.CppStruct;

public class CppModelBuilder implements IModelBuilder {

    private CppClass cppClass = null;
    private final CppExchangeFile file;
    
    public CppModelBuilder(CppExchangeFile file) {
        this.file = file;
    }

    @Override
    public void startModel(VariableType modelType) {
        cppClass = new CppStruct(modelType.getDescriptor());
    }

    @Override
    public void addMember(Variable variable) {
        cppClass.addMemberVariable(CppClass.AccessType.PUBLIC, variable);
    }

    @Override
    public void endModel() {
        file.addClass(cppClass);
    }

    @Override
    public String getReference(String modelName) {
        return BuilderContext.NO_REF;
    }
}
