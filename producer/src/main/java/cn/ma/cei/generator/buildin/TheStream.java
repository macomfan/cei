/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.buildin;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.VariableType;

/**
 *
 * @author u0151316
 */
public class TheStream {

    public final static String typeName = "Stream";

    public static VariableType getType() {
        return BuilderContext.variableType(typeName);
    }
}
