package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.generator.DataProcessorBase;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.generator.buildin.WebSocketConnection;
import cn.ma.cei.model.processor.xWebSocketSend;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.utils.Checker;


public class BuildWebSocketSend extends DataProcessorBase<xWebSocketSend> {
    @Override
    public Variable build(xWebSocketSend item, IDataProcessorBuilder builder) {
        Variable connection;
        if (Checker.isEmpty(item.connection)) {
            connection = queryVariable("{connection}");
        } else {
            connection = queryVariable(item.connection);
        }
        if (connection == null) {
            CEIErrors.showXMLFailure("Cannot find the connection in send.");
            return null;
        }
        if (connection.getType() != WebSocketConnection.getType()) {
            CEIErrors.showXMLFailure("The connection is not WebSocketConnect type");
        }
        Variable value = queryVariableOrConstant(item.value, xString.inst.getType());
        builder.send(connection, value);
        return null;
    }

    @Override
    public VariableType returnType(xWebSocketSend item) {
        return null;
    }

    @Override
    public String resultVariableName(xWebSocketSend item) {
        return null;
    }
}
