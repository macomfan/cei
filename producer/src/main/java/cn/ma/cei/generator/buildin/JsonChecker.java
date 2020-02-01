package cn.ma.cei.generator.buildin;

import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.environment.VariableType;

public class JsonChecker {
    public final static String typeName = "JsonChecker";

    public static VariableType getType() {
        return VariableFactory.variableType(typeName);
    }
}
