package cn.ma.cei.generator;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.builder.ModelBuilder;
import cn.ma.cei.model.xModel;

public class BuildModel {

    public static void build(xModel model, ModelBuilder builder) {
        String reference = builder.getRefrerence(model.name);
        VariableFactory.registerModel(model.name, reference);

        VariableType modelType = VariableFactory.variableType(model.name);
        builder.startModel(modelType);

        model.memberList.forEach((item) -> {
            item.startBuilding();
            Variable member = VariableFactory.registerMemberVariable(modelType, item.getType(), item.name);
            System.out.println("TYPE: " + item.getType().getDescriptor());
            member.defaultValue = item.defaultValue;
            builder.registerMember(member);
            item.endBuilding();
        });
        builder.endModel();
    }
}
