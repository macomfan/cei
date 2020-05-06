package cn.ma.cei.langs.golang;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.IMethod;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.*;
import cn.ma.cei.langs.golang.processor.GoGetNowBuilder;
import cn.ma.cei.langs.golang.processor.GoJsonBuilderBuilder;
import cn.ma.cei.langs.golang.processor.GoJsonParserBuilder;
import cn.ma.cei.langs.golang.processor.GoStringBuilderBuilder;
import cn.ma.cei.langs.golang.tools.GoMethod;
import cn.ma.cei.langs.golang.vars.GoVar;

public class GoDataProcessorBuilder implements IDataProcessorBuilder {

    GoMethod method;

    public GoDataProcessorBuilder(GoMethod method) {
        this.method = method;
    }

    @Override
    public void onAddReference(VariableType variableType) {
        method.addReference(variableType);
    }

    @Override
    public IJsonBuilderBuilder createJsonBuilderBuilder() {
        return new GoJsonBuilderBuilder(method);
    }

    @Override
    public IStringBuilderBuilder createStringBuilderBuilder() {
        return new GoStringBuilderBuilder(method);
    }

    @Override
    public IJsonParserBuilder createJsonParserBuilder() {
        return new GoJsonParserBuilder(method);
    }

    @Override
    public IGetNowBuilder createGetNowBuilder() {
        return new GoGetNowBuilder(method);
    }


    @Override
    public Variable convertJsonWrapperToString(Variable jsonWrapper) {
        return BuilderContext.createStatement(jsonWrapper.getDescriptor() + ".ToJsonString()");
    }

    @Override
    public Variable convertStringWrapperToString(Variable stringWrapper) {
        return BuilderContext.createStatement(stringWrapper.getDescriptor() + ".ToString()");
    }

    @Override
    public Variable convertStringWrapperToArray(Variable stringWrapper) {
        return null;
    }

    @Override
    public Variable stringReplacement(Variable... items) {
        method.addReference("fmt");
        return BuilderContext.createStatement(method.invoke("fmt.Sprintf", method.varListToArray(items)));
    }

    @Override
    public String getStringFormatEntity(int index, Variable item) {
        return "%s";
    }

    @Override
    public Variable convertIntToString(Variable intVariable) {
        method.addReference("../../impl");
        return BuilderContext.createStatement(method.invoke("impl.ToString",
                method.var(intVariable)));
    }

    @Override
    public Variable convertResponseToString(Variable response) {
        return BuilderContext.createStatement(method.invoke(response.getDescriptor() + ".GetString"));
    }

    @Override
    public Variable convertResponseToStream(Variable msg) {
        return BuilderContext.createStatement(method.invoke(msg.getDescriptor() + ".GetBytes"));
    }

    @Override
    public Variable convertDecimalToString(Variable decimalVariable) {
        method.addReference("../../impl");
        return BuilderContext.createStatement(method.invoke("impl.ToFloat64",
                method.var(decimalVariable)));
    }

    @Override
    public Variable convertBooleanToString(Variable booleanVariable) {
        method.addReference("../../impl");
        return BuilderContext.createStatement(method.invoke("impl.ToBool",
                method.var(booleanVariable)));
    }

    @Override
    public Variable convertNativeToDecimal(Variable stringVariable) {
        return stringVariable;
    }

    @Override
    public void upgradeWebSocketMessage(Variable messageVariable, Variable valueVariable) {

    }

    @Override
    public void base64(Variable output, Variable input) {
        method.addAssignAndDeclare(method.useVariable(method.var(output)), method.invoke("impl.Base64Encode", method.var(input)));
    }

    @Override
    public void hmacsha265(Variable output, Variable input, Variable key) {
        method.addAssignAndDeclare(method.useVariable(method.var(output)), method.invoke("impl.HMACSHA256", method.var(input), method.var(key)));
    }

    @Override
    public void combineQueryString(Variable requestVariable, Variable output, Variable sort, Variable separator) {
        method.addAssignAndDeclare(method.useVariable(method.var(output)),
                method.invoke("impl.CombineQueryString", method.var(requestVariable), method.var(sort), method.var(separator)));
    }

    @Override
    public void getRequestInfo(Variable requestVariable, Variable output, Variable info, Variable convert) {
        method.addAssignAndDeclare(method.useVariable(method.var(output)),
                method.invoke("impl.GetRequestInfo", method.var(requestVariable), method.var(info), method.var(convert)));
    }

    @Override
    public void URLEscape(Variable output, Variable input) {
        method.addAssignAndDeclare(method.useVariable(method.var(output)), method.invoke("impl.url_escape", method.var(input)));

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
    public void addQueryString(Variable requestVariable, Variable key, Variable value) {
        method.addInvoke(requestVariable.getDescriptor() + ".AddQueryString", method.var(key), method.var(value));
    }
}
