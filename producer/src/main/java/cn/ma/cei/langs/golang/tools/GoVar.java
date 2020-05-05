/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.langs.golang.tools;

import cn.ma.cei.generator.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 *
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
    
    public String getNameDescriptor() {
        return this.variable.getDescriptor();
    }
    
    public String getTypeDescriptor() {
        return this.variable.getTypeDescriptor();
    }

    public static GoVar[] toArray(Variable... items) {
        List<GoVar> list = new ArrayList<>();
        for (Variable item : items) {
            list.add(new GoVar(item));
        }
        return list.toArray(new GoVar[0]);
    }
}
