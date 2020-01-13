package cn.ma.cei.generator.environment;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.utils.MapWithValue2;
import cn.ma.cei.utils.NormalMap;
import cn.ma.cei.utils.SecondLevelMap;

import java.lang.reflect.Constructor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableFactory {

    public final static String NO_REF = "NO_REF";
    /**
     * *
     * Variable type name - VariableType
     */
    private static final EnvironmentData<NormalMap<String, VariableType>> variableTypeMap = new EnvironmentData(NormalMap::new);

    static class ModelInfo {

        /**
         * ModelName - is build-in model
         */
        private final NormalMap<String, Boolean> modelNameMap = new NormalMap<>();
        /**
         * ModelType - MemberName - MemberType
         */
        private final SecondLevelMap<VariableType, String, VariableType> membersInModelInfo = new SecondLevelMap<>();

        /**
         * ModelType - TypeDescriptior - Reference
         */
        private final MapWithValue2<String, String, String> modelInfo = new MapWithValue2<>();

        public boolean modelExist(String modelName) {
            return modelInfo.containsKey(modelName);
        }

        public boolean isCustomModel(String modelName) {
            if (!modelNameMap.containsKey(modelName)) {
                throw new CEIException("Model not exist");
            }
            return modelNameMap.get(modelName);
        }

        public void registerModel(String modelName, String modelNameDescriptor, String reference, boolean isBuildIn) {
            try {
                modelInfo.tryPut(modelName, modelNameDescriptor, reference);
                modelNameMap.tryPut(modelName, isBuildIn);
            } catch (Exception e) {
                throw new CEIException("Registering dupicated model");
            }
        }

        public void registerMember(VariableType modelType, String memberName, VariableType memberType) {
            if (!modelExist(modelType.getName())) {
                throw new CEIException("Model is not registered");
            }
            try {
                membersInModelInfo.tryPut(modelType, memberName, memberType);
            } catch (Exception e) {
                throw new CEIException("Registering dupicated member");
            }
        }

        public VariableType queryMemberType(VariableType modelType, String memberName) {
            if (!membersInModelInfo.containsKey(modelType, memberName)) {
                throw new CEIException("Cannot find member");
            }
            return membersInModelInfo.get(modelType, memberName);
        }

        public String getModelReference(String modelName) {
            return modelInfo.get2(modelName);
        }

        public String getModelTypeDescriptor(String modelName) {
            return modelInfo.get1(modelName);
        }
    }

    private static final EnvironmentData<ModelInfo> modelInfo = new EnvironmentData<>(ModelInfo::new);

//    /**
//     * *
//     * ModelType - MemberName - MemberType
//     */
//    private static final EnvironmentData<SecondLevelMap<VariableType, String, VariableType>> membersInModelInfo = new EnvironmentData<>(SecondLevelMap::new);
//
//    /**
//     * *
//     * ModelType - TypeDescriptior - Reference
//     */
//    private static final EnvironmentData<MapWithValue2<VariableType, String, String>> modelInfo = new EnvironmentData<>(MapWithValue2::new);
    public static String genericTypeName(String name, String... names) {
        // TODO
        String finalName = name;
        for (String one : names) {
            finalName += "#" + one;
        }
        return finalName;
    }

    public static boolean isCustomModel(VariableType type) {
        return modelInfo.get().isCustomModel(type.getName());
    }

    /**
     * *
     * Get or create a VaribaleType. This is the only interface to get or create
     * the VariableType.
     *
     * @param modelName The name of type you want to get or create.
     * @param argsTypes The generic type, e.g. List<String>, the name should be
     * "list", the argsType, should be String.
     * @return The VariableType.
     */
    public static VariableType variableType(String modelName, VariableType... argsTypes) {
        String finalName = modelName;
        for (VariableType argsType : argsTypes) {
            if (argsType != null) {
                finalName += "#" + argsType.getName();
            }
        }
        if (variableTypeMap.get().containsKey(finalName)) {
            return variableTypeMap.get().get(finalName);
        }
        // The type is not exist. Check if it can be created.
        if (!modelInfo.get().modelExist(modelName)) {
            throw new CEIException("Cannot create the model, it has not been registered.");
        }

        try {
            Constructor<?> cons = VariableType.class.getDeclaredConstructor(String.class, VariableType[].class);
            cons.setAccessible(true);
            VariableType type = (VariableType) cons.newInstance(modelName, argsTypes);
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

    public static Variable createHardcodeStringVariable(String name) {
        return createVariable(xString.inst.getType(), name, Variable.Position.STRING, null);
    }

    public static Variable createConstantVariable(String name) {
        return createVariable(xString.inst.getType(), name, Variable.Position.CONSTANT, null);
    }

//    public static Variable createThisVariable(VariableType type, String name) {
//        return createVariable(type, name, Variable.Position.THIS, null);
//    }
    /**
     * *
     * Register the member in a model, the member must be registered before
     * query it. For the model, one member can be registered only one time.
     *
     * @param modelType
     * @param memberType
     * @param memberName The variable name.
     * @return The new created member variable.
     */
    public static Variable registerMemberVariable(VariableType modelType, VariableType memberType, String memberName) {
        modelInfo.get().registerMember(modelType, memberName, memberType);
        return createVariable(memberType, memberName, Variable.Position.MEMBER, null);
    }

    public static Variable queryMemberVariable(Variable parentVariable, String name) {
        try {
            if (!modelInfo.get().modelExist(parentVariable.getTypeName())) {
                throw new CEIException("[VariableFactory] The model is not defined: " + parentVariable.getTypeName());
            }
            VariableType memberType = modelInfo.get().queryMemberType(parentVariable.getType(), name);
            return createVariable(memberType, name, Variable.Position.REFER, parentVariable);
        } catch (CEIException e) {
            throw new CEIException("[VariableFactory] The memeber is not defined: " + name + " in model: " + parentVariable.getTypeName());
        }
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

    public static void setupBuildinVariableType(String typeName, String typeDescriptor, String referenceName) {
        modelInfo.get().registerModel(typeName, typeDescriptor, referenceName, true);
    }

    public static void registerModel(String modelName, String reference) {
        String modelNameDescriptor = Environment.getCurrentDescriptionConverter().getModelDescriptor(modelName);
        modelInfo.get().registerModel(modelName, modelNameDescriptor, reference, false);
    }

    public static String getModelReference(VariableType modeltype) {
        return modelInfo.get().getModelReference(modeltype.getName());
    }

    public static String getModelTypeDescriptor(VariableType modeltype) {
        return modelInfo.get().getModelTypeDescriptor(modeltype.getName());
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
