package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;

import java.util.List;

public interface IWebSocketImplementationBuilder extends IMethodBuilder {

    default void startMethod(VariableType returnType, String methodDescriptor, List<Variable> params) {}

    default void endMethod(Variable returnVariable) {}

    default void endMethod() {}

    /**
     * Send the variable to WebSocket.
     * e.g.
     * sendToWebSocket(send)
     *
     * @param send the variable sent to the WebSocket
     */
    void send(Variable send);

    /***
     * Call the callback when the WebSocket action is triggered.
     * e.g.
     * callbackVariable.getDescriptor() + ".invoke(response)
     *
     * @param callbackVariable the callback variable in the input parameter
     * @param response the response should be the input parameter of the callback function
     */
    void callback(Variable callbackVariable, Variable response);
}
