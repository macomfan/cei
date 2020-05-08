package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIErrorType;
import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.exception.CEIException;

public class Variable {

    private final VariableType type;
    private final String name;
    private final String nameDescriptor;
    public Variable parent;
    public Position position;

    private Variable(Variable parentVariable, Variable currentVariable) {
        if (parentVariable == null || currentVariable == null) {
            CEIErrors.showCodeFailure(Variable.class,"Cannot create reference variable");
        }
        this.parent = parentVariable;
        this.type = currentVariable.type;
        this.position = Position.REFER;
        this.name = parentVariable.name + "#" + currentVariable.name;
        this.nameDescriptor = GlobalContext.getCurrentDescriptionConverter().getReferenceByChain(
                parentVariable.nameDescriptor, currentVariable.nameDescriptor);
    }

    private Variable(VariableType type, String name, Position position) {

        if (type == null || !type.isValid()) {
            throw new CEIException("[Variable] type is null");
        }
        if ((name == null || name.equals("")) && position != Position.STRING) {
            throw new CEIException("[Variable] name is null");
        }
        if (position == null) {
            throw new CEIException("[Variable] position is null");
        }
        switch (position) {
            case MEMBER:
                this.nameDescriptor = GlobalContext.getCurrentDescriptionConverter().getMemberVariableDescriptor(name);
                this.name = name;
                break;
            case PRIVATE:
                this.nameDescriptor = GlobalContext.getCurrentDescriptionConverter().getPrivateMemberDescriptor(name);
                this.name = name;
                break;
            case REFER:
                this.name = null;
                this.nameDescriptor = null;
                CEIErrors.showCodeFailure(Variable.class, "Cannot create Reference Variable here");
                break;
            case USER:
            case INPUT:
            case LOCAL:
                if (name.equals(BuilderContext.SELF)) {
                    this.nameDescriptor = GlobalContext.getCurrentDescriptionConverter().getSelfDescriptor();
                } else {
                    this.nameDescriptor = GlobalContext.getCurrentDescriptionConverter().getVariableDescriptor(name);
                }
                this.name = name;
                break;
            case STATEMENT:
                this.nameDescriptor = name;
                this.name = "##CONST##_" + name;
                break;
            case STRING:
                this.nameDescriptor = GlobalContext.getCurrentDescriptionConverter().toStringDescriptor(name);
                this.name = "##STR##_" + name;
                break;
            default:
                throw new CEIException("[Variable] position is not supported");
        }

        this.parent = null;
        this.type = type;
        this.position = position;
    }

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

    public Variable tryGetMember(String name) {
        Variable member = getMember(name);
        if (member == null) {
            CEIErrors.showFailure(CEIErrorType.XML, "Cannot find member: \"%s\" in model: \"%s\"", name, this.getType().getName());
        }
        return member;
    }

    public Variable getMember(String name) {
        Variable member = type.getMember(name);
        if (member == null) {
            return null;
        }
        return VariableCreator.createReferenceVariable(this, member);
    }

    public boolean isSelf() {
        return name.equals(BuilderContext.SELF);
    }

    public enum Position {
        INPUT,      // The input variable of a method
        LOCAL,      // The local variable including both self variable and CEI defined variable
        USER,       // The user defined in XML
        MEMBER,     // The public member of a model
        PRIVATE,    // The private member of a model
        REFER,
        STATEMENT,  // The hard code statement for each language.
        STRING,     // The string value, e.g. for java it will be "xxx".
    }
}
