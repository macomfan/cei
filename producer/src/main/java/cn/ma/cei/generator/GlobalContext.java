/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIErrorType;
import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.builder.IFramework;
import cn.ma.cei.generator.naming.IDescriptionConverter;
import cn.ma.cei.model.types.xString;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.TwoTuple;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author u0151316
 */
class GlobalContext {

    private static CEIPath workingFolder = null;
    private static CEIPath exchangeFolder = null;
    private static String currentExchange = "";
    private static Language currentLanguage = Language.NA;
    private static IFramework currentFramework = null;
    private static VariableType currentModel = null;
    private static sMethod currentMethod = null;

    private static final EnvironmentData<String, VariableType> variableTypes = new EnvironmentData<>();
    private static final EnvironmentData<String, TwoTuple<String, List<String>>> variableTypeInfo = new EnvironmentData<>();

    public static VariableType getCurrentModel() {
        return currentModel;
    }

    public static sMethod getCurrentMethod() {
        return currentMethod;
    }

    public static void setCurrentModel(VariableType model) {
        GlobalContext.currentModel = model;
    }

    public static void setCurrentMethod(sMethod method) {
        GlobalContext.currentMethod = method;
    }

    public static void setWorkingFolder(CEIPath folder) {
        if (!folder.exists()) {
            throw new CEIException("Folder invalid");
        }
        workingFolder = folder;
    }

    public static CEIPath getWorkingFolder() {
        return workingFolder;
    }

    public static void setExchangeFolder(CEIPath folder) {
        if (!folder.exists()) {
            throw new CEIException("Folder invalid");
        }
        exchangeFolder = folder;
    }

    public static CEIPath getExchangeFolder() {
        return exchangeFolder;
    }

    public static String getCurrentExchange() {
        return GlobalContext.currentExchange;
    }

    public static void setCurrentExchange(String exchange) {
        GlobalContext.currentExchange = exchange;
    }

    public static Language getCurrentLanguage() {
        return GlobalContext.currentLanguage;
    }

    public static void setCurrentLanguage(Language language) {
        GlobalContext.currentLanguage = language;
    }

    public static void setCurrentFramework(IFramework currentFramework) {
        GlobalContext.currentFramework = currentFramework;
    }

    public static IDescriptionConverter getCurrentDescriptionConverter() {
        return Checker.checkBuilder(currentFramework.getDescriptionConverter(), currentFramework.getClass(), "DescriptionConverter");
    }

    public static void setupBuildInVariableType(String typeName, String typeDescriptor, String reference) {
        if (variableTypeInfo.containsKey(typeName)) {
            throw new CEIException("Cannot setup duplicate type");
        }
        List<String> references = new LinkedList<>();
        references.add(reference);
        TwoTuple<String, List<String>> value = new TwoTuple<>(typeDescriptor, references);
        variableTypeInfo.tryPut(typeName, value);
    }

    public static void setupRunTimeVariableType(String typeName, String reference) {
        setupBuildInVariableType(typeName, getCurrentDescriptionConverter().getModelDescriptor(typeName), reference);
    }

    public static Variable createStringConstant(String value) {
        return VariableCreator.createVariable(xString.inst.getType(), value, Variable.Position.STRING, null);
    }

    public static Variable createStatement(String statement) {
        return VariableCreator.createVariable(xString.inst.getType(), statement, Variable.Position.CONSTANT, null);
    }

    public static VariableType variableType(String typeName, VariableType... argsTypes) {
        String finalName = typeName;
        boolean isGenericType = false;
        if (argsTypes != null && argsTypes.length != 0) {
            ArrayList<String> tmp = new ArrayList<>();
            isGenericType = true;
            for (VariableType oneType : argsTypes) {
                if (oneType != null) {
                    tmp.add(oneType.getName());
                } else {
                    tmp.add("");
                }
            }
            finalName = genericTypeName(typeName, tmp.toArray(new String[0]));
        }
        if (variableTypes.containsKey(finalName)) {
            return variableTypes.get(finalName);
        }
        if (!variableTypeInfo.containsKey(typeName)) {
            CEIErrors.showFailure(CEIErrorType.XML, "Cannot find model: %s, it has not been defined or setup.", typeName);
        }
        if (argsTypes != null && argsTypes.length != 0 && isGenericType) {
            List<String> references = new LinkedList<>(variableTypeInfo.get(typeName).get2());
            String typeDescriptor = variableTypeInfo.get(typeName).get1();
            List<String> subTypeNames = new LinkedList<>();
            for (VariableType oneType : argsTypes) {
                if (oneType != null) {
                    references.addAll(oneType.getReferences());
                    subTypeNames.add(oneType.getDescriptor());
                }
            }
            typeDescriptor = GlobalContext.getCurrentDescriptionConverter().getGenericTypeDescriptor(typeDescriptor, subTypeNames);
            TwoTuple<String, List<String>> value = new TwoTuple<>(typeDescriptor, references);
            variableTypeInfo.tryPut(finalName, value);
        }
        try {
            Constructor<?> cons = VariableType.class.getDeclaredConstructor(String.class, VariableType[].class);
            cons.setAccessible(true);
            VariableType type = (VariableType) cons.newInstance(finalName, argsTypes);
            variableTypes.tryPut(finalName, type);
            return type;
        } catch (Exception e) {
            throw new CEIException("[Context] cannot create VariableType: " + finalName);
        }
    }

    public static TwoTuple<String, List<String>> getModelInfo(String modelName) {
        return variableTypeInfo.get(modelName);
    }

    public static String genericTypeName(String typeName, String... typeNames) {
        if (typeName.contains("#")) {
            throw new CEIException("");
        }
        if (typeNames == null || typeNames.length == 0) {
            return typeName;
        }
        StringBuilder finalName = new StringBuilder(typeName);
        for (String one : typeNames) {
            finalName.append("#").append(one);
        }
        return finalName.toString();
    }
}
