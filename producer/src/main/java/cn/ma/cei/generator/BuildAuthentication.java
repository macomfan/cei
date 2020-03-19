/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.IAuthenticationBuilder;
import cn.ma.cei.generator.buildin.AuthenticationTool;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.buildin.RestfulRequest;
import cn.ma.cei.model.authentication.*;
import cn.ma.cei.model.restful.xAuthentication;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.model.types.xStringArray;
import cn.ma.cei.model.websocket.xWSAuthentication;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.RegexHelper;

/**
 * @author U0151316
 */
public class BuildAuthentication {

    public static void buildRestful(xAuthentication authentication, IAuthenticationBuilder builder) {
        Checker.isNull(builder, BuildAuthentication.class, "AuthenticationBuilder");


        Variable request = GlobalContext.getCurrentMethod().createInputVariable(RestfulRequest.getType(), "request");
        Variable options = GlobalContext.getCurrentMethod().createInputVariable(RestfulOptions.getType(), "options");

        builder.startMethod(null,
                GlobalContext.getCurrentMethod().getDescriptor(),
                GlobalContext.getCurrentMethod().getInputVariableList());

        BuildDataProcessor.build(authentication.items, builder.createDataProcessorBuilder(), item -> {
            if (item instanceof xGetRequestInfo) {
                processGetRequestInfo((xGetRequestInfo) item, request, builder);
            } else if (item instanceof xCombineQueryString) {
                processCombineQueryString((xCombineQueryString) item, request, builder);
            } else if (item instanceof xAddQueryString) {
                processAddQueryString((xAddQueryString) item, request, builder);
            }
        });


//        authentication.items.forEach(item -> item.doBuild(() -> {
//            if (item instanceof xGetNow) {
//                processGetNow((xGetNow) item, builder);
//            } else if (item instanceof xAddQueryString) {
//
//            } else if (item instanceof xCombineQueryString) {
//
//            } else if (item instanceof xGetRequestInfo) {
//
//            } else if (item instanceof xAddStringArray) {
//                processAppendStringArray((xAddStringArray) item, request, builder);
//            } else if (item instanceof xCombineStringArray) {
//                processCombineStringArray((xCombineStringArray) item, builder);
//            } else if (item instanceof xBase64) {
//                processBase64((xBase64) item, builder);
//            } else if (item instanceof xHmacsha256) {
//                processHmacsha256((xHmacsha256) item, builder);
//            } else if (item instanceof xAppendToString) {
//                processAppendToString((xAppendToString) item, builder);
//            }
//        }));
        builder.endMethod();
    }

    public static void buildWebSocket(xWSAuthentication authentication, IAuthenticationBuilder builder) {

    }

    private static Variable queryVariable(String name) {
        Variable variable = GlobalContext.getCurrentMethod().getVariableAsParam(name);
        if (variable == null) {
            Variable options = GlobalContext.getCurrentMethod().getVariable("options");
            if (options == null) {
                throw new CEIException("[BuildSignature] Cannot query option");
            }
            String variableName = RegexHelper.isReference(name);
            variable = options.getMember(variableName);
            if (variable == null) {
                throw new CEIException("[BuildSignature] cannot query variable: " + name);
            }
        }
        return variable;
    }

    public static void processAddQueryString(xAddQueryString appendQueryString, Variable requestVariable, IAuthenticationBuilder builder) {
        if (Checker.isEmpty(appendQueryString.key) || Checker.isEmpty(appendQueryString.value)) {
            throw new CEIException("[BuildSignature] key and value must be defined for append_query_string");
        }
        Variable variable = queryVariable(appendQueryString.value);
        Variable key = queryVariable(appendQueryString.key);
        builder.addQueryString(requestVariable, key, variable);
    }

    public static void processCombineQueryString(xCombineQueryString combineQueryString, Variable requestVariable, IAuthenticationBuilder builder) {
        if (Checker.isEmpty(combineQueryString.output)) {
            throw new CEIException("[BuildSignature] output must be defined for CombineQueryString");
        }
        Variable output = GlobalContext.getCurrentMethod().createLocalVariable(xString.inst.getType(), RegexHelper.isReference(combineQueryString.output));

        Variable sort;
        if (!Checker.isEmpty(combineQueryString.sort)) {
            sort = GlobalContext.createStatement(Constant.authenticationMethod().tryGet(combineQueryString.sort));
        } else {
            sort = GlobalContext.createStatement(Constant.authenticationMethod().tryGet(AuthenticationTool.Constant.NONE));
        }
        Variable separator = queryVariable(combineQueryString.separator);
        builder.combineQueryString(requestVariable, output, sort, separator);
    }

