package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.IModelBuilder;
import cn.ma.cei.model.xModel;

public class BuildModel {

    public static void build(xModel model, IModelBuilder builder) {
        if (builder == null) {
            throw new CEIException("[BuildModel] ModelBuilder is null");
        }
        String reference = builder.getReference(
                GlobalContext.getCurrentDescriptionConverter().getMethodDescriptor(model.name));
        GlobalContext.setupRunTimeVariableType(model.name, reference);
        VariableType modelType = GlobalContext.variableType(model.name);
        GlobalContext.setCurrentModel(modelType);
        builder.startModel(modelType);

        model.memberList.forEach(item -> item.doBuild(() -> {
            Variable member = modelType.addMember(item.getType(), item.name);
            builder.addMember(member);
        }));
        builder.endModel();
        GlobalContext.setCurrentModel(null);
    }
}
