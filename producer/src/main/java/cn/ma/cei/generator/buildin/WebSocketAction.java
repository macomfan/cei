package cn.ma.cei.generator.buildin;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.VariableType;

public class WebSocketAction {
    public final static String typeName = "WebSocketAction";

    public static VariableType getType() {
        return BuilderContext.variableType(typeName);
    }
}
