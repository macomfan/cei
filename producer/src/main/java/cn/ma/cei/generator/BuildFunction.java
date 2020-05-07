package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IMethodBuilder;
import cn.ma.cei.model.xFunction;
import cn.ma.cei.utils.Checker;

public class BuildFunction {
    public static void build(xFunction function, IMethodBuilder builder) {
        if (!Checker.isNull(function.inputList)) {
            function.inputList.forEach(input -> {
                GlobalContext.getCurrentMethod().createInputVariable(input.getType(), input.name);
            });
        }

        VariableType returnType = null;
        if (!Checker.isEmpty(function.procedureReturn)) {
            returnType = BuildDataProcessor.getReturnType(function.implementation, function.procedureReturn);
//            if (returnType == null) {
//                Variable variable = GlobalContext.getCurrentMethod().queryVariable(function.procedureReturn);
//                if (variable == null) {
//                    CEIErrors.showXMLFailure("Cannot find return variable %s", function.procedureReturn);
//                    return;
//                }
//                returnType = variable.getType();
//            }
        }

        builder.startMethod(returnType,
                GlobalContext.getCurrentMethod().getDescriptor(),
                GlobalContext.getCurrentMethod().getInputVariableList());
        IDataProcessorBuilder dataProcessorBuilder = Checker.checkNull(builder.createDataProcessorBuilder(), builder, "DataProcessorBuilder");
        BuildDataProcessor.Context context = new BuildDataProcessor.Context();
        context.procedure = function.implementation;
        context.specifiedReturnType = returnType;
        context.dataProcessorBuilder = dataProcessorBuilder;
        context.returnVariableName = function.procedureReturn;

        BuildDataProcessor.build(context);
        Variable returnVariable = null;
        if (!Checker.isEmpty(function.procedureReturn)) {
            returnVariable = GlobalContext.getCurrentMethod().queryVariable(function.procedureReturn);
        }
        if (returnVariable != null) {
            builder.endMethod(returnVariable);
        } else {
            builder.endMethod();
        }

    }
}
