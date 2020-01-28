package cn.ma.cei.generator.builder;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.environment.Variable;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.model.types.xString;

import java.lang.reflect.Constructor;

class VariableCreator {

    public static Variable createLocalVariable(VariableType type, String name) {
        return createVariable(type, name, Variable.Position.LOCAL);
    }

    public static Variable createInputVariable(VariableType type, String name) {
        return createVariable(type, name, Variable.Position.INPUT);
    }

    public static Variable createMemberVariable(VariableType modelType, VariableType memberType, String memberName) {
        VariableFactory.registerMemberVariable(modelType, memberType, memberName);
        return createVariable(memberType, memberName, Variable.Position.MEMBER);
    }

    private static Variable createVariable(VariableType type, String name, Variable.Position position) {
        try {
            Constructor<?> cons = Variable.class.getDeclaredConstructor(VariableType.class, String.class, Variable.Position.class, Variable.class);
            cons.setAccessible(true);
            return (Variable) cons.newInstance(type, name, position, null);
        } catch (Exception e) {
            throw new CEIException("[VariableFactory] cannot create Variable");
        }
    }
}
