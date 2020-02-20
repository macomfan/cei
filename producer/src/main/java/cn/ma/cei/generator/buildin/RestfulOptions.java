/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.buildin;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.model.types.xString;

/**
 *
 * @author u0151316
 */
public class RestfulOptions {

    public String url = null;
    public Integer connectionTimeout = null;

    public final static String typeName = "RestfulOptions";

    public static VariableType getType() {
        return BuilderContext.variableType(typeName);
    }

    public static void registryMember() {
        RestfulOptions.getType().addMember(BuilderContext.variableType(xString.typeName), "apiKey");
        RestfulOptions.getType().addMember(BuilderContext.variableType(xString.typeName), "secretKey");
        RestfulOptions.getType().addMember(BuilderContext.variableType(xString.typeName), "url");
    }
}
