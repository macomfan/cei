package cn.ma.cei.langs.golang;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.builder.*;
import cn.ma.cei.langs.golang.tools.GoMethod;
import cn.ma.cei.langs.golang.tools.GoVar;

public class GoDataProcessorBuilder implements IDataProcessorBuilder {

    GoMethod method;

    public GoDataProcessorBuilder(GoMethod method) {
        this.method = method;
    }

    @Override
    public IJsonBuilderBuilder createJsonBuilderBuilder() {
        return null;
    }

    @Override
    public IStringBuilderBuilder createStringBuilderBuilder() {
        return null;
    }

    @Override
    public IJsonParserBuilder createJsonParserBuilder() {
        return null;
    }

    @Override
    public IGetNowBuilder createGetNowBuilder() {
        return null;
    }



    @Override
    public Variable jsonWrapperToString(Variable jsonWrapper) {
        return null;
    }

    @Override
    public Variable stringWrapperToString(Variable stringWrapper) {
        return null;
    }

    @Override
    public Variable stringReplacement(Variable... items) {
        return null;
    }

    @Override
    public String getStringFormatEntity(int index, Variable item) {
        return null;
    }

    @Override
    public Variable convertIntToString(Variable intVariable) {
        return null;
    }

    @Override
    public Variable convertRestfulResponseToString(Variable response) {
        return null;
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
    public void invokeFunction(String methodName, Variable returnVariable, Variable... params) {

    }

    @Override
    public void gzip(Variable output, Variable input) {

    }

    @Override
    public void addQueryString(Variable requestVariable, Variable key, Variable value) {
        method.addInvoke(requestVariable.getDescriptor() + ".AddQueryString", new GoVar(key), new GoVar(value));
    }
}
