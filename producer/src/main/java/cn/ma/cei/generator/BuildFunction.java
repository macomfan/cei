package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IMethodBuilder;
import cn.ma.cei.model.xFunction;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.RegexHelper;

public class BuildFunction {
    public static void build(xFunction function, IMethodBuilder builder) {
        if (!Checker.isNull(function.inputList)) {
            function.inputList.forEach(input -> {
                GlobalContext.getCurrentMethod().createInputVariable(input.getType(), input.name);
            });
        }

        VariableType returnType = null;
        if (!Checker.isEmpty(function.procedureReturn)) {
            String returnVariableName = RegexHelper.isReference(function.procedureReturn);
            if (Checker.isEmpty(returnVariableName)) {
                CEIErrors.showXMLFailure(function, "Return variable should looks like {%s}", function.procedureReturn);
            }
            returnType = BuildDataProcessor.getResultType(function.implementation.items, returnVariableName);
            if (returnType == null) {
                Variable variable = GlobalContext.getCurrentMethod().getVariable(returnVariableName);
                if (variable == null) {
                    CEIErrors.showXMLFailure(function, "Cannot find return variable %s", function.procedureReturn);
                    return;
                }
                returnType = variable.getType();
            }
        }

        builder.startMethod(returnType,
                GlobalContext.getCurrentMethod().getDescriptor(),
                GlobalContext.getCurrentMethod().getInputVariableList());

        IDataProcessorBuilder processorBuilder = Checker.checkBuilder(builder.createDataProcessorBuilder(), builder.getClass(), "DataProcessorBuilder");
        BuildDataProcessor.build(function.implementation.items, processorBuilder);
        Variable returnVariable = null;
        if (!Checker.isEmpty(function.procedureReturn)) {
            returnVariable = GlobalContext.getCurrentMethod().queryVariable(function.procedureReturn);
        }
        builder.endMethod(returnVariable);
    }
}
