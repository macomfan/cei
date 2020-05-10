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
public class GoPtrVar extends GoVar {

    public GoPtrVar(Variable variable) {
        super(variable);
        super.isPtr = true;
    }

    @Override
    public String getTypeDescriptor() {
        return "*" + super.variable.getTypeDescriptor();
    }
}
