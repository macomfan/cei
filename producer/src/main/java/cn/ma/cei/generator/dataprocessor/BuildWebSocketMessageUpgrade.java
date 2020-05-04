package cn.ma.cei.generator.dataprocessor;

import cn.ma.cei.generator.DataProcessorBase;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.IDataProcessorBuilder;
import cn.ma.cei.model.processor.xWebSocketMessageUpgrade;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.generator.buildin.WebSocketMessage;

public class BuildWebSocketMessageUpgrade extends DataProcessorBase<xWebSocketMessageUpgrade> {
    @Override
    public Variable build(xWebSocketMessageUpgrade item, IDataProcessorBuilder builder) {
        Variable message = queryInputVariable(item.message, null, WebSocketMessage.getType());
        Variable value = queryVariableOrConstant(item.value, xString.inst.getType());
        builder.upgradeWebSocketMessage(message, value);
        return null;
    }

    @Override
    public VariableType returnType(xWebSocketMessageUpgrade item) {
        return null;
    }

    @Override
    public String resultVariableName(xWebSocketMessageUpgrade item) {
        return null;
    }
}
