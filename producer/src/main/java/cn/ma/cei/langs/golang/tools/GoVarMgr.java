package cn.ma.cei.langs.golang.tools;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.buildin.*;
import cn.ma.cei.langs.golang.vars.GoPtrVar;
import cn.ma.cei.langs.golang.vars.GoVar;

import java.util.ArrayList;
import java.util.List;

/**
 * The base class of GoMethod
 * Because golang have the pointer and non-pointer type. We use GoVarMgr to convert Variable to GoVariable.
 */
public class GoVarMgr {

    public GoVar var(Variable variable) {
        if (variable.getType() == RestfulOptions.getType()
                || variable.getType() == RestfulRequest.getType()
                || variable.getType() == RestfulRequest.getType()
                || variable.getType() == WebSocketCallback.getType()
                || variable.getType() == WebSocketConnection.getType()
                || variable.getType() == WebSocketMessage.getType()
                || variable.getType() == WebSocketOptions.getType()) {
            return new GoPtrVar(variable);
        } else {
            return new GoVar(variable);
        }
    }

    public GoVar varNormal(Variable variable) {
        return new GoVar(variable);
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

//    public List<GoVar> varListToPtrList(List<Variable> items) {
//        List<GoVar> list = new ArrayList<>();
//        for (Variable item : items) {
//            list.add(new GoPtrVar(item));
//        }
//        return list;
//    }
}
