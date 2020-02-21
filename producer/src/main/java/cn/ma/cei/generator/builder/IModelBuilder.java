package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;

public interface IModelBuilder {
    String getReference(String modelDescriptor);

    void startModel(VariableType modelType);

    void addMember(Variable variable);

    void endModel();
}
