package cn.ma.cei.generator.buildin;

import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.BuilderContext;

public class RestfulResponse {

    public final static String typeName = "RestfulResponse";

    public static VariableType getType() {
        return BuilderContext.variableType(typeName);
    }
}
