package cn.ma.cei.finalizer;

import cn.ma.cei.exception.CEIErrorType;
import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.exception.CEIException;
import cn.ma.cei.exception.CEIInnerException;
import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.restful.xConnection;
import cn.ma.cei.model.restful.xInterface;
import cn.ma.cei.model.restful.xRestful;
import cn.ma.cei.model.signature.xSignature;
import cn.ma.cei.model.websocket.xWSConnection;
import cn.ma.cei.model.websocket.xWSInterface;
import cn.ma.cei.model.xModel;
import cn.ma.cei.model.xSDK;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.NormalMap;
import cn.ma.cei.utils.SecondLevelMap;
import cn.ma.cei.utils.UniquetList;

import java.util.HashSet;
import java.util.Set;

public class XMLDatabase {
    private static SecondLevelMap<String, String, UniquetList<String, xWSInterface>> webSocketInterfaceMap = new SecondLevelMap<>();
    private static SecondLevelMap<String, String, UniquetList<String, xInterface>> restfulInterfaceMap = new SecondLevelMap<>();
    private static SecondLevelMap<String, String, xConnection> restfulClientMap = new SecondLevelMap<>();
    private static SecondLevelMap<String, String, xWSConnection> webSocketClientMap = new SecondLevelMap<>();
    private static SecondLevelMap<String, String, xSignature> signatureMap = new SecondLevelMap<>();
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
        for (xRestful item : sdk.clients.restfulList) {
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
        sdk.clients.restfulList.forEach((client) -> res.add(client.name));
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

    public static void registerModel(String exchange, String modelName, xModel model) {
        try {
            if (Checker.isEmpty(modelName) || model == null) {
                CEIErrors.showFailure(CEIErrorType.CODE, "Model is null in registerModel.");
                return;
            }
            if (modelMap.containsKey(exchange, modelName)) {
                xModel exist = modelMap.get(exchange, modelName);
                CEIErrors.showFailure(CEIErrorType.XML, duplicateInfo("Model", modelName, model, exist));
            }
            modelMap.tryPut(exchange, modelName, model);
        } catch (CEIInnerException e) {
            CEIErrors.showFailure(CEIErrorType.CODE, "Map error in registerModel.");
        }
    }

    public static void registerWebSocketClient(String exchange, String clientName, xWSConnection connection) {
        innerRegisterClient(webSocketClientMap, exchange, clientName, connection, "WebSocket");
    }

    public static void registerRestfulClient(String exchange, String clientName, xConnection connection) {
        innerRegisterClient(restfulClientMap, exchange, clientName, connection, "Restful");
    }

    public static void registerRestfulInterface(String exchange, String clientName, String interfaceName, xInterface intf) {
        innerRegisterInterface(restfulInterfaceMap, exchange, clientName, interfaceName, intf, "Restful");
    }

    public static void registerWebSocketInterface(String exchange, String clientName, String interfaceName, xWSInterface intf) {
        innerRegisterInterface(webSocketInterfaceMap, exchange, clientName, interfaceName, intf, "WebSocket");
    }

    private static <T extends xElement> void innerRegisterInterface(
            SecondLevelMap<String, String, UniquetList<String, T>> to, String exchange, String clientName, String interfaceName, T intf, String type) {
        try {
            if (Checker.isEmpty(clientName) || Checker.isEmpty(interfaceName) || intf == null) {
                CEIErrors.showFailure(CEIErrorType.CODE, "Interface is null in registerInterface.");
                return;
            }
            if (to.containsKey(exchange, clientName)) {
                UniquetList<String, T> list = to.get(exchange, clientName);
                if (list.containsKey(interfaceName)) {
                    T exist = list.get(interfaceName);
                    CEIErrors.showFailure(CEIErrorType.XML, duplicateInfo(type + " client", clientName, intf, exist));
                }
                list.put(interfaceName, intf);
            } else {
                UniquetList<String, T> list = new UniquetList<>();
                to.put(exchange, clientName, list);
                list.put(interfaceName, intf);
            }
        } catch (CEIInnerException e) {
            CEIErrors.showFailure(CEIErrorType.CODE, "Map error in registerInterface.");
        }
    }

    private static <T extends xElement> void innerRegisterClient(
            SecondLevelMap<String, String, T> to, String exchange, String clientName, T connection, String type) {
        try {
            if (Checker.isEmpty(clientName) || connection == null) {
                CEIErrors.showFailure(CEIErrorType.CODE, "Client is null in restfulClient.");
                return;
            }
            if (to.containsKey(exchange, clientName)) {
                T exist = to.get(exchange, clientName);
                CEIErrors.showFailure(CEIErrorType.XML, duplicateInfo(type + " client", clientName, connection, exist));
            }
            to.tryPut(exchange, clientName, connection);
        } catch (CEIInnerException e) {
            CEIErrors.showFailure(CEIErrorType.CODE, "Map error in restfulClient.");
        }
    }

    public static xModel lookupModel(String exchange, String modelName) {
        try {
            return modelMap.tryGet(exchange, modelName);
        } catch (CEIException e) {
            throw new CEIException("Cannot find model: " + modelName);
        }
    }

    private static String duplicateInfo(String title, String name, xElement e1, xElement e2) {
        String info = String.format("Duplicate define of %s: %s", title, name);
        info = String.format("%s\nFile1: %s\nFile2: %s", info, e1.filename, e2.filename);
        return info;
    }
}
