package cn.ma.cei.generator.buildin;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.VariableType;

public class WebSocketConnection {
    public final static String typeName = "WebSocketConnection";

    public static VariableType getType() {
        return BuilderContext.variableType(typeName);
    }
}
