package cn.ma.cei.generator;

import cn.ma.cei.generator.database.VariableFactory;
import cn.ma.cei.generator.builder.ModelBuilder;
import cn.ma.cei.generator.database.ModelInfo;
import cn.ma.cei.generator.database.Reference;
import cn.ma.cei.model.xModel;

public class BuildModel {

    public static void build(xModel model, ModelBuilder builder) {
        VariableType modelType = VariableFactory.variableType(model.name);
        
        String referecne = builder.getRefrerence(modelType);
        Reference.addReference(modelType, referecne);
        Variable modelVariable = VariableFactory.createLocalVariable(modelType, "temp");
        builder.startModel(modelType);
        model.memberList.forEach((item) -> {
            Variable variable = VariableFactory.createMemberVariable(modelVariable, item.getType(), item.name);
            ModelInfo.addMember(modelType, variable.name, variable);
            variable.defaultValue = item.defaultValue;
            builder.registerMember(variable);
        });
        builder.endModel();
    }
}
