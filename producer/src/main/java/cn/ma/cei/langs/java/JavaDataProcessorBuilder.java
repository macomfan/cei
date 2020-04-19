package cn.ma.cei.langs.java;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.*;
import cn.ma.cei.langs.java.processor.JavaGetNowBuilder;
import cn.ma.cei.langs.java.processor.JavaJsonBuilderBuilder;
import cn.ma.cei.langs.java.processor.JavaJsonParserBuilder;
import cn.ma.cei.langs.java.tools.JavaMethod;
import cn.ma.cei.model.types.xDecimal;
import cn.ma.cei.model.types.xInt;
import cn.ma.cei.model.types.xString;

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
    public Variable jsonWrapperToString(Variable jsonWrapper) {
        return BuilderContext.createStatement(jsonWrapper.getDescriptor() + ".toJsonString()");
    }

    @Override
    public Variable stringWrapperToString(Variable stringWrapper) {
        return BuilderContext.createStatement(stringWrapper.getDescriptor() + ".toNormalString()");
    }

    @Override
    public Variable stringReplacement(Variable... items) {
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
    public Variable convertRestfulResponseToString(Variable response) {
        return BuilderContext.createStatement(method.invoke(response.getDescriptor() + ".getString"));
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
}
