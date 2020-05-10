package cn.ma.cei.langs.golang.vars;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.langs.golang.tools.GoVarMgr;
import cn.ma.cei.utils.WordSplitter;

public class GoMergedInputVar extends GoVar{
    public GoMergedInputVar(Variable variable) {
        super(variable);
    }

    @Override
    public String getDescriptor() {
        if (variable.position == Variable.Position.INPUT) {
            return WordSplitter.getUpperCamelCase(this.variable.getDescriptor());
        } else {
            CEIErrors.showCodeFailure(this.getClass(), "Cannot be GoInputVar %s", variable.getName());
            return null;
        }
    }
}
