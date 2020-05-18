package cn.ma.cei.langs.cpp;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.IMethod;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.*;
import cn.ma.cei.generator.buildin.CEIUtils;
import cn.ma.cei.langs.cpp.processor.CppGetNowBuilder;
import cn.ma.cei.langs.cpp.processor.CppJsonBuilderBuilder;
import cn.ma.cei.langs.cpp.processor.CppJsonParserBuilder;
import cn.ma.cei.langs.cpp.processor.CppStringBuilderBuilder;
import cn.ma.cei.langs.cpp.tools.CppMethod;
import cn.ma.cei.model.types.xDecimal;

public class CppDataProcessorBuilder implements IDataProcessorBuilder {
    private CppMethod method;

    public CppDataProcessorBuilder(CppMethod method) {
        this.method = method;
    }

    @Override
    public void onAddReference(VariableType variableType) {

    }

    @Override
    public IJsonBuilderBuilder createJsonBuilderBuilder() {
        return new CppJsonBuilderBuilder();
    }

    @Override
    public IStringBuilderBuilder createStringBuilderBuilder() {
        return new CppStringBuilderBuilder();
    }

    @Override
    public IJsonParserBuilder createJsonParserBuilder() {
        return new CppJsonParserBuilder(method);
    }

    @Override
    public IGetNowBuilder createGetNowBuilder() {
        return new CppGetNowBuilder();
    }

    @Override
    public void base64(Variable output, Variable input) {

    }

    @Override
    public void hmacsha265(Variable output, Variable input, Variable key) {

    }

    @Override
    public void encodeHex(Variable output, Variable input) {

    }

    @Override
    public void addQueryString(Variable requestVariable, Variable key, Variable value) {

    }

    @Override
    public void addHeaderString(Variable requestVariable, Variable key, Variable value) {

    }

    @Override
    public void combineQueryString(Variable requestVariable, Variable output, Variable sort, Variable separator) {

    }

    @Override
    public void getRequestInfo(Variable requestVariable, Variable output, Variable info, Variable convert) {

    }

    @Override
    public void URLEscape(Variable output, Variable input) {

    }

    @Override
    public void invokeFunction(IMethod methodInfo, Variable returnVariable, Variable... params) {

    }

    @Override
    public void invokeCallback(Variable callback, Variable... params) {

    }

    @Override
    public void send(Variable connection, Variable value) {

    }

    @Override
    public void gzip(Variable output, Variable input) {

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
        return BuilderContext.createStatement(method.invoke("Long.toString", decimalVariable));
    }

    @Override
    public Variable convertBooleanToString(Variable booleanVariable) {
        return BuilderContext.createStatement(method.invoke("Long.toString", booleanVariable));
    }

    @Override
    public Variable convertStreamToString(Variable streamVariable) {
        return null;
    }

    @Override
    public Variable convertNativeToDecimal(Variable stringVariable) {
        // return BuilderContext.createStatement(method.newInstance(xDecimal.inst.getType(), stringVariable));
        return BuilderContext.createStatement("new");
    }

    @Override
    public void upgradeWebSocketMessage(Variable messageVariable, Variable valueVariable) {

    }
}
