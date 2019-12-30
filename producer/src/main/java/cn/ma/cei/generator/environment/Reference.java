/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator.environment;

import cn.ma.cei.utils.MapWithValue2;

/**
 *
 * @author u0151316
 */
public class Reference {

    private static final EnvironmentData<MapWithValue2<VariableType, String, String>> info = new EnvironmentData<>();

    private static MapWithValue2<VariableType, String, String> info() {
        if (info.isNull()) {
            info.trySet(new MapWithValue2<>());
        }
        return info.get();
    }

    public static String getTypeDescriptor(VariableType type) {
        if (!info().containsKey(type)) {
            info().tryPut(type, Environment.getCurrentDescriptionConverter().getModelDescriptor(type.getName()), "TBD");
        }
        return info().get1(type);
    }

    public static String getReference(VariableType type) {
        return info().get2(type);
    }

    public static void setupBuildinVariableType(String typeName, String typeDescriptor, String referenceName) {
        info().tryPut(VariableFactory.variableType(typeName), typeDescriptor, referenceName);
    }

    public static void addReference(VariableType type, String referenceName) {
        String typeDescriptor = info().get1(type);
        info().put(type, typeDescriptor, referenceName);
    }
}
