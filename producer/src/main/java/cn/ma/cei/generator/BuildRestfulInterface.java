package cn.ma.cei.generator;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableList;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.RestfulInterfaceBuilder;
import cn.ma.cei.generator.buildin.RestfulConnection;
import cn.ma.cei.generator.buildin.RestfulRequest;
import cn.ma.cei.generator.buildin.RestfulResponse;
import cn.ma.cei.generator.environment.Constant;
import cn.ma.cei.generator.environment.Naming;
import cn.ma.cei.model.xInterface;
import cn.ma.cei.model.xQueryStrings;

public class BuildRestfulInterface {

    public static void build(xInterface restIf, RestfulInterfaceBuilder builder) {
        builder.startInterface(Naming.get().getMethodDescriptor(restIf.name));
        Variable request = VariableFactory.createLocalVariable(RestfulRequest.getType(), "request");
        Variable response = VariableFactory.createLocalVariable(RestfulResponse.getType(), "response");
        builder.registerVariable(request);
        builder.registerVariable(response);
        if (restIf.inputList != null) {
            restIf.inputList.forEach((input) -> {
                builder.registerVariable(VariableFactory.createInputVariable(input.getType(), input.name));
            });
        }

        VariableType returnType = null;
        if (restIf.response != null) {
            returnType = BuildResponse.getReturnType(restIf.response);
        }

        VariableList inputVariableList = new VariableList();
        builder.getVariableList().getVariableList().forEach((variable) -> {
            if (variable.position == Variable.Position.INPUT) {
                inputVariableList.registerVariable(variable);
            }
        });

        builder.defineMethod(returnType, restIf.name, inputVariableList, () -> {
            builder.defineRequest(request);
            builder.setUrl(request);
            builder.setRequestTarget(request, restIf.request.target);
            if (restIf.request.method.equals("get")) {
                builder.setRequestMethod(request, Constant.requestMethod().tryGet(RestfulInterfaceBuilder.RequestMethod.GET));
            } else if (restIf.request.method.equals("post")) {
                builder.setRequestMethod(request, Constant.requestMethod().tryGet(RestfulInterfaceBuilder.RequestMethod.POST));
            }
            makeQueryString(restIf.request.queryStrings, builder);
            builder.onAddReference(RestfulConnection.getType());
            builder.invokeQuery(request, response);
            Variable returnVariable = BuildResponse.build(restIf.response, response, builder);
            builder.returnResult(returnVariable);
        });

        builder.endInterface();
    }

    private static void makeQueryString(xQueryStrings queryStrings, RestfulInterfaceBuilder builder) {
        if (queryStrings == null || queryStrings.queryList == null) {
            return;
        }
        Variable request = builder.queryVariable("request");
        queryStrings.queryList.forEach((queryString) -> {
            String value = VariableFactory.isReference(queryString.value);
            if (value == null) {
                builder.addToQueryStringByHardcode(request, queryString.name, queryString.value);
            } else {
                Variable var = builder.queryVariable(value);
                if (var == null) {
                    throw new CEIException("Cannot lookup variable: " + var);
                }
                builder.addToQueryStringByVariable(request, queryString.name, var);
            }
        });
    }

}
