package cn.ma.cei.langs.golang.vars;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.langs.golang.tools.GoVarMgr;
import cn.ma.cei.utils.WordSplitter;

public class GoQueryInputVar extends GoVar{
    public GoQueryInputVar(Variable variable) {
        super(variable);
    }

    @Override
    public String getDescriptor() {
        return "args." + WordSplitter.getUpperCamelCase(variable.getDescriptor());
    }
}
