package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.exception.CEIException;

import java.lang.reflect.Constructor;

class VariableCreator {
    public static Variable createVariable(VariableType type, String name, Variable.Position position) {
        try {
            Constructor<?> cons = Variable.class.getDeclaredConstructor(VariableType.class, String.class, Variable.Position.class);
            cons.setAccessible(true);
            return (Variable) cons.newInstance(type, name, position);
        } catch (Exception e) {
            CEIErrors.showCodeFailure(VariableCreator.class,"Cannot create Variable: %s", e.getMessage());
            return null;
        }
    }

    public static Variable createReferenceVariable(Variable parentVariable, Variable currentVariable) {
        try {
            Constructor<?> cons = Variable.class.getDeclaredConstructor(Variable.class, Variable.class);
            cons.setAccessible(true);
            return (Variable) cons.newInstance(parentVariable, currentVariable);
        } catch (Exception e) {
            CEIErrors.showCodeFailure(VariableCreator.class,"Cannot create Reference Variable: %s", e.getMessage());
            return null;
        }
    }
}
