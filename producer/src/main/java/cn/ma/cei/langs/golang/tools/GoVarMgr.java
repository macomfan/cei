package cn.ma.cei.langs.golang.tools;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.generator.Variable;
import cn.ma.cei.langs.golang.vars.*;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.NormalMap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/** The GoMethod base class, implement the go variable lookup engine.
 *
 */
public class GoVarMgr {

    private final NormalMap<String, GoVar> varList = new NormalMap<>();
    private final NormalMap<String, GoVar> mergedVariable = new NormalMap<>();

    protected String queryVariableDescriptor(GoVar variable) {
        if (mergedVariable.containsKey(variable.getName())) {
            return "args." + mergedVariable.get(variable.getName()).getDescriptor();
        }
        return variable.getDescriptor();
    }

    protected List<GoVar> mergeInputVar(List<GoVar> params) {
        if (!Checker.isNull(params)) {
            List<GoVar> vars = new LinkedList<>();
            params.forEach(item -> {
                GoMergedInputVar mergedInputVar = new GoMergedInputVar(item.variable, this);
                mergedVariable.put(item.getName(), mergedInputVar);
                vars.add(mergedInputVar);
            });
            return vars;
        }
        return null;
    }

    public GoVar var(Variable variable) {
        if (mergedVariable.containsKey(variable.getName())) {
            return new GoQueryInputVar(variable, this);
        }
        return new GoVar(variable, this);
    }

    public GoVar varGetPtrVal(Variable variable) {
        return new GoGetValueVar(variable, this);
    }

    public GoVar varPtr(Variable variable) {
        return new GoPtrVar(variable, this);
    }

    public GoVar[] varListToArray(Variable... items) {
        List<GoVar> list = new ArrayList<>();
        for (Variable item : items) {
            list.add(var(item));
        }
        return list.toArray(new GoVar[0]);
    }
}
