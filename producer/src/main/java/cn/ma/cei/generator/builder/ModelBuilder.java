package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;

public abstract class ModelBuilder {
    public abstract String getRefrerence(VariableType modelType);

    public abstract void startModel(VariableType modelType);

    public abstract void registerMember(Variable variable);

    public abstract void endModel();
}
