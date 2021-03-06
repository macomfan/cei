package cn.ma.cei.generator;

public class BuilderContext {

    public static final String SELF = "###SELF###";
    public final static String NO_REF = "NO_REF";

    /**
     * Must be called before build a exchange
     *
     * @param folder Where the file to be generated.
     */
    public static void setExchangeFolder(CEIPath folder) {
        GlobalContext.setExchangeFolder(folder);
    }

    public static Language getCurrentLanguage() {
        return GlobalContext.getCurrentLanguage();
    }

    public static CEIPath getExchangeFolder() {
        return GlobalContext.getExchangeFolder();
    }

    public static CEIPath getWorkingFolder() {
        return GlobalContext.getWorkingFolder();
    }

    public static void setupBuildInVariableType(String typeName, String typeDescriptor, String reference) {
        GlobalContext.setupBuildInVariableType(typeName, typeDescriptor, reference);
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
