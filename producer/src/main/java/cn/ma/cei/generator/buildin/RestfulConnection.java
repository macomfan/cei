package cn.ma.cei.generator.buildin;

import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.database.VariableFactory;

public class RestfulConnection {

    public final static String typeName = "RestfulConnection";

    public static VariableType getType() {
        return VariableFactory.variableType(typeName);
    }
}
