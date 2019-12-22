package cn.ma.cei.finalizer;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.utils.SecondLevelMap;
import cn.ma.cei.model.xModel;

public class XMLDatabase {
    private static SecondLevelMap<String, String, xModel> modelMap = new SecondLevelMap<>();

    public static boolean registerModel(String exchange, String modelName, xModel xModel) {
        try {
            modelMap.tryPut(exchange, modelName, xModel);
        } catch (CEIException e) {
            throw new CEIException("Model redefine: " + modelName);
        }
        return true;
    }

    public static xModel lookupModel(String exchange, String modelName) {
        try {
            return modelMap.tryGet(exchange, modelName);
        } catch (CEIException e) {
            throw new CEIException("Cannot find model: " + modelName);
        }
    }
}
