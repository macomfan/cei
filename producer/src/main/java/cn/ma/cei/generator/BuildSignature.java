/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.SignatureBuilder;
import cn.ma.cei.generator.buildin.RestfulOptions;
import cn.ma.cei.generator.buildin.RestfulRequest;
import cn.ma.cei.generator.buildin.SignatureTool;
import cn.ma.cei.generator.buildin.TheStream;
import cn.ma.cei.generator.environment.Constant;
import cn.ma.cei.generator.environment.Environment;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.environment.VariableType;
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

/**
 *
 * @author U0151316
 */
public class BuildSignature {

    public static void build(xSignature signature, SignatureBuilder builder) {
        if (Checker.isEmpty(signature.name)) {
            throw new CEIException("[BuildSignature] name is null");
        }
        Variable request = builder.newInputVariable(RestfulRequest.getType(), "{request}");
        Variable options = builder.newInputVariable(RestfulOptions.getType(), "{options}");

        builder.startMethod(null, Environment.getCurrentDescriptionConverter().getMethodDescriptor(signature.name), builder.getVariableList());
        signature.items.forEach(item -> {
            if (item instanceof xGetNow) {
                processGetNow((xGetNow) item, builder);
            } else if (item instanceof xAddQueryString) {
                processAppendQueryString((xAddQueryString) item, request, builder);
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
        });
        builder.endMethod();
    }

    private static Variable queryVariable(String name, SignatureBuilder builder) {
        Variable variable = builder.queryVariableAsParam(name);
        if (variable == null) {
            Variable options = builder.queryVariable("options");
            if (options == null) {
                throw new CEIException("[BuildSignature] Cannot query option");
            }
            String variableName = VariableFactory.isReference(name);
            variable = options.queryMember(variableName);
            if (variable == null) {
                throw new CEIException("[BuildSignature] cannot query variable: " + name);
            }
        }
        return variable;
    }

    public static void processGetNow(xGetNow getNow, SignatureBuilder builder) {
        if (Checker.isEmpty(getNow.output)) {
            throw new CEIException("[BuildSignature] output must be defined for get_now");
        }
        Variable output = builder.newLoaclVariable(xString.inst.getType(), getNow.output);
        Variable format = queryVariable(getNow.format, builder);
        builder.getNow(output, format);
    }

    public static void processAppendQueryString(xAddQueryString appendQueryString, Variable requestVariable, SignatureBuilder builder) {
        if (Checker.isEmpty(appendQueryString.key) || Checker.isEmpty(appendQueryString.value)) {
            throw new CEIException("[BuildSignature] key and value must be defined for append_query_string");
        }
        Variable variable = queryVariable(appendQueryString.value, builder);
        Variable key = queryVariable(appendQueryString.key, builder);
        builder.addQueryString(requestVariable, key, variable);
    }

    public static void processCombineQueryString(xCombineQueryString combineQueryString, Variable requestVariable, SignatureBuilder builder) {
        if (Checker.isEmpty(combineQueryString.output)) {
            throw new CEIException("[BuildSignature] output must be defined for CombineQueryString");
        }
        Variable output = builder.newLoaclVariable(xString.inst.getType(), combineQueryString.output);

        Variable sort;
        if (!Checker.isEmpty(combineQueryString.sort)) {
            sort = VariableFactory.createConstantVariable(Constant.signatureMethod().tryGet(combineQueryString.sort));
        } else {
            sort = VariableFactory.createConstantVariable(Constant.signatureMethod().tryGet(SignatureTool.Constant.NONE));
        }
        Variable separator = queryVariable(combineQueryString.separator, builder);
        builder.combineQueryString(requestVariable, output, sort, separator);
    }

    public static void processGetRequestInfo(xGetRequestInfo getRequestInfo, Variable requestVariable, SignatureBuilder builder) {
        if (Checker.isEmpty(getRequestInfo.output)) {
            throw new CEIException("[BuildSignature] output must be defined for GetRequestInfo");
        }
        if (Checker.isEmpty(getRequestInfo.info)) {
            throw new CEIException("[BuildSignature] info must be defined for GetRequestInfo");
        }
        Variable output = builder.newLoaclVariable(xString.inst.getType(), getRequestInfo.output);

        Variable info;
        if (!Checker.isEmpty(getRequestInfo.info)) {
            info = VariableFactory.createConstantVariable(Constant.signatureMethod().tryGet(getRequestInfo.info));
        } else {
            info = VariableFactory.createConstantVariable(Constant.signatureMethod().tryGet(SignatureTool.Constant.NONE));
        }
        Variable convert;
        if (!Checker.isEmpty(getRequestInfo.convert)) {
            convert = VariableFactory.createConstantVariable(Constant.signatureMethod().tryGet(getRequestInfo.convert));
        } else {
            convert = VariableFactory.createConstantVariable(Constant.signatureMethod().tryGet(SignatureTool.Constant.NONE));
        }
        builder.getRequestInfo(requestVariable, output, info, convert);
    }

    public static void processAppendStringArray(xAddStringArray appendStringArray, Variable requestVariable, SignatureBuilder builder) {
        if (Checker.isEmpty(appendStringArray.output)) {
            throw new CEIException("[BuildSignature] output must be defined for AppendStringArray");
        }
        if (Checker.isEmpty(appendStringArray.input)) {
            throw new CEIException("[BuildSignature] input must be defined for AppendStringArray");
        }
        String outputName = VariableFactory.isReference(appendStringArray.output);
        if (outputName == null) {
            throw new CEIException("[BuildSignature] outpup must be {} in AppendStringArray");
        }
        Variable output = builder.queryVariable(outputName);
        if (output == null) {
            output = builder.newLoaclVariable(xStringArray.inst.getType(), appendStringArray.output);
            builder.newStringArray(output);
        }

        Variable input = queryVariable(appendStringArray.input, builder);
        builder.addStringArray(output, input);
    }

    public static void processCombineStringArray(xCombineStringArray combineQueryString, SignatureBuilder builder) {
        if (Checker.isEmpty(combineQueryString.output)) {
            throw new CEIException("[BuildSignature] output must be defined for CombineStringArray");
        }
        if (Checker.isEmpty(combineQueryString.input)) {
            throw new CEIException("[BuildSignature] input must be defined for CombineStringArray");
        }
        Variable input = queryVariable(combineQueryString.input, builder);
        Variable output = builder.newLoaclVariable(xString.inst.getType(), combineQueryString.output);
        Variable separator = queryVariable(combineQueryString.separator, builder);
        builder.combineStringArray(output, input, separator);

    }

    public static void processAppendToString(xAppendToString appendToString, SignatureBuilder builder) {
        if (Checker.isEmpty(appendToString.output)) {
            throw new CEIException("[BuildSignature] output must be defined for AppendToString");
        }
        if (Checker.isEmpty(appendToString.input)) {
            throw new CEIException("[BuildSignature] input must be defined for AppendToString");
        }

        String outputName = VariableFactory.isReference(appendToString.output);
        if (outputName == null) {
            throw new CEIException("[BuildSignature] outpup must be {} in AppendStringArray");
        }
        Variable input = queryVariable(appendToString.input, builder);
        Variable output = builder.queryVariable(outputName);
        if (output == null) {
            output = builder.newLoaclVariable(xString.inst.getType(), appendToString.output);
            builder.appendToString(true, output, input);
        } else {
            builder.appendToString(false, output, input);
        }
    }

    public static void processBase64(xBase64 base64, SignatureBuilder builder) {
        if (Checker.isEmpty(base64.output)) {
            throw new CEIException("[BuildSignature] output must be defined for base64");
        }
        if (Checker.isEmpty(base64.input)) {
            throw new CEIException("[BuildSignature] input must be defined for base64");
        }
        Variable input = queryVariable(base64.input, builder);
        Variable output = builder.newLoaclVariable(xString.inst.getType(), base64.output);
        builder.base64(output, input);
    }

    public static void processHmacsha256(xHmacsha256 hmacsha256, SignatureBuilder builder) {
        if (Checker.isEmpty(hmacsha256.output)) {
            throw new CEIException("[BuildSignature] output must be defined for hmacsha256");
        }
        if (Checker.isEmpty(hmacsha256.input)) {
            throw new CEIException("[BuildSignature] input must be defined for hmacsha256");
        }
        if (Checker.isEmpty(hmacsha256.key)) {
            throw new CEIException("[BuildSignature] key must be defined for hmacsha256");
        }
        Variable input = queryVariable(hmacsha256.input, builder);
        Variable key = queryVariable(hmacsha256.key, builder);
        Variable output = builder.newLoaclVariable(TheStream.getType(), hmacsha256.output);
        builder.hmacsha265(output, input, key);
    }
}
