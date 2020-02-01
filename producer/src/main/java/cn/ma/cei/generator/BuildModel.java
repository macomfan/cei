package cn.ma.cei.generator;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.builder.ModelBuilder;
import cn.ma.cei.model.xModel;

public class BuildModel {

    public static void build(xModel model, ModelBuilder builder) {
        String reference = builder.getReference(model.name);
        VariableFactory.registerModel(model.name, reference);

        VariableType modelType = VariableFactory.variableType(model.name);
        builder.setClassType(modelType);
        builder.startModel(modelType);

        model.memberList.forEach((item) -> {
            item.startBuilding();
            Variable member = builder.createMemberVariable(item.getType(), item.name);
            member.defaultValue = item.defaultValue;
            builder.registerMember(member);
            item.endBuilding();
        });
        builder.endModel();
    }
}
