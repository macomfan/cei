package cn.ma.cei.generator;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.RestfulInterfaceBuilder;
import cn.ma.cei.generator.buildin.RestfulConnection;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.buildin.RestfulRequest;
import cn.ma.cei.generator.buildin.RestfulResponse;
import cn.ma.cei.generator.environment.Constant;
import cn.ma.cei.generator.environment.Environment;
import cn.ma.cei.model.xHeader;
import cn.ma.cei.model.xInterface;
import cn.ma.cei.model.xPostBody;
import cn.ma.cei.model.xQuery;
import cn.ma.cei.utils.Checker;
import java.util.LinkedList;
import java.util.List;

public class BuildRestfulInterface {

    public static void build(xInterface restIf, RestfulInterfaceBuilder builder) {
        // Should be the member variable
        Variable options = VariableFactory.createLocalVariable(RestfulOptions.getType(), "options");
        builder.registerVariable(options);

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

        List<Variable> inputVariableList = new LinkedList<>();
        builder.getVariableList().forEach((variable) -> {
            if (variable.position == Variable.Position.INPUT) {
                inputVariableList.add(variable);
            }
        });

        builder.startMethod(returnType, Environment.getCurrentDescriptionConverter().getMethodDescriptor(restIf.name), inputVariableList);
        {
            if (Checker.isEmpty(restIf.request.method)) {
                throw new CEIException("[BuildRestfulInterface] Method is null");
            }

            builder.defineRequest(request);
            builder.setUrl(request);
            builder.setRequestTarget(request, VariableFactory.createHardcodeStringVariable(restIf.request.target));

            Variable requestMethod = VariableFactory.createConstantVariable(Constant.requestMethod().tryGet(restIf.request.method));

            builder.setRequestMethod(request, requestMethod);
            makeHeaders(restIf.request.headers, builder);
            makeQueryString(restIf.request.queryStrings, builder);
            makePostBody(restIf.request.postBody, request, builder);
            makeSignature(restIf.request.signature, request, builder);
            builder.onAddReference(RestfulConnection.getType());
            builder.invokeQuery(response, request);
            Variable returnVariable = BuildResponse.build(restIf.response, response, builder);
            builder.returnResult(returnVariable);
        }
        builder.endMethod();
    }

    private static void makeHeaders(List<xHeader> headers, RestfulInterfaceBuilder builder) {
        if (headers == null) {
            return;
        }
        Variable request = builder.queryVariable("request");
        headers.forEach((header) -> {
            Variable var;
            String value = VariableFactory.isReference(header.value);
            if (value == null) {
                var = VariableFactory.createHardcodeStringVariable(header.value);
            } else {
                var = builder.queryVariable(value);
                if (var == null) {
                    throw new CEIException("Cannot lookup variable: " + var);
                }
            }
            Variable tag = VariableFactory.createHardcodeStringVariable(header.tag);
            builder.addHeader(request, tag, var);
        });
    }

    private static void makeQueryString(List<xQuery> queryStrings, RestfulInterfaceBuilder builder) {
        if (queryStrings == null) {
            return;
        }
        Variable request = builder.queryVariable("request");
        queryStrings.forEach((queryString) -> {

            Variable var;
            String value = VariableFactory.isReference(queryString.value);
            if (value == null) {
                var = VariableFactory.createHardcodeStringVariable(queryString.value);
            } else {
                var = builder.queryVariable(value);
                if (var == null) {
                    throw new CEIException("Cannot lookup variable: " + var);
                }
            }
            Variable queryStringName = VariableFactory.createHardcodeStringVariable(queryString.name);
            builder.addToQueryString(request, queryStringName, var);
        });
    }

    private static void makePostBody(xPostBody postBody, Variable request, RestfulInterfaceBuilder builder) {
        if (postBody != null && postBody.jsonBuilder != null) {
            Variable result = BuildJsonBuilder.build(postBody.jsonBuilder, builder.getJsonBuilderBuilder(), builder);
            if (result != null) {
                builder.setPostBody(request, result);
            }
        }
    }

    private static void makeSignature(String signatureName, Variable request, RestfulInterfaceBuilder builder) {
        if (signatureName != null && !signatureName.equals("")) {
            builder.invokeSignature(request, Environment.getCurrentDescriptionConverter().getMethodDescriptor(signatureName));
        }
    }
}
