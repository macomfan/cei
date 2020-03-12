package cn.ma.cei.generator.buildin;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.VariableType;

public class WebSocketCallback {
    public final static String typeName = "WebSocketCallback";

    public static VariableType getType() {
        return BuilderContext.variableType(typeName);
    }
}
