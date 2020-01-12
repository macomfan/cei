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
// TODO can be merged to VariableFactory?
public class Reference {
    public final static String NO_REF = "NO_REF";

    private static final EnvironmentData<MapWithValue2<VariableType, String, String>> info = new EnvironmentData<>(MapWithValue2::new);

    public static String getTypeDescriptor(VariableType type) {
        if (!info.get().containsKey(type)) {
            info.get().tryPut(type, Environment.getCurrentDescriptionConverter().getModelDescriptor(type.getName()), "TBD");
        }
        return info.get().get1(type);
    }

    public static String getReference(VariableType type) {
        return info.get().get2(type);
    }

    public static void setupBuildinVariableType(VariableType type, String typeDescriptor, String referenceName) {
        info.get().tryPut(type, typeDescriptor, referenceName);
    }

    public static void addReference(VariableType type, String referenceName) {
        String typeDescriptor = info.get().get1(type);
        info.get().put(type, typeDescriptor, referenceName);
    }
}
