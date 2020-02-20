package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;

import java.lang.reflect.Constructor;

class VariableCreator {

    public static Variable createLocalVariable(VariableType type, String name) {
        return createVariable(type, name, Variable.Position.LOCAL, null);
    }

    public static Variable createInputVariable(VariableType type, String name) {
        return createVariable(type, name, Variable.Position.INPUT, null);
    }

    public static Variable createMemberVariable(VariableType memberType, String memberName) {
        return createVariable(memberType, memberName, Variable.Position.MEMBER, null);
    }

    public static Variable createVariable(VariableType type, String name, Variable.Position position, Variable parentVariable) {
        try {
            Constructor<?> cons = Variable.class.getDeclaredConstructor(VariableType.class, String.class, Variable.Position.class, Variable.class);
            cons.setAccessible(true);
            return (Variable) cons.newInstance(type, name, position, parentVariable);
        } catch (Exception e) {
            throw new CEIException("[VariableFactory] cannot create Variable");
        }
    }
}
