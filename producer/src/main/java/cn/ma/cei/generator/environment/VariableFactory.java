package cn.ma.cei.generator.environment;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.utils.NormalMap;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableFactory {

    static class VariableTypeMap {

        private EnvironmentData<NormalMap<String, VariableType>> map = new EnvironmentData<>();

        public NormalMap<String, VariableType> get() {
            if (map.isNull()) {
                map.trySet(new NormalMap<>());
            }
            return map.get();
        }
    }

    private static final VariableTypeMap variableTypeMap = new VariableTypeMap();

    public static String genericTypeName(String name, String... names) {
        // TODO
        String finalName = name;
        for (String one : names) {
            finalName += "#" + one;
        }
        return finalName;
    }

    public static VariableType variableType(String name, VariableType... argsTypes) {
        String finalName = name;
        for (VariableType argsType : argsTypes) {
            finalName += "#" + argsType.getName();
        }
        if (variableTypeMap.get().containsKey(finalName)) {
            return variableTypeMap.get().get(finalName);
        }
        try {
            Constructor<?> cons = VariableType.class.getDeclaredConstructor(String.class);
            cons.setAccessible(true);
            VariableType type = (VariableType) cons.newInstance(name);
            Method method = VariableType.class.getDeclaredMethod("addGeneric", VariableType.class);
            method.setAccessible(true);
            for (VariableType argsType : argsTypes) {
                method.invoke(type, argsType);
            }
            variableTypeMap.get().tryPut(finalName, type);
            return type;
        } catch (Exception e) {
            throw new CEIException("[VariableFactory] cannot create VariableType");
        }
    }

    public static Variable createLocalVariable(VariableType type, String name) {
        return createVariable(type, name, Variable.Position.LOCAL, null);
    }

    public static Variable createInputVariable(VariableType type, String name) {
        return createVariable(type, name, Variable.Position.INPUT, null);
    }

    public static Variable createMemberVariable(Variable parentVariable, VariableType type, String name) {
        return createVariable(type, name, Variable.Position.MEMBER, parentVariable);
    }

    private static Variable createVariable(VariableType type, String name, Variable.Position position, Variable parentVariable) {
        try {
            Constructor<?> cons = Variable.class.getDeclaredConstructor(VariableType.class, String.class, Variable.Position.class, Variable.class);
            cons.setAccessible(true);
            return (Variable) cons.newInstance(type, name, position, parentVariable);
        } catch (Exception e) {
            throw new CEIException("[VariableFactory] cannot create Variable");
        }
    }

    public static String isReference(String value) {
        String pattern = "\\{.*\\}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(value);
        if (m.find()) {
            return value.substring(m.start() + 1, m.end() - 1);
        }
        return null;
    }
}
