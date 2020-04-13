package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.Variable;

public interface IDataProcessorBuilder {
    IJsonBuilderBuilder createJsonBuilderBuilder();

    IStringBuilderBuilder createStringBuilderBuilder();

    IJsonParserBuilder createJsonParserBuilder();

    IGetNowBuilder createGetNowBuilder();

    void base64(Variable output, Variable input);

    void hmacsha265(Variable output, Variable input, Variable key);

    void addQueryString(Variable requestVariable, Variable key, Variable value);

    void combineQueryString(Variable requestVariable, Variable output, Variable sort, Variable separator);

    void getRequestInfo(Variable requestVariable, Variable output, Variable info, Variable convert);

    void URLEscape(Variable output, Variable input);

    /***
     * Return the statement of converting the json wrapper to string;
     * e.g.
     * return BuilderContext.createStatement(xString.inst.getType(), jsonWrapper.getDescriptor() + ".toJsonString()");
     *
     * @param jsonWrapper the json builder variable
     * @return the statement variable
     */
    Variable jsonWrapperToString(Variable jsonWrapper);

    /***
     * Return the statement of converting the string wrapper to string;
     * e.g.
     * return BuilderContext.createStatement(xString.inst.getType(), stringWrapper.getDescriptor() + ".toNormalString()");
     *
     * @param stringWrapper the json builder variable
     * @return the statement variable
     */
    Variable stringWrapperToString(Variable stringWrapper);


    /***
     * Return the statement of calling the replacement function.
     * e.g.
     * return BuilderContext.createStatement("StringBuilder.replace(items)");
     *
     * @param items the input items
     * @return the statement variable
     */
    Variable stringReplacement(Variable... items);

    /***
     * Convert the int value to string.
     * e.g.
     * return BuilderContext.createStatement("Int.toString(intVariable)");
     *
     * @param intVariable
     * @return
     */
    Variable convertIntToString(Variable intVariable);
}
