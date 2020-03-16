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
import cn.ma.cei.model.websocket.xWebSocket;
import cn.ma.cei.model.xModel;
import cn.ma.cei.model.xSDK;
import cn.ma.cei.model.xSDKClients;
import cn.ma.cei.model.xSDKDefinition;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.utils.NormalMap;
import cn.ma.cei.utils.SecondLevelMap;
import cn.ma.cei.utils.UniquetList;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class XMLDatabase {

    private static SecondLevelMap<String, String, UniquetList<String, xWSInterface>> webSocketInterfaceMap = new SecondLevelMap<>();
    private static SecondLevelMap<String, String, UniquetList<String, xInterface>> restfulInterfaceMap = new SecondLevelMap<>();
    private static SecondLevelMap<String, String, xConnection> restfulClientMap = new SecondLevelMap<>();
    private static SecondLevelMap<String, String, xWSConnection> webSocketClientMap = new SecondLevelMap<>();
    private static SecondLevelMap<String, String, xSignature> signatureMap = new SecondLevelMap<>();
    private static SecondLevelMap<String, String, xModel> modelMap = new SecondLevelMap<>();
    private static NormalMap<String, xSDKDefinition> exchangeMap = new NormalMap<>();
    private static NormalMap<String, xSDK> sdkMap = new NormalMap<>();

//    private static NormalMap<String, xSDK> sdkMap = new NormalMap<>();
//
//    public static void attachSDK(xSDK sdk) {
//        sdkMap.tryPut(sdk.name, sdk);
//    }
//
    public static xSDK getSDK(String exchangeName) {
        if (!sdkMap.containsKey(exchangeName)) {
            CEIErrors.showFailure(CEIErrorType.RUNTIME, "Do not define SDK: %s", exchangeName);
        }
        return sdkMap.get(exchangeName);
    }

//    public static xRestful getClient(String exchangeName, String clientName) {
//        if (!sdkMap.containsKey(exchangeName)) {
//            return null;
//        }
//        xSDK sdk = sdkMap.get(exchangeName);
//        for (xRestful item : sdk.clients.restfulList) {
//            if (item.name.equals(clientName)) {
//                return item;
//            }
//        }
//        return null;
//    }

    public static Collection<xSDK> getSDKs() {
        return sdkMap.values();
    }

    public static void reset() {
        webSocketInterfaceMap.clear();
        restfulInterfaceMap.clear();
        restfulClientMap.clear();
        webSocketClientMap.clear();
        signatureMap.clear();
        modelMap.clear();
        exchangeMap.clear();
        sdkMap.clear();
    }

    public static void ready() {
        sdkMap.clear();
        exchangeMap.entrySet().forEach(exchange -> {
            if (!sdkMap.containsKey(exchange.getKey())) {
                xSDK sdk = new xSDK();
                sdk.name = exchange.getKey();
                sdk.definition = exchange.getValue();
                sdk.filename = sdk.definition.filename;
                sdkMap.put(sdk.name, sdk);
            }
            xSDK sdk = sdkMap.get(exchange.getKey());
            if (modelMap.containsKey1(sdk.name)) {
                sdk.modelList = new LinkedList<>(modelMap.getByKey1(sdk.name).values());
            }
            sdk.clients = new xSDKClients();
            sdk.clients.filename = sdk.filename;
            // For restful client
            if (restfulClientMap.containsKey1(sdk.name)) {
                sdk.clients.restfulList = new LinkedList<>();
                restfulClientMap.getByKey1(sdk.name).entrySet().forEach(entry -> {
                    xRestful restful = new xRestful();
                    restful.filename = entry.getValue().filename;
                    restful.connection = entry.getValue();
                    restful.name = entry.getKey();
                    if (restfulInterfaceMap.containsKey(sdk.name, restful.name)) {
                        restful.interfaceList = new LinkedList<>(restfulInterfaceMap.get(sdk.name, restful.name).values());
                    }
                    sdk.clients.restfulList.add(restful);
                });
            }
            // For websocket client
            if (webSocketClientMap.containsKey1(sdk.name)) {
                sdk.clients.webSocketList = new LinkedList<>();
                webSocketClientMap.getByKey1(sdk.name).entrySet().forEach(entry -> {
                    xWebSocket webSocket = new xWebSocket();
                    webSocket.filename = entry.getValue().filename;
                    webSocket.connection = entry.getValue();
                    webSocket.name = entry.getKey();
                    if (webSocketInterfaceMap.containsKey(sdk.name, webSocket.name)) {
                        webSocket.interfaces = new LinkedList<>(webSocketInterfaceMap.get(sdk.name, webSocket.name).values());
                    }
                    sdk.clients.webSocketList.add(webSocket);
                });
            }

        });
    }

    public static Set<String> getExchangeSet() {
        return exchangeMap.keySet();
    }

    public static Set<String> getClientSet(String exchangeName) {
        Set<String> clients = new HashSet<>();
        if (!exchangeMap.containsKey(exchangeName)) {
            CEIErrors.showFailure(CEIErrorType.RUNTIME, "Do not define SDK: %s", exchangeName);
        }
        if (restfulClientMap.containsKey1(exchangeName)) {
            clients.addAll(restfulClientMap.getByKey1(exchangeName).keySet());
        }
        if (webSocketClientMap.containsKey1(exchangeName)) {
            clients.addAll(webSocketClientMap.getByKey1(exchangeName).keySet());
        }
        return clients;
    }

    public static boolean isExchangeExist(String exchangeName) {
        return exchangeMap.containsKey(exchangeName);
    }


    public static Set<String> getModelSet(String exchangeName) {
        Set<String> models = new HashSet<>();
        if (!exchangeMap.containsKey(exchangeName)) {
            CEIErrors.showFailure(CEIErrorType.RUNTIME, "Do not define SDK: %s", exchangeName);
        }
        if (modelMap.containsKey1(exchangeName)) {
            models.addAll(modelMap.getByKey1(exchangeName).keySet());
        }
        return models;
    }

    public static void registerSDK(xSDK sdk) {
        if (sdk.definition == null) {
            CEIErrors.showFailure(CEIErrorType.CODE, "Cannot register a SDK without definition");
        }
        if (exchangeMap.containsKey(sdk.name)) {
            xSDKDefinition exist = exchangeMap.get(sdk.name);
            CEIErrors.showFailure(CEIErrorType.XML, duplicateInfo("Model", sdk.name, sdk.definition, exist));
        } else {
            exchangeMap.tryPut(sdk.name, sdk.definition);
        }
    }

    private static void checkSDKExist(String exchange) {
        if (!exchangeMap.containsKey(exchange)) {
            CEIErrors.showFailure(CEIErrorType.XML, NoDefineInfo("SDK", exchange, null));
        }
    }
    private static <T> void checkClientExist(String exchange, String clientName, SecondLevelMap<String, String, T> map, xElement what) {
        if (!map.containsKey(exchange, clientName)) {
            CEIErrors.showFailure(CEIErrorType.XML, NoDefineInfo("Client", clientName, what));
        }
    }

    public static void registerModel(String exchange, String modelName, xModel model) {
        try {
            if (Checker.isEmpty(modelName) || model == null) {
                CEIErrors.showFailure(CEIErrorType.CODE, "Model is null in registerModel.");
                return;
            }
            checkSDKExist(exchange);
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
        checkSDKExist(exchange);

        if (restfulClientMap.containsKey(exchange, clientName)) {
            xConnection existed = restfulClientMap.get(exchange, clientName);
            CEIErrors.showFailure(CEIErrorType.XML, duplicateInfo("Client", clientName, connection, existed));
        }
        innerRegisterClient(webSocketClientMap, exchange, clientName, connection, "WebSocket");
    }

    public static void registerRestfulClient(String exchange, String clientName, xConnection connection) {
        checkSDKExist(exchange);
        if (webSocketClientMap.containsKey(exchange, clientName)) {
            xWSConnection existed = webSocketClientMap.get(exchange, clientName);
            CEIErrors.showFailure(CEIErrorType.XML, duplicateInfo("Client", clientName, connection, existed));
        }
        innerRegisterClient(restfulClientMap, exchange, clientName, connection, "Restful");
    }

    public static void registerRestfulInterface(String exchange, String clientName, String interfaceName, xInterface intf) {
        checkClientExist(exchange, clientName, restfulClientMap, intf);
        innerRegisterInterface(restfulInterfaceMap, exchange, clientName, interfaceName, intf, "Restful");
    }

    public static void registerWebSocketInterface(String exchange, String clientName, String interfaceName, xWSInterface intf) {
        checkClientExist(exchange, clientName, webSocketClientMap, intf);
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

    private static String NoDefineInfo(String title, String name, xElement e) {
        String info = String.format("%s \"%s\" do not have the definition", title, name);
        if (e != null) {
            info = String.format("%s\nFile: %s", info, e.filename);
        }
        return info;
    }
}
