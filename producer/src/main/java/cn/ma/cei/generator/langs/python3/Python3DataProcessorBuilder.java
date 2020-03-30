package cn.ma.cei.generator.langs.python3;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.builder.IJsonBuilderBuilder;
import cn.ma.cei.generator.builder.IJsonParserBuilder;
import cn.ma.cei.generator.builder.IStringBuilderBuilder;
import cn.ma.cei.generator.langs.python3.tools.Python3Method;
import cn.ma.cei.model.types.xString;

public class Python3DataProcessorBuilder implements IDataProcessorBuilder {
    Python3Method method;

    public Python3DataProcessorBuilder(Python3Method method) {
        this.method = method;
    }

    @Override
    public IJsonBuilderBuilder createJsonBuilderBuilder() {
        return new Python3JsonBuilderBuilder(method);
    }

    @Override
    public IStringBuilderBuilder createStringBuilderBuilder() {
        return new Python3StringBuilderBuilder(method);
    }

    @Override
    public IJsonParserBuilder createJsonParserBuilder() {
        return new Python3JsonParserBuilder(method);
    }

    @Override
    public void getNow(Variable output, Variable format) {
        method.addAssign(method.defineVariable(output), method.invoke("CEIUtils.get_now", format));
    }

    @Override
    public Variable jsonWrapperToString(Variable jsonWrapper) {
        return BuilderContext.createStatement(jsonWrapper.getDescriptor() + ".to_json_string()");
    }

    @Override
    public Variable stringWrapperToString(Variable stringWrapper) {
        return BuilderContext.createStatement(stringWrapper.getDescriptor() + ".to_string()");
    }

    @Override
    public Variable stringReplacement(Variable... items) {
        return BuilderContext.createStatement(method.invoke("StringBuilder.replace", items));
    }

    @Override
    public Variable convertIntToString(Variable intVariable) {
        return null;
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
        method.addInvoke(requestVariable.getDescriptor() + ".add_query_string", key, value);
    }

    @Override
    public void combineQueryString(Variable requestVariable, Variable output, Variable sort, Variable separator) {
        method.addAssign(method.defineVariable(output), method.invoke("CEIUtils.combine_query_string", requestVariable, sort, separator));
    }

    @Override
    public void getRequestInfo(Variable requestVariable, Variable output, Variable info, Variable convert) {
        method.addAssign(method.defineVariable(output), method.invoke("CEIUtils.get_request_info", requestVariable, info, convert));
    }
}
