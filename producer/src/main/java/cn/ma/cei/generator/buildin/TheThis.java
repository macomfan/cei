package cn.ma.cei.generator.buildin;

import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.environment.VariableType;

public class TheThis {
    public final static String typeName = "this";

    public static VariableType getType() {
        return VariableFactory.variableType(typeName);
    }
}
