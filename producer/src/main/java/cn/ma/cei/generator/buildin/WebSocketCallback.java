package cn.ma.cei.generator.buildin;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.VariableType;

public class WebSocketCallback {
    static {
        WebSocketCallback.getType().createMethod("invoke");
    }

    public final static String typeName = "WebSocketCallback";

    public static VariableType getType() {
        return BuilderContext.variableType(typeName);
    }
}
