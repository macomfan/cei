/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.ISignatureBuilder;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.buildin.RestfulRequest;
import cn.ma.cei.generator.buildin.SignatureTool;
import cn.ma.cei.generator.buildin.TheStream;
import cn.ma.cei.model.signature.xAddQueryString;
import cn.ma.cei.model.signature.xAddStringArray;
import cn.ma.cei.model.signature.xAppendToString;
import cn.ma.cei.model.signature.xBase64;
import cn.ma.cei.model.signature.xCombineQueryString;
import cn.ma.cei.model.signature.xCombineStringArray;
import cn.ma.cei.model.signature.xGetRequestInfo;
import cn.ma.cei.model.signature.xGetNow;
import cn.ma.cei.model.signature.xHmacsha256;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.model.types.xStringArray;
import cn.ma.cei.model.xSignature;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.RegexHelper;

/**
 *
 * @author U0151316
 */
public class BuildSignature {

    public static void build(xSignature signature, ISignatureBuilder builder) {
        if (builder == null) {
            throw new CEIException("[BuildSignature] SignatureBuilder is null");
        }
        if (Checker.isEmpty(signature.name)) {
            throw new CEIException("[BuildSignature] name is null");
        }
        Variable request = GlobalContext.getCurrentMethod().createInputVariable(RestfulRequest.getType(), "request");
        Variable options = GlobalContext.getCurrentMethod().createInputVariable(RestfulOptions.getType(), "options");

        builder.startMethod(null,
                GlobalContext.getCurrentMethod().getDescriptor(),
                GlobalContext.getCurrentMethod().getInputVariableList());
        signature.items.forEach(item -> {
            item.startBuilding();
            if (item instanceof xGetNow) {
                processGetNow((xGetNow) item, builder);
            } else if (item instanceof xAddQueryString) {
                processAddQueryString((xAddQueryString) item, request, builder);
            } else if (item instanceof xCombineQueryString) {
                processCombineQueryString((xCombineQueryString) item, request, builder);
            } else if (item instanceof xGetRequestInfo) {
                processGetRequestInfo((xGetRequestInfo) item, request, builder);
            } else if (item instanceof xAddStringArray) {
                processAppendStringArray((xAddStringArray) item, request, builder);
            } else if (item instanceof xCombineStringArray) {
                processCombineStringArray((xCombineStringArray) item, builder);
            } else if (item instanceof xBase64) {
                processBase64((xBase64) item, builder);
            } else if (item instanceof xHmacsha256) {
                processHmacsha256((xHmacsha256) item, builder);
            } else if (item instanceof xAppendToString) {
                processAppendToString((xAppendToString) item, builder);
            }
            item.endBuilding();
        });
        builder.endMethod();
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

    public static void processGetNow(xGetNow getNow, ISignatureBuilder builder) {
        if (Checker.isEmpty(getNow.output)) {
            throw new CEIException("[BuildSignature] output must be defined for get_now");
        }
        Variable output = GlobalContext.getCurrentMethod().createLocalVariable(xString.inst.getType(), RegexHelper.isReference(getNow.output));
        Variable format = queryVariable(getNow.format);
        builder.getNow(output, format);
    }

    public static void processAddQueryString(xAddQueryString appendQueryString, Variable requestVariable, ISignatureBuilder builder) {
        if (Checker.isEmpty(appendQueryString.key) || Checker.isEmpty(appendQueryString.value)) {
            throw new CEIException("[BuildSignature] key and value must be defined for append_query_string");
        }
        Variable variable = queryVariable(appendQueryString.value);
        Variable key = queryVariable(appendQueryString.key);
        builder.addQueryString(requestVariable, key, variable);
    }

    public static void processCombineQueryString(xCombineQueryString combineQueryString, Variable requestVariable, ISignatureBuilder builder) {
        if (Checker.isEmpty(combineQueryString.output)) {
            throw new CEIException("[BuildSignature] output must be defined for CombineQueryString");
        }
        Variable output = GlobalContext.getCurrentMethod().createLocalVariable(xString.inst.getType(), RegexHelper.isReference(combineQueryString.output));

        Variable sort;
        if (!Checker.isEmpty(combineQueryString.sort)) {
            sort = GlobalContext.createStatement(Constant.signatureMethod().tryGet(combineQueryString.sort));
        } else {
            sort = GlobalContext.createStatement(Constant.signatureMethod().tryGet(SignatureTool.Constant.NONE));
        }
        Variable separator = queryVariable(combineQueryString.separator);
        builder.combineQueryString(requestVariable, output, sort, separator);
    }

    public static void processGetRequestInfo(xGetRequestInfo getRequestInfo, Variable requestVariable, ISignatureBuilder builder) {
        if (Checker.isEmpty(getRequestInfo.output)) {
            throw new CEIException("[BuildSignature] output must be defined for GetRequestInfo");
        }
        if (Checker.isEmpty(getRequestInfo.info)) {
            throw new CEIException("[BuildSignature] info must be defined for GetRequestInfo");
        }
        Variable output = GlobalContext.getCurrentMethod().createLocalVariable(xString.inst.getType(), RegexHelper.isReference(getRequestInfo.output));

        Variable info;
        if (!Checker.isEmpty(getRequestInfo.info)) {
            info = GlobalContext.createStatement(Constant.signatureMethod().tryGet(getRequestInfo.info));
        } else {
            info = GlobalContext.createStatement(Constant.signatureMethod().tryGet(SignatureTool.Constant.NONE));
        }
        Variable convert;
        if (!Checker.isEmpty(getRequestInfo.convert)) {
            convert = GlobalContext.createStatement(Constant.signatureMethod().tryGet(getRequestInfo.convert));
        } else {
            convert = GlobalContext.createStatement(Constant.signatureMethod().tryGet(SignatureTool.Constant.NONE));
        }
        builder.getRequestInfo(requestVariable, output, info, convert);
    }

    public static void processAppendStringArray(xAddStringArray appendStringArray, Variable requestVariable, ISignatureBuilder builder) {
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

    public static void processCombineStringArray(xCombineStringArray combineQueryString, ISignatureBuilder builder) {
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

    public static void processAppendToString(xAppendToString appendToString, ISignatureBuilder builder) {
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
            output =  GlobalContext.getCurrentMethod().createLocalVariable(xString.inst.getType(), RegexHelper.isReference(appendToString.output));
            builder.appendToString(true, output, input);
        } else {
            builder.appendToString(false, output, input);
        }
    }

    public static void processBase64(xBase64 base64, ISignatureBuilder builder) {
        if (Checker.isEmpty(base64.output)) {
            throw new CEIException("[BuildSignature] output must be defined for base64");
        }
        if (Checker.isEmpty(base64.input)) {
            throw new CEIException("[BuildSignature] input must be defined for base64");
        }
        Variable input = queryVariable(base64.input);
        Variable output = GlobalContext.getCurrentMethod().createLocalVariable(xString.inst.getType(), RegexHelper.isReference(base64.output));
        builder.base64(output, input);
    }

    public static void processHmacsha256(xHmacsha256 hmacsha256, ISignatureBuilder builder) {
        if (Checker.isEmpty(hmacsha256.output)) {
            throw new CEIException("[BuildSignature] output must be defined for hmacsha256");
        }
        if (Checker.isEmpty(hmacsha256.input)) {
            throw new CEIException("[BuildSignature] input must be defined for hmacsha256");
        }
        if (Checker.isEmpty(hmacsha256.key)) {
            throw new CEIException("[BuildSignature] key must be defined for hmacsha256");
        }
        Variable input = queryVariable(hmacsha256.input);
        Variable key = queryVariable(hmacsha256.key);
        Variable output =  GlobalContext.getCurrentMethod().createLocalVariable(TheStream.getType(), RegexHelper.isReference(hmacsha256.output));
        builder.hmacsha265(output, input, key);
    }
}
