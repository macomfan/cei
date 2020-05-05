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
import cn.ma.cei.langs.golang.tools.GoVar;

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
        return BuilderContext.createStatement(stringWrapper.getDescriptor() + ".ToNormalString()");
    }

    @Override
    public Variable convertStringWrapperToArray(Variable stringWrapper) {
        return null;
    }

    @Override
    public Variable stringReplacement(Variable... items) {
        method.addReference("fmt");
        return BuilderContext.createStatement(method.invoke("fmt.format", GoVar.toArray(items)));
    }

    @Override
    public String getStringFormatEntity(int index, Variable item) {
        return "%s";
    }

    @Override
    public Variable convertIntToString(Variable intVariable) {
        method.addReference("strconv");
        return BuilderContext.createStatement(method.invoke("strconv.FormatUint",
                new GoVar(intVariable),
                new GoVar(BuilderContext.createStatement("10"))));
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
        method.addReference("strconv");
        return BuilderContext.createStatement(method.invoke("strconv.FormatFloat",
                new GoVar(decimalVariable),
                new GoVar(BuilderContext.createStatement("10"))));
    }

    @Override
    public Variable convertBooleanToString(Variable booleanVariable) {
        method.addReference("strconv");
        return BuilderContext.createStatement(method.invoke("strconv.FormatBool",
                new GoVar(booleanVariable),
                new GoVar(BuilderContext.createStatement("10"))));
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
        method.addAssignAndDeclare(method.useVariable(new GoVar(output)), method.invoke("authentication.Base64", new GoVar(input)));
    }

    @Override
    public void hmacsha265(Variable output, Variable input, Variable key) {
        method.addAssignAndDeclare(method.useVariable(new GoVar(output)), method.invoke("authentication,hmacsha256", new GoVar(input), new GoVar(key)));
    }

    @Override
    public void combineQueryString(Variable requestVariable, Variable output, Variable sort, Variable separator) {
        method.addAssignAndDeclare(method.useVariable(new GoVar(output)),
                method.invoke("authentication.CombineQueryString", new GoVar(requestVariable), new GoVar(sort), new GoVar(separator)));
    }

    @Override
    public void getRequestInfo(Variable requestVariable, Variable output, Variable info, Variable convert) {
        method.addAssignAndDeclare(method.useVariable(new GoVar(output)),
                method.invoke("authentication.GetRequestInfo", new GoVar(requestVariable), new GoVar(info), new GoVar(convert)));
    }

    @Override
    public void URLEscape(Variable output, Variable input) {
        method.addAssignAndDeclare(method.useVariable(new GoVar(output)), method.invoke("ceiutils.url_escape", new GoVar(input)));

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
        method.addInvoke(requestVariable.getDescriptor() + ".AddQueryString", new GoVar(key), new GoVar(value));
    }
}
