/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.langs.golang.tools;

import cn.ma.cei.generator.environment.VariableType;

/**
 *
 * @author U0151316
 */
public class GoPtrType extends GoType {

    public GoPtrType(VariableType type) {
        super(type);
        super.isPtr = true;
    }

    @Override
    public String getDescriptor() {
        return "*" + this.type.getDescriptor();
    }
}
