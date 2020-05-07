/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.golang.vars;

import cn.ma.cei.generator.VariableType;

/**
 *
 * @author U0151316
 */
public class GoType {

    public VariableType type;
    public boolean isPtr = false;
    
    public GoType(VariableType type) {
        this.type = type;
    }
    
    public String getDescriptor() {
        return this.type.getDescriptor();
    }
}
