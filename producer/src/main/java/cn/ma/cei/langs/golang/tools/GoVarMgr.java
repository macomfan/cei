package cn.ma.cei.langs.golang.tools;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.langs.golang.vars.*;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.NormalMap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The base class of GoMethod
 * Because golang have the pointer and non-pointer type. We use GoVarMgr to convert Variable to GoVariable.
 * For the merged parameters. it supports to lookup the variable from the struct.
 */
public class GoVarMgr {

    private final NormalMap<String, GoVar> varList = new NormalMap<>();
    private final NormalMap<String, GoVar> mergedVariable = new NormalMap<>();

    /**
     * This method can be called only in StartInterface or StartMethod.
     *
     * @param params
     * @return The public variable should be added into the new struct.
     */
    protected List<GoVar> mergeInputVar(List<GoVar> params) {
        if (!Checker.isNull(params)) {
            List<GoVar> vars = new LinkedList<>();
            params.forEach(item -> {
                GoMergedInputVar mergedInputVar = new GoMergedInputVar(item.variable);
                mergedVariable.put(item.getName(), mergedInputVar);
                vars.add(mergedInputVar);
            });
            return vars;
        }
        return null;
    }

    public GoVar var(Variable variable) {
        if (mergedVariable.containsKey(variable.getName())) {
            return new GoQueryInputVar(variable);
        }
        return new GoVar(variable);
    }

    public GoVar varGetPtrVal(Variable variable) {
        return new GoGetValueVar(variable);
    }

    public GoVar varPtr(Variable variable) {
        return new GoPtrVar(variable);
    }

    public GoVar[] varListToArray(Variable... items) {
        List<GoVar> list = new ArrayList<>();
        for (Variable item : items) {
            list.add(var(item));
        }
        return list.toArray(new GoVar[0]);
    }

    public List<GoVar> varListToList(List<Variable> items) {
        List<GoVar> list = new ArrayList<>();
        for (Variable item : items) {
            list.add(var(item));
        }
        return list;
    }

    public List<GoVar> varListToPtrList(List<Variable> items) {
        List<GoVar> list = new ArrayList<>();
        for (Variable item : items) {
            list.add(new GoPtrVar(item));
        }
        return list;
    }
}
