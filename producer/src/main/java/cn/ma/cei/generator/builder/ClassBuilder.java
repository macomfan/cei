package cn.ma.cei.generator.builder;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.utils.UniquetList;

public class ClassBuilder {
    private final UniquetList<String, Variable> memberList = new UniquetList<>();
    private VariableType selfType = null;

    public void setClassType(VariableType type) {
        selfType = type;
    }

    public Variable createMemberVariable(VariableType type, String name) {
        if (selfType == null) {
            throw new CEIException("Have not set the class type");
        }
        Variable variable;
        if (memberList.containsKey(name)) {
            throw new CEIException("Member duplicate: " + name);
        } else {
            variable = VariableCreator.createMemberVariable(selfType, type, name);
        }
        return variable;
    }
}
