package cn.ma.cei.generator.langs.java;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IJsonBuilderBuilder;
import cn.ma.cei.generator.builder.IJsonParserBuilder;
import cn.ma.cei.generator.builder.IStringBuilderBuilder;
import cn.ma.cei.generator.langs.java.tools.JavaMethod;
import cn.ma.cei.model.types.xString;

import java.nio.Buffer;

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
        return null;
    }

    @Override
    public IJsonParserBuilder createJsonParserBuilder() {
        return new JavaJsonParserBuilder(method);
    }

    @Override
    public Variable jsonWrapperToString(Variable jsonWrapper) {
        return BuilderContext.createStatement(xString.inst.getType(), jsonWrapper.getDescriptor() + ".toJsonString()");
    }

    @Override
    public Variable stringWrapperToString(Variable stringWrapper) {
        return BuilderContext.createStatement(xString.inst.getType(), stringWrapper.getDescriptor() + ".toNormalString()");
    }

    @Override
    public Variable stringReplacement(Variable... items) {
        return BuilderContext.createStatement(xString.inst.getType(), method.invoke("StringBuilder.replace", items));
    }

    @Override
    public void getNow(Variable output, Variable format) {
        method.addAssign(method.defineVariable(output), method.invoke("AuthenticationTool.getNow", format));
    }

    @Override
    public void base64(Variable output, Variable input) {
        method.addAssign(method.defineVariable(output), method.invoke("AuthenticationTool.base64", input));
    }

    @Override
    public void hmacsha265(Variable output, Variable input, Variable key) {
        method.addAssign(method.defineVariable(output), method.invoke("AuthenticationTool.hmacsha256", input, key));
    }

    @Override
    public void addQueryString(Variable requestVariable, Variable key, Variable value) {
        method.addInvoke(requestVariable.getDescriptor() + ".addQueryString", key, value);
    }

    @Override
    public void combineQueryString(Variable requestVariable, Variable output, Variable sort, Variable separator) {
        method.addAssign(method.defineVariable(output), method.invoke("AuthenticationTool.combineQueryString", requestVariable, sort, separator));
    }

    @Override
    public void getRequestInfo(Variable requestVariable, Variable output, Variable info, Variable convert) {
        method.addAssign(method.defineVariable(output), method.invoke("AuthenticationTool.getRequestInfo", requestVariable, info, convert));
    }
}
