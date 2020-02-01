package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableType;

public abstract class ModelBuilder extends ClassBuilder {
    public abstract String getReference(String modelName);

    public abstract void startModel(VariableType modelType);

    public abstract void registerMember(Variable variable);

    public abstract void endModel();
}
