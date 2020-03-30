package cn.ma.cei.generator.buildin;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.VariableType;

public class StringWrapper {
    public final static String typeName = "StringWrapper";

    public static VariableType getType() {
        return BuilderContext.variableType(typeName);
    }
}
