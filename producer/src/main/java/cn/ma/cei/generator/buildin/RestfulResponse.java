package cn.ma.cei.generator.buildin;

import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.database.VariableFactory;

public class RestfulResponse {

    public final static String typeName = "RestfulResponse";

    public static VariableType getType() {
        return VariableFactory.variableType(typeName);
    }
}
