package cn.ma.cei.generator;

public class BuilderContext {

    public final static String NO_REF = "NO_REF";

    public static void setExchangeFolder(CEIPath folder) {
        GlobalContext.setExchangeFolder(folder);
    }

    public static CEIPath getExchangeFolder() {
        return GlobalContext.getExchangeFolder();
    }

    public static CEIPath getWorkingFolder() {
        return GlobalContext.getWorkingFolder();
    }

    public static void setupBuildInVariableType(String typeName, String typeDescriptor, String reference) {
        GlobalContext.setupBuildInVariableType(typeName, typeDescriptor, typeDescriptor);
    }

    public static String genericTypeName(String typeName, String... typeNames) {
        return GlobalContext.genericTypeName(typeName, typeNames);
    }

    public static Variable createStringConstant(String value) {
        return GlobalContext.createStringConstant(value);
    }

    public static Variable createStatement(String value) {
        return GlobalContext.createStatement(value);
    }

    public static VariableType variableType(String typeName, VariableType... argsTypes) {
        return GlobalContext.variableType(typeName, argsTypes);
    }
}
