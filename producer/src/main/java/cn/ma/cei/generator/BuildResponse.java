package cn.ma.cei.generator;

import cn.ma.cei.generator.builder.JsonCheckerBuilder;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.JsonParserBuilder;
import cn.ma.cei.generator.builder.ResponseBuilder;
import cn.ma.cei.generator.builder.RestfulInterfaceBuilder;
import cn.ma.cei.generator.buildin.RestfulResponse;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.model.xResponse;
import cn.ma.cei.model.types.xString;

public class BuildResponse {

    public static VariableType getReturnType(xResponse response) {
        if (response.model.equals("raw")) {
            return VariableFactory.variableType(RestfulResponse.typeName);
        } else if (response.model.equals("string")) {
            return VariableFactory.variableType(xString.typeName);
        } else {
            // Check model
            return VariableFactory.variableType(response.model);
        }
    }

    public static Variable build(xResponse response, Variable responseVariable, VariableType returnType, RestfulInterfaceBuilder interfaceBuilder) {
        if (interfaceBuilder == null) {
            throw new CEIException("BuildResponse interfaceBuilder is null");
        }
        ResponseBuilder responseBuilder = interfaceBuilder.getResponseBuilder();
        if (responseBuilder == null) {
            throw new CEIException("ResponseBuilder is null");
        }
        if (response.jsonParser != null) {
            response.jsonParser.startBuilding();
            JsonParserBuilder jsonParserBuilder = responseBuilder.getJsonParserBuilder();
            Variable returnVariable = BuildJsonParser.build(
                    response.jsonParser, responseVariable, returnType, jsonParserBuilder, interfaceBuilder, JsonCheckerBuilder.UsedFor.REPORT_ERROR);
            response.jsonParser.endBuilding();
            return returnVariable;
        }
        return null;
    }
}
