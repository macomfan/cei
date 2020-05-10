package cn.ma.cei.langs.golang.vars;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.langs.golang.tools.GoVarMgr;

public class GoGetValueVar extends GoVar {

    public GoGetValueVar(Variable variable) {
        super(variable);
        super.isPtr = true;
    }

    @Override
    public String getDescriptor() {
        return "*" + this.variable.getDescriptor();
    }
}