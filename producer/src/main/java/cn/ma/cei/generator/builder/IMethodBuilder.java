package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;

import java.util.List;

public interface IMethodBuilder extends IBuilderBase {
    void onAddReference(VariableType variableType);

    /**
     * Start the function. the returnType can be null if no anything to be returned.
     *
     * @param returnType The type to be returned, null means no return
     * @param methodDescriptor The method name
     * @param params Parameters list, can be null if no parameters
     */
    void startMethod(VariableType returnType, String methodDescriptor, List<Variable> params);

    /**
     * Create the DataProcessorBuilder.
     * @return The instance of DataProcessorBuilder
     */
    IDataProcessorBuilder createDataProcessorBuilder();

    void endMethod(Variable returnVariable);

    // TODO
    // TO be deleted, instead of void endMethod(Variable returnVariable);
    void endMethod();
}
