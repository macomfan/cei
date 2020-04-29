package cn.ma.cei.generator.buildin;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.VariableType;

public class WebSocketEvent {
    public final static String typeName = "WebSocketEvent";

    public static VariableType getType() {
        return BuilderContext.variableType(typeName);
    }
}
