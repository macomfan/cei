/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.golang.vars;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.langs.golang.tools.GoVarMgr;

/**
 *
 * @author U0151316
 */
public class GoInterfaceVar extends GoVar {

    public GoInterfaceVar(Variable variable, GoVarMgr method) {
        super(variable, method);
    }
    
    @Override
    public String getName() {
        return variable.getName();
    }
    
    @Override
    public String getDescriptor() {
        return this.variable.getDescriptor();
    }
    
    @Override
    public String getTypeDescriptor() {
        return "interface{}";
    }
}
