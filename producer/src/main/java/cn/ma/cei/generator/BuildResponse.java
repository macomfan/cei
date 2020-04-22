package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIErrorType;
import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IJsonCheckerBuilder;
import cn.ma.cei.generator.buildin.RestfulResponse;
import cn.ma.cei.generator.dataprocessor.TypeConverter;
import cn.ma.cei.model.json.xJsonParser;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.model.xResponse;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.RegexHelper;

public class BuildResponse {

    public static VariableType getReturnType(xResponse response) {
        if (response.type != null) {
            if (response.type.equals("raw")) {
                return GlobalContext.variableType(RestfulResponse.typeName);
            } else if (response.type.equals("string")) {
                return GlobalContext.variableType(xString.typeName);
            } else {
                CEIErrors.showFailure(CEIErrorType.CODE, "Response type is invalid");
                return null;
            }
        } else {
            String returnVariableName = null;
            if (response.result != null) {
                returnVariableName = RegexHelper.isReference(response.result);
                if (Checker.isEmpty(returnVariableName)) {
                    CEIErrors.showXMLFailure(response, "Return variable should looks like {%s}", response.result);
                }
            }
            VariableType returnType = BuildDataProcessor.getResultType(response.items, returnVariableName);
            if (returnType == null) {
                if (response.items.size() == 1) {
                    CEIErrors.showXMLFailure(response.items.get(0), "Cannot get the return type");
                } else {
                    if (Checker.isEmpty(response.result)) {
                        CEIErrors.showXMLFailure(response, "Must define the return variable");
                    } else {
                        CEIErrors.showXMLFailure(response, "Cannot find the return variable %s", response.result);
                    }
                }
            }
            return returnType;
        }
    }

    public static Variable build(xResponse response,
                                 Variable responseVariable, VariableType returnType, IDataProcessorBuilder dataProcessorBuilder) {
        if (response.type != null && response.items != null) {
            CEIErrors.showXMLFailure(response, "The type is defined, cannot defined the process items");
        }
        if (response.items != null) {
            response.items.forEach(item -> {
                if (item instanceof xJsonParser) {
                    if (((xJsonParser) item).jsonChecker != null)
                        ((xJsonParser) item).jsonChecker.usedFor = IJsonCheckerBuilder.UsedFor.REPORT_ERROR;
                }
            });
            return BuildDataProcessor.build(response.items, responseVariable, "", dataProcessorBuilder);
        } else {
            if ("string".equals(response.type)) {
                return TypeConverter.convertType(responseVariable, xString.inst.getType(), dataProcessorBuilder);
            } else if ("raw".equals(response.type)) {
                return responseVariable;
            }
        }
        return null;
    }
}
