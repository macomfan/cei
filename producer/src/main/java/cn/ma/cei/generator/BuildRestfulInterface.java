package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.IRestfulInterfaceBuilder;
import cn.ma.cei.generator.buildin.RestfulConnection;
import cn.ma.cei.generator.buildin.RestfulRequest;
import cn.ma.cei.generator.buildin.RestfulResponse;
import cn.ma.cei.model.restful.xHeader;
import cn.ma.cei.model.restful.xInterface;
import cn.ma.cei.model.restful.xPostBody;
import cn.ma.cei.model.restful.xQuery;
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
                builder.defineRequest(request);
                builder.setRequestTarget(request, BuildAttributeExtension.createValueFromAttribute("target", restIf.request, builder));
                Variable requestMethod = GlobalContext.createStatement(Constant.requestMethod().tryGet(restIf.request.method));
                builder.setRequestMethod(request, requestMethod);
            });
            makeHeaders(restIf.request.headers, builder);
            makeQueryString(restIf.request.queryStrings, builder);
            makePostBody(restIf.request.postBody, request, builder);

            restIf.request.doBuild(() -> makeSignature(restIf.request.signature, request, builder));

            builder.onAddReference(RestfulConnection.getType());
            VariableType finalReturnType = returnType.get();
            restIf.response.doBuild(() -> {
                builder.invokeQuery(response, request);
                Variable returnVariable = BuildResponse.build(restIf.response, response, finalReturnType, builder.createDataProcessorBuilder());
                builder.returnResult(returnVariable);
            });
        }
        builder.endMethod();
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
            Variable var = BuildAttributeExtension.createValueFromAttribute("value", queryString, builder);
            Variable key = BuildAttributeExtension.createValueFromAttribute("key", queryString, builder);
            builder.addToQueryString(request, key, var);
        }));
    }

    private static void makePostBody(xPostBody postBody, Variable request, IRestfulInterfaceBuilder builder) {
        if (postBody == null) {
            return;
        }
        postBody.doBuild(() -> {
            Variable result = BuildAttributeExtension.createValueFromAttribute("value", postBody, builder);
            if (result != null) {
                builder.setPostBody(request, result);
            }
        });
    }

    private static void makeSignature(String signatureName, Variable request, IRestfulInterfaceBuilder builder) {
        if (signatureName != null && !signatureName.equals("")) {
            builder.invokeSignature(request, GlobalContext.getCurrentDescriptionConverter().getMethodDescriptor(signatureName));
        }
    }
}
