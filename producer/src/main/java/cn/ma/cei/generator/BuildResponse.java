package cn.ma.cei.generator;

import cn.ma.cei.generator.builder.JsonCheckerBuilder;
import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.IJsonParserBuilder;
import cn.ma.cei.generator.builder.IRestfulInterfaceBuilder;
import cn.ma.cei.generator.buildin.RestfulResponse;
import cn.ma.cei.model.xResponse;
import cn.ma.cei.model.types.xString;

public class BuildResponse {

    public static VariableType getReturnType(xResponse response) {
        if (response.model.equals("raw")) {
            return GlobalContext.variableType(RestfulResponse.typeName);
        } else if (response.model.equals("string")) {
            return GlobalContext.variableType(xString.typeName);
        } else {
            // Check model
            return GlobalContext.variableType(response.model);
        }
    }

    public static Variable build(xResponse response, Variable responseVariable, VariableType returnType, IRestfulInterfaceBuilder interfaceBuilder) {
        if (interfaceBuilder == null) {
            throw new CEIException("BuildResponse interfaceBuilder is null");
        }
        if (response.jsonParser != null) {
            response.jsonParser.startBuilding();
            IJsonParserBuilder jsonParserBuilder = interfaceBuilder.createJsonParserBuilder();
            Variable returnVariable = BuildJsonParser.build(
                    response.jsonParser, responseVariable, returnType, jsonParserBuilder, interfaceBuilder, JsonCheckerBuilder.UsedFor.REPORT_ERROR);
            response.jsonParser.endBuilding();
            return returnVariable;
        }
        return null;
    }
}
