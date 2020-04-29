package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.IMethod;
import cn.ma.cei.generator.Variable;

public interface IDataProcessorBuilder extends IBuilderBase {
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

    void invokeFunction(IMethod methodInfo, Variable returnVariable, Variable... params);

    /***
     * Call the callback when the WebSocket event is triggered.
     * e.g.
     * callback.invoke(params)
     *
     * @param callback the callback variable in the input parameter
     * @param params the response should be the input parameter of the callback function
     */
    void invokeCallback(Variable callback,  Variable... params);

    /**
     * Send the variable to WebSocket.
     * e.g.
     * connection.send(value)
     *
     * @param connection the variable sent to the WebSocket
     * @param value the variable to send the WebSocket, it should string type
     */
    void send(Variable connection, Variable value);

    void gzip(Variable output, Variable input);

    /***
     * Return the statement of converting the json wrapper to string;
     * e.g.
     * return BuilderContext.createStatement(xString.inst.getType(), jsonWrapper.getDescriptor() + ".toJsonString()");
     *
     * @param jsonWrapper the json builder variable
     * @return the statement variable
     */
    Variable convertJsonWrapperToString(Variable jsonWrapper);

    /***
     * Return the statement of converting the string wrapper to string;
     * e.g.
     * return BuilderContext.createStatement(xString.inst.getType(), stringWrapper.getDescriptor() + ".toNormalString()");
     *
     * @param stringWrapper the json builder variable
     * @return the statement variable
     */
    Variable convertStringWrapperToString(Variable stringWrapper);

    Variable convertStringWrapperToArray(Variable stringWrapper);


    /***
     * Return the statement of calling the replacement function.
     * e.g.
     * return BuilderContext.createStatement("StringBuilder.replace(items)");
     *
     * @param items the input items, items[0] is the format string
     * @return the statement variable
     */
    Variable stringReplacement(Variable... items);

    String getStringFormatEntity(int index, Variable item);

    /***
     * Convert the int value to string.
     * e.g.
     * return BuilderContext.createStatement("Int.toString(intVariable)");
     *
     * @param intVariable
     * @return
     */
    Variable convertIntToString(Variable intVariable);


    /***
     *
     *
     * @return
     */
    Variable convertRestfulResponseToString(Variable response);

    /***
     *
     *
     * @return
     */
    Variable convertDecimalToString(Variable decimalVariable);

    /***
     *
     *
     * @return
     */
    Variable convertBooleanToString(Variable booleanVariable);
}
