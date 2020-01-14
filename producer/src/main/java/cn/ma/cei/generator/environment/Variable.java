package cn.ma.cei.generator.environment;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.utils.SecondLevelMap;

public class Variable {

    private static EnvironmentData<SecondLevelMap<String, Position, String>> variableDescriptor = new EnvironmentData<>();

    public enum Position {
        INPUT,
        LOCAL,
        MEMBER,
        REFER,
        THIS,
        CONSTANT,
        STRING,
    }

    public Variable parent;

    public String defaultValue;
    public Position position;

    private VariableType type;
    private String name;
    private String nameDescriptor;

    public String getDescriptor() {
        return nameDescriptor;
    }

    public String getName() {
        return name;
    }

    public VariableType getType() {
        return type;
    }

    public String getTypeDescriptor() {
        return type.getDescriptor();
    }

    public String getTypeName() {
        return type.getName();
    }

    private Variable(VariableType type, String name, Position position, Variable parentVariable) {

        if (type == null || !type.isValid()) {
            throw new CEIException("[Variable] type is null");
        }
        if ((name == null || name.equals("")) && position != Position.STRING) {
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
                this.nameDescriptor = parentVariable.nameDescriptor + "." + Environment.getCurrentDescriptionConverter().getMemberVariableDescriptor(name);
                break;
            //case THIS:
            //TODO
            //this.nameDescriptor = "self";
            //break;
            case INPUT:
            case LOCAL:
                this.nameDescriptor = Environment.getCurrentDescriptionConverter().getVariableDescriptor(name);
                break;
            case CONSTANT:
                this.nameDescriptor = name;
                break;
            case STRING:
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
