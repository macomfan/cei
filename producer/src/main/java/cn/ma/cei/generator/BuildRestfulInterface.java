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

public class BuildRestfulInterface {

    public static void build(xInterface restIf, IRestfulInterfaceBuilder builder) {

        Variable request = GlobalContext.getCurrentMethod().createLocalVariable(RestfulRequest.getType(), "request");
        Variable response = GlobalContext.getCurrentMethod().createLocalVariable(RestfulResponse.getType(), "response");

        List<Variable> inputVariableList = new LinkedList<>();
        if (restIf.inputList != null) {
            restIf.inputList.forEach((input) -> {
                input.startBuilding();
                Variable inputVariable = GlobalContext.getCurrentMethod().createInputVariable(input.getType(), input.name);
                inputVariableList.add(inputVariable);
                input.endBuilding();
            });
        }

        VariableType returnType = null;
        if (restIf.response != null) {
            restIf.response.startBuilding();
            returnType = BuildResponse.getReturnType(restIf.response);
            GlobalContext.getCurrentMethod().setReturnType(returnType);
            restIf.response.endBuilding();
        }


        builder.startMethod(returnType, GlobalContext.getCurrentDescriptionConverter().getMethodDescriptor(restIf.name), inputVariableList);
        {
            builder.defineRequest(request);
            builder.setRequestTarget(request, BuildAttributeExtension.createValueFromAttribute("target", restIf.request, builder));

            Variable requestMethod = GlobalContext.createStatement(Constant.requestMethod().tryGet(restIf.request.method));
            builder.setRequestMethod(request, requestMethod);
            makeHeaders(restIf.request.headers, builder);
            makeQueryString(restIf.request.queryStrings, builder);
            makePostBody(restIf.request.postBody, request, builder);
            makeSignature(restIf.request.signature, request, builder);
            builder.onAddReference(RestfulConnection.getType());
            builder.invokeQuery(response, request);
            Variable returnVariable = BuildResponse.build(restIf.response, response, returnType, builder);
            builder.returnResult(returnVariable);
        }
        builder.endMethod();
    }

    private static void makeHeaders(List<xHeader> headers, IRestfulInterfaceBuilder builder) {
        if (headers == null) {
            return;
        }
        Variable request = GlobalContext.getCurrentMethod().getVariable("request");
        headers.forEach((header) -> {
            header.startBuilding();
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
            header.endBuilding();
        });
    }

    private static void makeQueryString(List<xQuery> queryStrings, IRestfulInterfaceBuilder builder) {
        if (queryStrings == null) {
            return;
        }
        Variable request = GlobalContext.getCurrentMethod().getVariable("request");
        queryStrings.forEach((queryString) -> {
            queryString.startBuilding();
            Variable var = BuildAttributeExtension.createValueFromAttribute("value", queryString, builder);
            Variable key = BuildAttributeExtension.createValueFromAttribute("key", queryString, builder);
            builder.addToQueryString(request, key, var);
            queryString.endBuilding();
        });
    }

    private static void makePostBody(xPostBody postBody, Variable request, IRestfulInterfaceBuilder builder) {
        if (postBody != null) {
            postBody.startBuilding();
            Variable result = BuildAttributeExtension.createValueFromAttribute("value", postBody, builder);
            if (result != null) {
                builder.setPostBody(request, result);
            }
            postBody.endBuilding();
        }
    }

    private static void makeSignature(String signatureName, Variable request, IRestfulInterfaceBuilder builder) {
        if (signatureName != null && !signatureName.equals("")) {
            builder.invokeSignature(request, GlobalContext.getCurrentDescriptionConverter().getMethodDescriptor(signatureName));
        }
    }
}
