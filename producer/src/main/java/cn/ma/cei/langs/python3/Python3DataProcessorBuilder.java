package cn.ma.cei.langs.python3;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.IMethod;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.*;
import cn.ma.cei.generator.buildin.CEIUtils;
import cn.ma.cei.generator.buildin.Procedures;
import cn.ma.cei.langs.python3.processor.Python3GetNowBuilder;
import cn.ma.cei.langs.python3.processor.Python3JsonBuilderBuilder;
import cn.ma.cei.langs.python3.processor.Python3JsonParserBuilder;
import cn.ma.cei.langs.python3.processor.Python3StringBuilderBuilder;
import cn.ma.cei.langs.python3.tools.Python3Method;

public class Python3DataProcessorBuilder implements IDataProcessorBuilder {
    Python3Method method;

    public Python3DataProcessorBuilder(Python3Method method) {
        this.method = method;
    }

    @Override
    public void onAddReference(VariableType variableType) {
        method.addReference(variableType);
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
    public IGetNowBuilder createGetNowBuilder() {
        return new Python3GetNowBuilder(method);
    }

    @Override
    public Variable convertJsonWrapperToString(Variable jsonWrapper) {
        return BuilderContext.createStatement(jsonWrapper.getDescriptor() + ".to_json_string()");
    }

    @Override
    public Variable convertStringWrapperToString(Variable stringWrapper) {
        return BuilderContext.createStatement(stringWrapper.getDescriptor() + ".to_string()");
    }

    @Override
    public Variable convertStringWrapperToArray(Variable stringWrapper) {
        return null;
    }

    @Override
    public Variable stringReplacement(Variable... items) {
        method.addReference(CEIUtils.getType());
        return BuilderContext.createStatement(method.invoke("CEIUtils.string_replace", items));
    }

    @Override
    public String getStringFormatEntity(int index, Variable item) {
        return String.format("{%d}", index);
    }

    @Override
    public Variable convertIntToString(Variable intVariable) {
        return BuilderContext.createStatement(method.invoke("str", intVariable));
    }

    @Override
    public Variable convertResponseToString(Variable response) {
        return BuilderContext.createStatement(method.invoke(response.getDescriptor() +".get_string"));
    }

    @Override
    public Variable convertResponseToStream(Variable response) {
        return BuilderContext.createStatement(method.invoke(response.getDescriptor() +".get_bytes"));
    }

    @Override
    public Variable convertDecimalToString(Variable decimalVariable) {
        return BuilderContext.createStatement(method.invoke("str", decimalVariable));
    }

    @Override
    public Variable convertBooleanToString(Variable booleanVariable) {
        return BuilderContext.createStatement(method.invoke("str", booleanVariable));
    }

    @Override
    public Variable convertStreamToString(Variable streamVariable) {
        return BuilderContext.createStatement("str(" + streamVariable.getDescriptor()+ "encoding = \"utf8\")");
    }

    @Override
    public Variable convertNativeToDecimal(Variable stringVariable) {
        return BuilderContext.createStatement(method.invoke("float", stringVariable));
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
    public void encodeHex(Variable output, Variable input) {
        method.addAssign(method.defineVariable(output), method.invoke("CEIUtils.hex", input));
    }

    @Override
    public void addQueryString(Variable requestVariable, Variable key, Variable value) {
        method.addInvoke(requestVariable.getDescriptor() + ".add_query_string", key, value);
    }

    @Override
    public void addHeaderString(Variable requestVariable, Variable key, Variable value) {
        method.addInvoke(requestVariable.getDescriptor() + ".add_header_string", key, value);
    }

    @Override
    public void combineQueryString(Variable requestVariable, Variable output, Variable sort, Variable separator) {
        method.addAssign(method.defineVariable(output), method.invoke("CEIUtils.combine_query_string", requestVariable, sort, separator));
    }

    @Override
    public void getRequestInfo(Variable requestVariable, Variable output, Variable info, Variable convert) {
        method.addAssign(method.defineVariable(output), method.invoke("CEIUtils.get_request_info", requestVariable, info, convert));
    }

    @Override
    public void URLEscape(Variable output, Variable input) {
        method.addAssign(method.defineVariable(output), method.invoke("CEIUtils.url_escape", input));

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
        method.addInvoke(callback.getDescriptor(), params);
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
