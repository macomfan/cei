package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.generator.DataProcessorBase;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.model.processor.xWebSocketCallback;
import cn.ma.cei.utils.Checker;

public class BuildWebSocketCallback extends DataProcessorBase<xWebSocketCallback> {
    @Override
    public Variable build(xWebSocketCallback item, IDataProcessorBuilder builder) {
        if (Checker.isEmpty(item.func)) {
            CEIErrors.showXMLFailure("callback is null in websocket_callback");
        }
        Variable callbackFunc = queryVariable(item.func);
        Variable argument;
        if (Checker.isEmpty(item.argument)) {
            argument = getDefaultInput();
        } else {
            argument = queryVariable(item.argument);
        }
        builder.invokeCallback(callbackFunc, argument);
        return null;
    }

    @Override
    public VariableType returnType(xWebSocketCallback item) {
        return null;
    }

    @Override
    public String resultVariableName(xWebSocketCallback item) {
        return null;
    }
}
