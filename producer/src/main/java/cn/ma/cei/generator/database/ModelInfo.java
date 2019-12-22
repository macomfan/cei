package cn.ma.cei.generator.database;

import cn.ma.cei.generator.Variable;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.utils.SecondLevelMap;

public class ModelInfo {
    
    private static final EnvironmentData<SecondLevelMap<VariableType, String, Variable>> info = new EnvironmentData<>();

    private static SecondLevelMap<VariableType, String, Variable> info() {
        if (info.isNull()) {
            info.trySet(new SecondLevelMap<>());
        }
        return info.get();
    }
    
    public static Variable queryMember(VariableType modelType, String memberName) {
        return info().tryGet(modelType, memberName);
    }

    public static void addMember(VariableType modelType, String memberName, Variable memberVariable) {
        info().tryPut(modelType, memberName, memberVariable);
    }
}
