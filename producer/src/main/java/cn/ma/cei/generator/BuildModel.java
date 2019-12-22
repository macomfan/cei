package cn.ma.cei.generator;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.builder.ModelBuilder;
import cn.ma.cei.generator.environment.ModelInfo;
import cn.ma.cei.generator.environment.Reference;
import cn.ma.cei.model.xModel;

public class BuildModel {

    public static void build(xModel model, ModelBuilder builder) {
        VariableType modelType = VariableFactory.variableType(model.name);
        
        String referecne = builder.getRefrerence(modelType);
        Reference.addReference(modelType, referecne);
        builder.startModel(modelType);
        model.memberList.forEach((item) -> {
            Variable member = VariableFactory.createLocalVariable(item.getType(), item.name);
            ModelInfo.addMember(modelType, member.name, member);
            member.defaultValue = item.defaultValue;
            builder.registerMember(member);
        });
        builder.endModel();
    }
}
