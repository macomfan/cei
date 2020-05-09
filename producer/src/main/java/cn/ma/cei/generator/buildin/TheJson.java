package cn.ma.cei.generator.buildin;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.VariableType;

/**
 * This is not the JsonWrapper, it is original json object supported by each language
 * e.g. In java, it is the fastjson, in Python, it is imported by "import json"
 */
public class TheJson {
    public final static String typeName = "json";

    public static VariableType getType() {
        return BuilderContext.variableType(typeName, (VariableType) null);
    }
}
