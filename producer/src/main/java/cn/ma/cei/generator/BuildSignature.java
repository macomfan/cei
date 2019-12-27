/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.SignatureBuilder;
import cn.ma.cei.generator.buildin.RestfulOption;
import cn.ma.cei.generator.buildin.RestfulRequest;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.model.signature.xAppendQueryString;
import cn.ma.cei.model.signature.xCombineQueryString;
import cn.ma.cei.model.signature.xGetNow;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.model.xSignature;
import cn.ma.cei.utils.Checker;

/**
 *
 * @author U0151316
 */
public class BuildSignature {

    public void build(xSignature signature, SignatureBuilder builder) {
        Variable request = VariableFactory.createInputVariable(RestfulRequest.getType(), "request");
        Variable option = VariableFactory.createInputVariable(RestfulOption.getType(), "option");
        builder.registerVariable(request);
        builder.registerVariable(option);
        signature.items.forEach((item) -> {
            if (item instanceof xGetNow) {

            } else if (item instanceof xAppendQueryString) {

            } else if (item instanceof xCombineQueryString) {

            }
        });
    }

    public void processGetNow(xGetNow getNow, SignatureBuilder builder) {
        if (Checker.isEmpty(getNow.output)) {
            throw new CEIException("[BuildSignature] output must be defined for get_now");
        }
        Variable output = VariableFactory.createLocalVariable(xString.inst.getType(), getNow.output);
        builder.registerVariable(output);
        builder.getNow(output, getNow.format);
    }

    public void processAppendQueryString(xAppendQueryString appendQueryString, Variable requestVariable, SignatureBuilder builder) {
        if (Checker.isEmpty(appendQueryString.key) || Checker.isEmpty(appendQueryString.value)) {
            throw new CEIException("[BuildSignature] key and value must be defined for append_query_string");
        }
        String variableName = VariableFactory.isReference(appendQueryString.value);
        if (variableName == null) {
            builder.appendQueryStringByHardcode(requestVariable, appendQueryString.key, appendQueryString.value);
            return;
        }
        Variable variable = builder.queryVariable(variableName);
        if (variable == null) {
            Variable option = builder.queryVariable("option");
            variable = option.queryMember(variableName);
            if (variable == null) {
                throw new CEIException("[BuildSignature] cannot query variable");
            }
        }
        if (variable.type != xString.inst.getType()) {
            throw new CEIException("[BuildSignature] value must be string in append_query_string");
        }
        builder.appendQueryStringByVariable(requestVariable, appendQueryString.key, variable);
    }

    public void processCombineQueryString(xCombineQueryString combineQueryString, Variable requestVariable, SignatureBuilder builder) {
        if (Checker.isEmpty(combineQueryString.output)) {
            throw new CEIException("[BuildSignature] output must be defined for CombineQueryString");
        }
        String outputName = VariableFactory.isReference(combineQueryString.output);
        if (outputName == null) {
            throw new CEIException("[BuildSignature] output must be variable");
        }
        Variable output = builder.queryVariable(combineQueryString.output);
        if (output == null) {
            output = VariableFactory.createLocalVariable(xString.inst.getType(), combineQueryString.output);
            builder.registerVariable(output);
        }
        builder.combineQueryString(requestVariable, output, combineQueryString.sort, combineQueryString.separator);
        
        
    }
}
