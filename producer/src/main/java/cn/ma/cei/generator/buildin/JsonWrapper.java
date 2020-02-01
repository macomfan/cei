package cn.ma.cei.generator.buildin;

import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.environment.VariableFactory;

public class JsonWrapper {

    public final static String typeName = "JsonWrapper";

    public static VariableType getType() {
        return VariableFactory.variableType(typeName);
    }
}
