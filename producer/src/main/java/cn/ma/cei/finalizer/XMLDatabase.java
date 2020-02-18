package cn.ma.cei.finalizer;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.model.types.*;
import cn.ma.cei.model.xModel;
import cn.ma.cei.model.xRestful;
import cn.ma.cei.model.xSDK;
import cn.ma.cei.utils.NormalMap;
import cn.ma.cei.utils.SecondLevelMap;

import java.util.HashSet;
import java.util.Set;

public class XMLDatabase {
    private static SecondLevelMap<String, String, xModel> modelMap = new SecondLevelMap<>();
    private static NormalMap<String, xSDK> sdkMap = new NormalMap<>();

    public static void attachSDK(xSDK sdk) {
        sdkMap.tryPut(sdk.name, sdk);
    }

    public static xSDK getSDK(String exchangeName) {
        if (!sdkMap.containsKey(exchangeName)) {
            return null;
        }
        return sdkMap.get(exchangeName);
    }

    public static xRestful getClient(String exchangeName, String clientName) {
        if (!sdkMap.containsKey(exchangeName)) {
            return null;
        }
        xSDK sdk = sdkMap.get(exchangeName);
        for (xRestful item : sdk.restfulList) {
            if (item.name.equals(clientName)) {
                return item;
            }
        }
        return null;
    }

    public static Set<String> getExchangeSet() {
        return sdkMap.keySet();
    }

    public static Set<String> getClientSet(String exchangeName) {
        xSDK sdk = getSDK(exchangeName);
        if (sdk == null) return null;
        Set<String> res = new HashSet<>();
        sdk.restfulList.forEach((client) -> res.add(client.name));
        return res;
    }

    public static boolean isExchangeExist(String exchangeName) {
        return sdkMap.containsKey(exchangeName);
    }


    public static Set<String> getModelSet(String exchangeName) {
        xSDK sdk = getSDK(exchangeName);
        if (sdk == null) return null;
        Set<String> res = new HashSet<>();
        sdk.modelList.forEach((model) -> res.add(model.name));
        return res;
    }

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
