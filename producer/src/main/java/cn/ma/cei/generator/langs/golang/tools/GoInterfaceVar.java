/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.golang.tools;

import cn.ma.cei.generator.environment.Variable;

/**
 *
 * @author U0151316
 */
public class GoInterfaceVar extends GoVar {

    public GoInterfaceVar(Variable variable) {
        super(variable);
    }
    
    @Override
    public String getName() {
        return variable.getName();
    }
    
    @Override
    public String getNameDescriptor() {
        return this.variable.getDescriptor();
    }
    
    @Override
    public String getTypeDescriptor() {
        return "interface{}";
    }
}
