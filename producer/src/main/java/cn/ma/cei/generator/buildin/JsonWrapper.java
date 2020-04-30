package cn.ma.cei.generator.buildin;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.VariableType;

public class JsonWrapper {

    public final static String typeName = "JsonWrapper";

    public static VariableType getType() {
        return BuilderContext.variableType(typeName);
    }
}
