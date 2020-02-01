package cn.ma.cei.generator.langs.golang.tools;

import cn.ma.cei.generator.environment.Variable;

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