package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIErrorType;
import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IJsonCheckerBuilder;
import cn.ma.cei.generator.buildin.RestfulResponse;
import cn.ma.cei.model.json.xJsonParser;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.model.xResponse;

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
            return BuildDataProcessor.getResultType(response.items, response.result);
        }
    }

    public static Variable build(xResponse response,
                                 Variable responseVariable, VariableType returnType, IDataProcessorBuilder dataProcessorBuilder) {
        if (response.items != null) {
            response.items.forEach(item -> {
                if (item instanceof xJsonParser) {
                    if (((xJsonParser) item).jsonChecker != null)
                        ((xJsonParser) item).jsonChecker.usedFor = IJsonCheckerBuilder.UsedFor.REPORT_ERROR;
                }
            });
            return BuildDataProcessor.build(response.items, responseVariable, "", dataProcessorBuilder);
        }
        return null;
    }
}
