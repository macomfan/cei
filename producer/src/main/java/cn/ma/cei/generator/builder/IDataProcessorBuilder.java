package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.IMethod;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;

/**
 * To implement the method to call the user defined processor.
 * For some simple processors, just use one method to implement。
 * For the complex processor, such as json parse, get now. will be implemented in another builder.
 *
 */
public interface IDataProcessorBuilder extends IBuilderBase {

    void onAddReference(VariableType variableType);

    /**
     * Create the JsonBuilderBuilder, it will be created for each <json_builder></json_builder>
     *
     * @return The builder instance, cannot be null.
     */
    IJsonBuilderBuilder createJsonBuilderBuilder();

    /**
     * Create the StringBuilderBuilder, it will be created for each <string_builder></string_builder>
     *
     * @return The builder instance, cannot be null.
     */
    IStringBuilderBuilder createStringBuilderBuilder();

    /**
     * Create the JsonParserBuilder, it will be created for each <json_parser></json_parser>
     *
     * @return The builder instance, cannot be null.
     */
    IJsonParserBuilder createJsonParserBuilder();


    /**
     * Create the GetNowBuilder, it will be created for each <get_now></get_now>
     *
     * @return The builder instance, cannot be null.
     */
    IGetNowBuilder createGetNowBuilder();

    /**
     * Encode the input to base64
     * e.g.
     * output = base64(input)
     *
     * @param output The output, be String type.
     * @param input The input, be TheStream type.
     */
    void base64(Variable output, Variable input);

    /**
     * Encode the input to by HMACSHA256.
     * e.g.
     * output = hmacsha256(input, key)
     *
     * @param output The output, be String type.
     * @param input The input, be TheStream type.
     * @param key The private key.
     */
    void hmacsha265(Variable output, Variable input, Variable key);

    /**
     * Add the query string to restful request. No output.
     * e.g.
     * addQueryString(requestVariable, key, value)
     *
     * @param requestVariable The request defined by <request></request>.
     * @param key The key.
     * @param value The value.
     */
    void addQueryString(Variable requestVariable, Variable key, Variable value);

    /**
     * Add the header string to restful request. No output.
     * e.g.
     * addHeaderString(requestVariable, key, value)
     *
     * @param requestVariable The request defined by <request></request>.
     * @param key The key.
     * @param value The value.
     */
    void addHeaderString(Variable requestVariable, Variable key, Variable value);

    /**
     * Combine the query strings to a signal string.
     * e.g.
     * output = combineQueryString(requestVariable, sort, separator)
     *
     * @param requestVariable The request defined by <request></request>.
     * @param output The output, be String type.
     * @param sort The sort value.
     * @param separator The separator.
     */
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
    Variable convertResponseToString(Variable response);

    /***
     *
     *
     * @return
     */
    Variable convertResponseToStream(Variable msg);

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

    /***
     *
     *
     * @return
     */
    Variable convertStreamToString(Variable streamVariable);

    /***
     *
     *
     * @return
     */
    Variable convertNativeToDecimal(Variable stringVariable);

    void upgradeWebSocketMessage(Variable messageVariable, Variable valueVariable);
}
