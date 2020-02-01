/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.buildin;

import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.environment.VariableType;

/**
 *
 * @author u0151316
 */
public class SignatureTool {

    static public class Constant {

        public final static String ASC = "asc";
        public final static String DSC = "dsc";
        public final static String HOST = "host";
        public final static String METHOD = "method";
        public final static String TARGET = "target";
        public final static String UPPERCASE = "uppercase";
        public final static String LOWERCASE = "lowercase";
        public final static String NONE = "none";
    }

    public final static String typeName = "SignatureTool";

    public static VariableType getType() {
        return VariableFactory.variableType(typeName);
    }
}
