package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IRestfulInterfaceBuilder;
import cn.ma.cei.generator.buildin.*;
import cn.ma.cei.model.restful.*;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.RegexHelper;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BuildRestfulInterface {

    public static void build(xInterface restIf, IRestfulInterfaceBuilder builder) {

        Variable request = GlobalContext.getCurrentMethod().createLocalVariable(RestfulRequest.getType(), "request");
        Variable response = GlobalContext.getCurrentMethod().createLocalVariable(RestfulResponse.getType(), "response");

        List<Variable> inputVariableList = new LinkedList<>();
        if (restIf.inputList != null) {
            restIf.inputList.forEach((input) -> input.doBuild(() -> {
                Variable inputVariable = GlobalContext.getCurrentMethod().createInputVariable(input.getType(), input.name);
                inputVariableList.add(inputVariable);
            }));
        }

        AtomicReference<VariableType> returnType = new AtomicReference<>();
        if (restIf.response != null) {
            restIf.response.doBuild(() -> {
                returnType.set(BuildResponse.getReturnType(restIf.response));
                GlobalContext.getCurrentMethod().setReturnType(returnType.get());
            });
        }

        builder.startMethod(returnType.get(), GlobalContext.getCurrentDescriptionConverter().getMethodDescriptor(restIf.name), inputVariableList);
        {
            restIf.request.doBuild(() -> {
                Variable option = GlobalContext.getCurrentMethod().queryVariable("{option}");
                builder.defineRequest(request, option);
                builder.setRequestTarget(request, BuildUserProcedure.createValueFromProcedure(restIf.request.target, restIf.request, builder));
                Variable requestMethod = GlobalContext.createStatement(Constant.requestMethod().get(restIf.request.method));
                builder.setRequestMethod(request, requestMethod);
            });
            makeHeaders(restIf.request.headers, builder);
            makeQueryString(restIf.request.queryStrings, builder);
            makePostBody(restIf.request.postBody, request, builder);

            restIf.request.doBuild(() -> makeAuthentication(restIf.request.authentication, request, builder));

            builder.onAddReference(RestfulConnection.getType());
            VariableType finalReturnType = returnType.get();
            restIf.response.doBuild(() -> {
                builder.invokeQuery(response, request);
                IDataProcessorBuilder dataProcessorBuilder = Checker.checkNull(builder.createDataProcessorBuilder(), builder, "DataProcessorBuilder");
                Variable returnVariable = BuildResponse.build(restIf.response, response, finalReturnType, dataProcessorBuilder);
                builder.returnResult(returnVariable);
            });
        }
        builder.endMethod(null);
    }

    private static void makeHeaders(List<xHeader> headers, IRestfulInterfaceBuilder builder) {
        if (headers == null) {
            return;
        }
        Variable request = GlobalContext.getCurrentMethod().getVariable("request");
        headers.forEach((header) -> header.doBuild(() -> {
            Variable var;
            String value = RegexHelper.isReference(header.value);
            if (value == null) {
                var = GlobalContext.createStringConstant(header.value);
            } else {
                var = GlobalContext.getCurrentMethod().getVariable(value);
                if (var == null) {
                    throw new CEIException("Cannot lookup variable: " + var);
                }
            }
            Variable tag = GlobalContext.createStringConstant(header.tag);
            builder.addHeader(request, tag, var);
        }));
    }

    private static void makeQueryString(List<xQuery> queryStrings, IRestfulInterfaceBuilder builder) {
        if (queryStrings == null) {
            return;
        }
        Variable request = GlobalContext.getCurrentMethod().getVariable("request");
        queryStrings.forEach((queryString) -> queryString.doBuild(() -> {
            Variable var = BuildUserProcedure.createValueFromProcedure(queryString.value, queryString, builder);
            Variable key = BuildUserProcedure.createValueFromProcedure(queryString.key, queryString, builder);
            builder.addToQueryString(request, key, var);
        }));
    }

    private static void makePostBody(xPostBody postBody, Variable request, IRestfulInterfaceBuilder builder) {
        if (postBody == null) {
            return;
        }
        postBody.doBuild(() -> {
            Variable result = BuildUserProcedure.createValueFromProcedure(xString.inst.getType(), postBody.value, postBody, builder);
            if (result != null) {
                builder.setPostBody(request, result);
            }
        });
    }

    private static void makeAuthentication(xAuthentication authentication, Variable request, IRestfulInterfaceBuilder builder) {
        if (authentication != null && !Checker.isEmpty(authentication.name)) {

            VariableType procedureType = Procedures.getType();
            sMethod authenticationMethod = procedureType.getMethod(authentication.name);
            if (authenticationMethod == null) {
                CEIErrors.showXMLFailure(authentication, "Cannot find method: %s", authentication.name);
                return;
            }
            List<Variable> inputs = authenticationMethod.getInputVariableList();
            if (Checker.isNull(inputs) || inputs.size() < 2
                    || inputs.get(0).getType() != RestfulRequest.getType() || inputs.get(1).getType() != RestfulOptions.getType()) {
                CEIErrors.showXMLFailure(authentication, "%s cannot be the authentication function, it must defines 2 inputs, one is RestfulRequest, another is RestfulOptions");
            }
            Variable option = GlobalContext.getCurrentMethod().queryVariable("{option}");

            IDataProcessorBuilder dataProcessorBuilder =
                    Checker.checkNull(builder.createDataProcessorBuilder(), builder, "DataProcessorBuilder");
            dataProcessorBuilder.invokeFunction(authentication.name, null, request, option);
        }
    }
}
