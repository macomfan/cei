package cn.ma.cei.generator.buildin;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.VariableType;

public class RestfulResponse {

    public final static String typeName = "RestfulResponse";

    public static VariableType getType() {
        return BuilderContext.variableType(typeName);
    }
}
