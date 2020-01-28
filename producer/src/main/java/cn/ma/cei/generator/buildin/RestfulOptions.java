/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.buildin;

import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.environment.VariableType;
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
        return VariableFactory.variableType(typeName);
    }

    public static void registryMember() {
        VariableFactory.registerMemberVariable(RestfulOptions.getType(), VariableFactory.variableType(xString.typeName), "apiKey");
        VariableFactory.registerMemberVariable(RestfulOptions.getType(), VariableFactory.variableType(xString.typeName), "secretKey");
        VariableFactory.registerMemberVariable(RestfulOptions.getType(), VariableFactory.variableType(xString.typeName), "url");
    }
}