    public static void processGetRequestInfo(xGetRequestInfo getRequestInfo, Variable requestVariable, IAuthenticationBuilder builder) {
        if (Checker.isEmpty(getRequestInfo.output)) {
            throw new CEIException("[BuildSignature] output must be defined for GetRequestInfo");
        }
        if (Checker.isEmpty(getRequestInfo.info)) {
            throw new CEIException("[BuildSignature] info must be defined for GetRequestInfo");
        }
        Variable output = GlobalContext.getCurrentMethod().createLocalVariable(xString.inst.getType(), RegexHelper.isReference(getRequestInfo.output));

        Variable info;
        if (!Checker.isEmpty(getRequestInfo.info)) {
            info = GlobalContext.createStatement(Constant.authenticationMethod().tryGet(getRequestInfo.info));
        } else {
            info = GlobalContext.createStatement(Constant.authenticationMethod().tryGet(AuthenticationTool.Constant.NONE));
        }
        Variable convert;
        if (!Checker.isEmpty(getRequestInfo.convert)) {
            convert = GlobalContext.createStatement(Constant.authenticationMethod().tryGet(getRequestInfo.convert));
        } else {
            convert = GlobalContext.createStatement(Constant.authenticationMethod().tryGet(AuthenticationTool.Constant.NONE));
        }
        builder.getRequestInfo(requestVariable, output, info, convert);
    }

    public static void processAppendStringArray(xAddStringArray appendStringArray, Variable requestVariable, IAuthenticationBuilder builder) {
        if (Checker.isEmpty(appendStringArray.output)) {
            throw new CEIException("[BuildSignature] output must be defined for AppendStringArray");
        }
        if (Checker.isEmpty(appendStringArray.input)) {
            throw new CEIException("[BuildSignature] input must be defined for AppendStringArray");
        }
        String outputName = RegexHelper.isReference(appendStringArray.output);
        if (outputName == null) {
            throw new CEIException("[BuildSignature] outpup must be {} in AppendStringArray");
        }
        Variable output = GlobalContext.getCurrentMethod().getVariable(outputName);
        if (output == null) {
            output = GlobalContext.getCurrentMethod().createLocalVariable(xStringArray.inst.getType(), RegexHelper.isReference(appendStringArray.output));
            builder.newStringArray(output);
        }

        Variable input = queryVariable(appendStringArray.input);
        builder.addStringArray(output, input);
    }

    public static void processCombineStringArray(xCombineStringArray combineQueryString, IAuthenticationBuilder builder) {
        if (Checker.isEmpty(combineQueryString.output)) {
            throw new CEIException("[BuildSignature] output must be defined for CombineStringArray");
        }
        if (Checker.isEmpty(combineQueryString.input)) {
            throw new CEIException("[BuildSignature] input must be defined for CombineStringArray");
        }
        Variable input = queryVariable(combineQueryString.input);
        Variable output = GlobalContext.getCurrentMethod().createLocalVariable(xString.inst.getType(), RegexHelper.isReference(combineQueryString.output));
        Variable separator = queryVariable(combineQueryString.separator);
        builder.combineStringArray(output, input, separator);

    }

    public static void processAppendToString(xAppendToString appendToString, IAuthenticationBuilder builder) {
        if (Checker.isEmpty(appendToString.output)) {
            throw new CEIException("[BuildSignature] output must be defined for AppendToString");
        }
        if (Checker.isEmpty(appendToString.input)) {
            throw new CEIException("[BuildSignature] input must be defined for AppendToString");
        }

        String outputName = RegexHelper.isReference(appendToString.output);
        if (outputName == null) {
            throw new CEIException("[BuildSignature] outpup must be {} in AppendStringArray");
        }
        Variable input = queryVariable(appendToString.input);
        Variable output = GlobalContext.getCurrentMethod().getVariable(outputName);
        if (output == null) {
            output = GlobalContext.getCurrentMethod().createLocalVariable(xString.inst.getType(), RegexHelper.isReference(appendToString.output));
            builder.appendToString(true, output, input);
        } else {
            builder.appendToString(false, output, input);
        }
    }


}
