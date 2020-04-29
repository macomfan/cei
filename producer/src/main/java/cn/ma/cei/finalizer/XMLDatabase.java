package cn.ma.cei.finalizer;

import cn.ma.cei.exception.CEIErrorType;
import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.exception.CEIException;
import cn.ma.cei.model.restful.xRestful;
import cn.ma.cei.model.websocket.xWebSocket;
import cn.ma.cei.model.xCustomProcedures;
import cn.ma.cei.model.xModel;
import cn.ma.cei.model.xSDK;
import cn.ma.cei.model.xSDKClients;
import cn.ma.cei.utils.NormalMap;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


public class XMLDatabase {
    private static final NormalMap<String, ExchangeData> sdkData = new NormalMap<>();
    private static final NormalMap<String, xSDK> sdkMap = new NormalMap<>();

    public static void reset() {
        sdkData.clear();
        sdkMap.clear();
    }

    public static xModel lookupModel(String exchange, String modelName) {
        try {
            if (!sdkData.containsKey(exchange)) {
                CEIErrors.showFailure(CEIErrorType.CODE, "Not found");
            }
            return sdkData.get(exchange).getModel(modelName);
        } catch (CEIException e) {
            throw new CEIException("Cannot find model: " + modelName);
        }
    }

    public static xSDK getSDK(String exchangeName) {
        return sdkMap.get(exchangeName);
    }

    public static Set<String> getModelSet(String exchangeName) {
        ExchangeData data = sdkData.get(exchangeName);
        Set<String> models = new HashSet<>();
        data.getModelList().forEach(model -> models.add(model.name));
        return models;
    }

    public static boolean containsExchange(String exchangeName) {
        return sdkData.containsKey(exchangeName);
    }

    public static Set<String> getExchangeSet() {
        return sdkData.keySet();
    }

    public static Collection<xSDK> getSDKs() {
        if (sdkMap.size() != 0) {
            return sdkMap.values();
        }

        sdkData.values().forEach(data -> {
            xSDK sdk = new xSDK();
            sdk.name = data.getName();
            sdk.definition = data.getDefinition();
            sdk.filename = sdk.definition.filename;
            sdkMap.put(sdk.name, sdk);
            // For model
            sdk.modelList = data.getModelList();
            // For authentication
            sdk.procedures = new xCustomProcedures();
            sdk.procedures.filename = sdk.filename;
            sdk.procedures.functions = data.getFunctionList();

            sdk.clients = new xSDKClients();
            sdk.clients.filename = sdk.filename;
            sdk.clients.restfulList = new LinkedList<>();
            sdk.clients.webSocketList = new LinkedList<>();
            // For restful client
            data.getRestfulClientList().forEach(item -> {
                xRestful client = new xRestful();
                client.name = item;
                client.connection = data.getRestfulConnection(item);
                client.interfaceList = data.getRestfulInterfaceList(item);
                sdk.clients.restfulList.add(client);
            });

            // For WebSocket client
            data.getWSClientList().forEach(item -> {
                xWebSocket client = new xWebSocket();
                client.name = item;
                client.connection = data.getWSConnection(item);
                client.events = data.getWSEventList(item);
                client.interfaces = data.getWSInterfaceList(item);
                sdk.clients.webSocketList.add(client);
            });

        });
        return sdkMap.values();
    }


    public static void registrySDK(xSDK sdk) {
        if (!sdkData.containsKey(sdk.name)) {
            sdkData.put(sdk.name, new ExchangeData(sdk.name));
        }
        ExchangeData exchangeData = sdkData.get(sdk.name);
        exchangeData.setDefinition(sdk.definition);
        exchangeData.appendModelList(sdk.modelList);
        if (sdk.procedures != null) {
            exchangeData.appendFunction(sdk.procedures.functions);
        }
        if (sdk.clients != null && sdk.clients.restfulList != null) {
            sdk.clients.restfulList.forEach(item -> {
                if (item.connection != null) {
                    exchangeData.addRestfulClient(item.name, item.connection);
                }
                exchangeData.appendRestfulInterfaceList(item.name, item.interfaceList);
            });
        }
        if (sdk.clients != null && sdk.clients.webSocketList != null) {
            sdk.clients.webSocketList.forEach(item -> {
                if (item.connection != null) {
                    exchangeData.addWSClient(item.name, item.connection);
                }
                exchangeData.appendWebSocketInterfaceList(item.name, item.interfaces);
                exchangeData.appendWebSocketEventList(item.name, item.events);
            });
        }
    }
}
