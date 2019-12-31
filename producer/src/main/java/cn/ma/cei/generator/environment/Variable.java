package cn.ma.cei.generator.environment;

import cn.ma.cei.exception.CEIException;

public class Variable {

    public enum Position {
        INPUT,
        LOCAL,
        MEMBER,
        REFER,
        CONSTANT,
        HARDCODE_STRING,
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
        if ((name == null || name.equals("")) && position != Position.HARDCODE_STRING) {
            throw new CEIException("[Variable] name is null");
        }
        this.name = name;
        if (position == null) {
            throw new CEIException("[Variable] position is null");
        }
        switch (position) {
            case MEMBER:
                this.nameDescriptor = Environment.getCurrentDescriptionConverter().getMemberVariableDescriptor(name);
                break;
            case REFER:
                // TODO
                this.nameDescriptor = parentVariable.nameDescriptor + "." + Environment.getCurrentDescriptionConverter().getVariableDescriptor(name);
                break;
            case INPUT:
            case LOCAL:
                this.nameDescriptor = Environment.getCurrentDescriptionConverter().getVariableDescriptor(name);
                break;
            case CONSTANT:
                this.nameDescriptor = name;
                break;
            case HARDCODE_STRING:
                this.nameDescriptor = Environment.getCurrentDescriptionConverter().toStringDescriptor(name);
                break;
            default:
                throw new CEIException("[Variable] position is not suppotrd");
        }

        this.parent = parentVariable;
        this.type = type;
        this.position = position;
    }

    public Variable queryMember(String name) {
        return VariableFactory.queryMemberVariable(this, name);
    }
}
