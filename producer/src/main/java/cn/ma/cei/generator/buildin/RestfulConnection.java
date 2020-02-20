package cn.ma.cei.generator.buildin;

import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.BuilderContext;

public class RestfulConnection {

    public final static String typeName = "RestfulConnection";

    public static VariableType getType() {
        return BuilderContext.variableType(typeName);
    }
}
