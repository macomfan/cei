package cn.ma.cei.langs.java;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.IMethod;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.*;
import cn.ma.cei.generator.buildin.CEIUtils;
import cn.ma.cei.generator.buildin.Procedures;
import cn.ma.cei.langs.java.processor.JavaGetNowBuilder;
import cn.ma.cei.langs.java.processor.JavaJsonBuilderBuilder;
import cn.ma.cei.langs.java.processor.JavaJsonParserBuilder;
import cn.ma.cei.langs.java.processor.JavaStringBuilderBuilder;
import cn.ma.cei.langs.java.tools.JavaMethod;
import cn.ma.cei.model.types.xDecimal;

public class JavaDataProcessorBuilder implements IDataProcessorBuilder {
    JavaMethod method;

    public JavaDataProcessorBuilder(JavaMethod method) {
        this.method = method;
    }

    @Override
    public IJsonBuilderBuilder createJsonBuilderBuilder() {
        return new JavaJsonBuilderBuilder(method);
    }

    @Override
    public IStringBuilderBuilder createStringBuilderBuilder() {
        return new JavaStringBuilderBuilder(method);
    }

    @Override
    public IJsonParserBuilder createJsonParserBuilder() {
        return new JavaJsonParserBuilder(method);
    }

    @Override
    public IGetNowBuilder createGetNowBuilder() {
        return new JavaGetNowBuilder(method);
    }

    @Override
    public Variable convertJsonWrapperToString(Variable jsonWrapper) {
        return BuilderContext.createStatement(jsonWrapper.getDescriptor() + ".toJsonString()");
    }

    @Override
    public Variable convertStringWrapperToString(Variable stringWrapper) {
        return BuilderContext.createStatement(stringWrapper.getDescriptor() + ".toNormalString()");
    }

    @Override
    public Variable convertStringWrapperToArray(Variable stringWrapper) {
        return null;
    }

    @Override
    public Variable stringReplacement(Variable... items) {
        method.addReference(CEIUtils.getType());
        return BuilderContext.createStatement(method.invoke("CEIUtils.stringReplace", items));
    }

    @Override
    public String getStringFormatEntity(int index, Variable item) {
        return "%s";
    }

    @Override
    public Variable convertIntToString(Variable intVariable) {
        return BuilderContext.createStatement(method.invoke("Long.toString", intVariable));
    }

    @Override
    public Variable convertResponseToString(Variable response) {
        return BuilderContext.createStatement(method.invoke(response.getDescriptor() + ".getString"));
    }

    @Override
    public Variable convertResponseToStream(Variable msg) {
        return BuilderContext.createStatement(method.invoke(msg.getDescriptor() + ".getBytes"));
    }

    @Override
    public Variable convertDecimalToString(Variable decimalVariable) {
        return BuilderContext.createStatement(method.invoke(decimalVariable.getDescriptor() + ".toString"));
    }

    @Override
    public Variable convertBooleanToString(Variable booleanVariable) {
        return BuilderContext.createStatement(method.invoke(booleanVariable.getDescriptor() + ".toString"));
    }

    @Override
    public Variable convertStringToDecimal(Variable stringVariable) {
        return BuilderContext.createStatement(method.newInstance(xDecimal.inst.getType(), stringVariable));
    }

    @Override
    public void upgradeWebSocketMessage(Variable messageVariable, Variable valueVariable) {
        method.addInvoke(messageVariable.getDescriptor() + ".upgrade", valueVariable);
    }

    @Override
    public void base64(Variable output, Variable input) {
        method.addAssign(method.defineVariable(output), method.invoke("CEIUtils.base64", input));
    }

    @Override
    public void hmacsha265(Variable output, Variable input, Variable key) {
        method.addAssign(method.defineVariable(output), method.invoke("CEIUtils.hmacsha256", input, key));
    }

    @Override
    public void addQueryString(Variable requestVariable, Variable key, Variable value) {
        method.addInvoke(requestVariable.getDescriptor() + ".addQueryString", key, value);
    }

    @Override
    public void combineQueryString(Variable requestVariable, Variable output, Variable sort, Variable separator) {
        method.addAssign(method.defineVariable(output), method.invoke("CEIUtils.combineQueryString", requestVariable, sort, separator));
    }

    @Override
    public void getRequestInfo(Variable requestVariable, Variable output, Variable info, Variable convert) {
        method.addAssign(method.defineVariable(output), method.invoke("CEIUtils.getRequestInfo", requestVariable, info, convert));
    }

    @Override
    public void URLEscape(Variable output, Variable input) {
        method.addAssign(method.defineVariable(output), method.invoke("CEIUtils.urlEscape", input));
    }

    @Override
    public void invokeFunction(IMethod methodInfo, Variable returnVariable, Variable... params) {
        if (returnVariable == null) {
            method.addInvoke(Procedures.getType().getDescriptor() + "." + methodInfo.getDescriptor(), params);
        } else {
            method.addAssign(returnVariable.getDescriptor(), method.invoke(Procedures.getType().getDescriptor() + "." + methodInfo.getDescriptor(), params));
        }
    }

    @Override
    public void invokeCallback(Variable callback, Variable... params) {
        method.addInvoke(callback.getDescriptor() + ".invoke", params);
    }

    @Override
    public void send(Variable connection, Variable value) {
        method.addInvoke(connection.getDescriptor() + ".send", value);
    }


    @Override
    public void gzip(Variable output, Variable input) {
        method.addAssign(method.defineVariable(output), method.invoke("CEIUtils.gzip", input));
    }
}
