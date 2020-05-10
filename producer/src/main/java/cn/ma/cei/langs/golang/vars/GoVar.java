/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.golang.vars;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.langs.golang.tools.GoVarMgr;

/**
 * @author U0151316
 */
public class GoVar {
    public Variable variable;
    public boolean isPtr = false;

    public GoVar(Variable variable) {
        this.variable = variable;
    }

    public String getName() {
        return variable.getName();
    }

    public String getDescriptor() {
        return variable.getDescriptor();
    }

    public String getTypeDescriptor() {
        return variable.getTypeDescriptor();
    }
}
