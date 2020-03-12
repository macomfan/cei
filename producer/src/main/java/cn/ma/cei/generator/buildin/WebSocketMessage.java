package cn.ma.cei.generator.buildin;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.VariableType;

public class WebSocketMessage {
    public final static String typeName = "WebSocketMessage";

    public static VariableType getType() {
        return BuilderContext.variableType(typeName);
    }
}
