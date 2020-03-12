package cn.ma.cei.generator.buildin;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.model.types.xString;

public class WebSocketOptions {
    public String url = null;
    public Integer connectionTimeout = null;

    public final static String typeName = "WebSocketOptions";

    public static VariableType getType() {
        return BuilderContext.variableType(typeName);
    }

    public static void registryMember() {
        WebSocketOptions.getType().addMember(BuilderContext.variableType(xString.typeName), "apiKey");
        WebSocketOptions.getType().addMember(BuilderContext.variableType(xString.typeName), "secretKey");
        WebSocketOptions.getType().addMember(BuilderContext.variableType(xString.typeName), "url");
    }
}
