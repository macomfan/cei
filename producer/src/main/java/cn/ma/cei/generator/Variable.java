package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.database.Naming;

public class Variable {

    public enum Position {
        INPUT,
        LOCAL,
        MEMBER
    }

    public Variable parent;
    public String name;
    public String nameDescriptor;
    public VariableType type;
    public String defaultValue;
    public Position position;

    private Variable(VariableType type, String name, Position position, Variable parentVariable) {
        if (type == null || !type.isValid()) {
            throw new CEIException("[Variable] type is null");
        }
        if (name == null || name.equals("")) {
            throw new CEIException("[Variable] name is null");
        }
        this.name = name;
        if (position == Position.MEMBER) {
            this.nameDescriptor = Naming.get().getMemberVariableDescriptor(name);
        } else {
            this.nameDescriptor = Naming.get().getVariableDescriptor(name);
        }

        this.parent = parentVariable;
        this.type = type;
        this.position = position;
    }
}
