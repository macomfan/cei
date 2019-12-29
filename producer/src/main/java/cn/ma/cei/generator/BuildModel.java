package cn.ma.cei.generator;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.builder.ModelBuilder;
import cn.ma.cei.generator.environment.Reference;
import cn.ma.cei.model.xModel;

public class BuildModel {

    public static void build(xModel model, ModelBuilder builder) {
        VariableType modelType = VariableFactory.variableType(model.name);
        // TODO
        // How to set reference
        Reference.getTypeDescriptor(modelType);
        String reference = builder.getRefrerence(modelType);
        Reference.addReference(modelType, reference);
        builder.startModel(modelType);
        model.memberList.forEach((item) -> {
            Variable member = VariableFactory.createMemberVariable(modelType, item.getType(), item.name);
            member.defaultValue = item.defaultValue;
            builder.registerMember(member);
        });
        builder.endModel();
    }
}
