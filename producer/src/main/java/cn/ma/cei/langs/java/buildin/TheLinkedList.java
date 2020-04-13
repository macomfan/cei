/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.java.buildin;

import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.VariableType;

/**
 *
 * @author u0151316
 */
public class TheLinkedList {

    public final static String typeName = "linkedlist";

    public static VariableType getType() {
        VariableType ttt =  BuilderContext.variableType(typeName, (VariableType)null);
        String t = ttt.getDescriptor();
        return ttt;
    }
}
