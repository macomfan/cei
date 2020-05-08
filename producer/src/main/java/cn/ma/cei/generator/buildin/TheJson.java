package cn.ma.cei.generator.buildin;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.VariableType;

public class TheJson {
    public final static String typeName = "json";

    public static VariableType getType() {
        return BuilderContext.variableType(typeName, (VariableType) null);
    }
}
