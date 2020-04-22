package cn.ma.cei.generator.buildin;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.VariableType;

public class Procedures {
    public final static String typeName = "Procedures";

    public static VariableType getType() {
        return BuilderContext.variableType(typeName);
    }
}
