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
import cn.ma.cei.generator.environment.Constant;
import cn.ma.cei.generator.environment.Environment;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.model.signature.xAppendQueryString;
import cn.ma.cei.model.signature.xAppendStringArray;
import cn.ma.cei.model.signature.xCombineQueryString;
import cn.ma.cei.model.signature.xGetRequestInfo;
import cn.ma.cei.model.signature.xGetNow;
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
        Variable request = VariableFactory.createInputVariable(RestfulRequest.getType(), "request");
        Variable options = VariableFactory.createInputVariable(RestfulOptions.getType(), "options");
        builder.registerVariable(request);
        builder.registerVariable(options);

        builder.startMethod(null, Environment.getCurrentDescriptionConverter().getMethodDescriptor(signature.name), builder.getVariableList());
        signature.items.forEach((item) -> {
            if (item instanceof xGetNow) {
                processGetNow((xGetNow) item, builder);
            } else if (item instanceof xAppendQueryString) {
                processAppendQueryString((xAppendQueryString) item, request, builder);
            } else if (item instanceof xCombineQueryString) {
                processCombineQueryString((xCombineQueryString) item, request, builder);
            } else if (item instanceof xGetRequestInfo) {
                processGetRequestInfo((xGetRequestInfo) item, request, builder);
            } else if (item instanceof xAppendStringArray) {
                processAppendStringArray((xAppendStringArray) item, request, builder);
            }
        });
        builder.endMethod();
    }

    private static Variable createVariable(VariableType type, String name, SignatureBuilder builder) {
        String variableName = VariableFactory.isReference(name);
        if (variableName == null) {
            throw new CEIException("[BuildSignature] The variable must be {}");
        }
        Variable variable = builder.queryVariable(variableName);
        if (variable != null) {
            throw new CEIException("[BuildSignature] The variable redefined");
        }
        variable = VariableFactory.createLocalVariable(type, variableName);
        builder.registerVariable(variable);
        return variable;
    }

    public static void processGetNow(xGetNow getNow, SignatureBuilder builder) {
        if (Checker.isEmpty(getNow.output)) {
            throw new CEIException("[BuildSignature] output must be defined for get_now");
        }
        Variable output = createVariable(xString.inst.getType(), getNow.output, builder);
        builder.getNow(output, getNow.format);
    }

    public static void processAppendQueryString(xAppendQueryString appendQueryString, Variable requestVariable, SignatureBuilder builder) {
        if (Checker.isEmpty(appendQueryString.key) || Checker.isEmpty(appendQueryString.value)) {
            throw new CEIException("[BuildSignature] key and value must be defined for append_query_string");
        }
        Variable variable;
        String variableName = VariableFactory.isReference(appendQueryString.value);
        if (variableName == null) {
            variable = VariableFactory.createHardcodeStringVariable(appendQueryString.value);
        } else {
            variable = builder.queryVariable(variableName);
            if (variable == null) {
                Variable options = builder.queryVariable("options");
                variable = options.queryMember(variableName);
                if (variable == null) {
                    throw new CEIException("[BuildSignature] cannot query variable");
                }
            }
            if (variable.type != xString.inst.getType()) {
                throw new CEIException("[BuildSignature] value must be string in append_query_string");
            }
        }
        builder.appendQueryString(requestVariable, appendQueryString.key, variable);
    }

    public static void processCombineQueryString(xCombineQueryString combineQueryString, Variable requestVariable, SignatureBuilder builder) {
        if (Checker.isEmpty(combineQueryString.output)) {
            throw new CEIException("[BuildSignature] output must be defined for CombineQueryString");
        }
        Variable output = createVariable(xString.inst.getType(), combineQueryString.output, builder);

        String sortDescriptor = Constant.signatureMethod().tryGet(SignatureTool.Constant.NONE);
        if (!Checker.isEmpty(combineQueryString.sort)) {
            sortDescriptor = Constant.signatureMethod().tryGet(combineQueryString.sort);
        }
        builder.combineQueryString(requestVariable, output, sortDescriptor, combineQueryString.separator);
    }

    public static void processGetRequestInfo(xGetRequestInfo getRequestInfo, Variable requestVariable, SignatureBuilder builder) {
        if (Checker.isEmpty(getRequestInfo.output)) {
            throw new CEIException("[BuildSignature] output must be defined for GetRequestInfo");
        }
        if (Checker.isEmpty(getRequestInfo.info)) {
            throw new CEIException("[BuildSignature] info must be defined for GetRequestInfo");
        }
        Variable output = createVariable(xString.inst.getType(), getRequestInfo.output, builder);

        String infoDescriptor = Constant.signatureMethod().tryGet(SignatureTool.Constant.NONE);
        if (!Checker.isEmpty(getRequestInfo.info)) {
            infoDescriptor = Constant.signatureMethod().tryGet(getRequestInfo.info);
        }
        String convertDescriptor = Constant.signatureMethod().tryGet(SignatureTool.Constant.NONE);
        if (!Checker.isEmpty(getRequestInfo.convert)) {
            convertDescriptor = Constant.signatureMethod().tryGet(getRequestInfo.convert);
        }
        builder.getRequestInfo(requestVariable, output, infoDescriptor, convertDescriptor);
    }

    public static void processAppendStringArray(xAppendStringArray appendStringArray, Variable requestVariable, SignatureBuilder builder) {
        if (Checker.isEmpty(appendStringArray.output)) {
            throw new CEIException("[BuildSignature] output must be defined for AppendStringArray");
        }
        if (Checker.isEmpty(appendStringArray.input)) {
            throw new CEIException("[BuildSignature] input must be defined for AppendStringArray");
        }
        Variable output = builder.queryVariable(appendStringArray.output);
        if (output == null) {
            output = VariableFactory.createLocalVariable(xStringArray.inst.getType(), appendStringArray.output);
            builder.registerVariable(output);
        }
    }
}
