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
    public GoVarMgr method;

    public GoVar(Variable variable, GoVarMgr method) {
        this.variable = variable;
        this.method = method;
    }

    public String getName() {
        return variable.getName();
    }

    public String getDescriptor() {
        return this.variable.getDescriptor();
    }

    public String getTypeDescriptor() {
        return this.variable.getTypeDescriptor();
    }
}
