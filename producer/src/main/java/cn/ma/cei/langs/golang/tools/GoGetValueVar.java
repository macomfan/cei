package cn.ma.cei.langs.golang.tools;

import cn.ma.cei.generator.Variable;

public class GoGetValueVar extends GoVar {

    public GoGetValueVar(Variable variable) {
        super(variable);
        super.isPtr = true;
    }

    @Override
    public String getNameDescriptor() {
        return "*" + this.variable.getDescriptor();
    }
}