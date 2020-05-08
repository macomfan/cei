package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;

public interface IModelBuilder extends IBuilderBase {
    /**
     * Return the reference of the new model.
     * e.g.
     * For Java
     * If user define a model named PriceModel
     * The refresh will be "cn.ma.cei.PriceModel"
     *
     * @param modelDescriptor The model name defined by user
     * @return The reference string.
     */
    String getReference(String modelDescriptor);

    /**
     * Start build the model. The model class should be defined in this method
     *
     * @param modelType The model type.
     */
    void startModel(VariableType modelType);

    void addMember(Variable variable);

    void endModel();
}
