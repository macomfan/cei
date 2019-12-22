package cn.ma.cei.generator;

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
        if (response.type.equals("raw")) {
            return VariableFactory.variableType(RestfulResponse.typeName);
        } else if (response.type.equals("string")) {
            return VariableFactory.variableType(xString.typeName);
        } else if (response.type.equals("model")) {
            if (response.jsonParser != null) {
                return BuildJsonParser.getModelType(response.jsonParser);
            }
            throw new CEIException("[BuildResponse] Cannot decided the return type");
        } else {
            throw new CEIException("[BuildResponse] Error response type");
        }
    }

    public static Variable build(xResponse response, Variable responseVariable, RestfulInterfaceBuilder interfaceBuilder) {
        if (interfaceBuilder == null) {
            throw new CEIException("BuildResponse interfaceBuilder is null");
        }
        ResponseBuilder responseBuilder = interfaceBuilder.getResponseBuilder();
        if (response.jsonParser != null) {
            JsonParserBuilder jsonParserBuilder = interfaceBuilder.getResponseBuilder().getJsonParserBuilder();
            return BuildJsonParser.build(response.jsonParser, responseVariable, jsonParserBuilder, interfaceBuilder);
        }
        //BuildJsonParser.build(response.jsonParser, );
        return null;
    }
}
