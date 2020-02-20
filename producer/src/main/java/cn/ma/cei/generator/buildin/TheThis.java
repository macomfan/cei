package cn.ma.cei.generator.buildin;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.VariableType;

public class TheThis {
    public final static String typeName = "this";

    public static VariableType getType() {
        return BuilderContext.variableType(typeName);
    }
}
