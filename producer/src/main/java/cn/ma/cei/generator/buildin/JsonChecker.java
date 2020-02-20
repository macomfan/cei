package cn.ma.cei.generator.buildin;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.VariableType;

public class JsonChecker {
    public final static String typeName = "JsonChecker";

    public static VariableType getType() {
        return BuilderContext.variableType(typeName);
    }
}
