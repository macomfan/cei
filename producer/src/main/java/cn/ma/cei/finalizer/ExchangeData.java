package cn.ma.cei.finalizer;

import cn.ma.cei.exception.CEIErrorType;
import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.restful.xAuthentication;
import cn.ma.cei.model.restful.xConnection;
import cn.ma.cei.model.restful.xInterface;
import cn.ma.cei.model.websocket.xAction;
import cn.ma.cei.model.websocket.xWSAuthentication;
import cn.ma.cei.model.websocket.xWSConnection;
import cn.ma.cei.model.websocket.xWSInterface;
import cn.ma.cei.model.xModel;
import cn.ma.cei.model.xSDKDefinition;
import cn.ma.cei.utils.NormalMap;
import cn.ma.cei.utils.UniqueList;

import java.util.List;

public class ExchangeData {

    @FunctionalInterface
    private interface GetKey<T> {
        String get(T item);
    }

    private static class ListData<T> {
        private UniqueList<String, T> data = new UniqueList<>();

        public void append(String key, T item) {
            if (data.containsKey(key)) {
                CEIErrors.showFailure(CEIErrorType.XML, "Dup");
            }
            data.put(key, item);
        }

        private T get(String key) {
            if (!data.containsKey(key)) {
                CEIErrors.showFailure(CEIErrorType.XML, "Not found");
            }
            return data.get(key);
        }

        public void appendList(List<T> list, GetKey<T> getKey) {
            if (list == null) {
                return;
            }
            list.forEach(item -> {
                if (data.containsKey(getKey.get(item))) {
                    CEIErrors.showFailure(CEIErrorType.XML, "Dup");
                }
                data.put(getKey.get(item), item);
            });
        }

        public List<String> getKeys() {
            return data.indexs();
        }

        public List<T> getList() {
            return data.values();
        }
    }

    private static class InterfaceData<T> {
        private NormalMap<String, UniqueList<String, T>> data = new NormalMap<>();

        public void appendList(String clientName, List<T> list, GetKey<T> getKey) {
            if (list == null) {
                return;
            }
            if (!data.containsKey(clientName)) {
                data.put(clientName, new UniqueList<>());
            }
            UniqueList<String, T> interfaces = data.get(clientName);
            list.forEach(item -> {
                if (interfaces.containsKey(getKey.get(item))) {
                    CEIErrors.showFailure(CEIErrorType.XML, "Dup");
                }
                interfaces.put(getKey.get(item), item);
            });
        }

        public List<T> getList(String clientName) {
            if (!data.containsKey(clientName)) {
                return null;
            }
            return data.get(clientName).values();
        }
    }

    private String name;
    private xSDKDefinition definition;

    private NormalMap<String, xElement> nameMap = new NormalMap<>();
    private ListData<xModel> modelList = new ListData<>();
    private ListData<xConnection> restfulClientList = new ListData<>();
    private ListData<xWSConnection> wsClientList = new ListData<>();

    private ListData<xAuthentication> restfulAuthList = new ListData<>();
    private ListData<xWSAuthentication> wsAuthList = new ListData<>();
    private NormalMap<String, xElement> authNameMap = new NormalMap<>();

    private InterfaceData<xInterface> restfulInterfaceList = new InterfaceData<>();
    private InterfaceData<xWSInterface> wsInterfaceList = new InterfaceData<>();
    private InterfaceData<xAction> wsActionList = new InterfaceData<>();

    public ExchangeData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDefinition(xSDKDefinition definition) {
        if (definition == null) return;
        if (this.definition != null) {
            CEIErrors.showFailure(CEIErrorType.XML, "Dup");
            return;
        }
        this.definition = definition;
    }

    public xModel getModel(String modelName) {
        return modelList.get(modelName);
    }

    public void appendModelList(List<xModel> list) {
        checkName(list, item -> item.name);
        modelList.appendList(list, item -> item.name);
    }

    public void appendRestfulAuth(List<xAuthentication> list) {
        checkAuthName(list, item -> item.name);
        restfulAuthList.appendList(list, item -> item.name);
    }

    public void appendWSAuth(List<xWSAuthentication> list) {
        checkAuthName(list, item -> item.name);
        wsAuthList.appendList(list, item -> item.name);
    }

    public void addRestfulClient(String name, xConnection connection) {
        checkName(name, connection);
        restfulClientList.append(name, connection);
    }

    public void addWSClient(String name, xWSConnection connection) {
        checkName(name, connection);
        wsClientList.append(name, connection);
    }

    public void appendRestfulInterfaceList(String clientName, List<xInterface> list) {
        restfulInterfaceList.appendList(clientName, list, item -> item.name);
    }

    public void appendWebSocketInterfaceList(String clientName, List<xWSInterface> list) {
        wsInterfaceList.appendList(clientName, list, item -> item.name);
    }

    public void appendWebSocketActionList(String clientName, List<xAction> list) {
        wsActionList.appendList(clientName, list, item -> item.name);
    }

    public xSDKDefinition getDefinition() {
        return definition;
    }

    public List<xModel> getModelList() {
        return modelList.getList();
    }

    public List<xAuthentication> getRestfulAuthList() {
        return restfulAuthList.getList();
    }

    public List<xWSAuthentication> getWSAuthList() {
        return wsAuthList.getList();
    }

    public List<String> getRestfulClientList() {
        return restfulClientList.getKeys();
    }

    public List<String> getWSClientList() {
        return wsClientList.getKeys();
    }

    public xConnection getRestfulConnection(String clientName) {
        return restfulClientList.get(clientName);
    }

    public xWSConnection getWSConnection(String clientName) {
        return wsClientList.get(clientName);
    }

    public List<xInterface> getRestfulInterfaceList(String clientName) {
        return restfulInterfaceList.getList(clientName);
    }

    public List<xWSInterface> getWSInterfaceList(String clientName) {
        return wsInterfaceList.getList(clientName);
    }

    public List<xAction> getWSActionList(String clientName) {
        return wsActionList.getList(clientName);
    }

    private <T extends xElement> void checkName(List<T> list, GetKey<T> getKey) {
        if (list == null) {
            return;
        }
        list.forEach(item -> checkName(getKey.get(item), item));
    }

    private <T extends xElement> void checkName(String name, T value) {
        if (nameMap.containsKey(name)) {
            CEIErrors.showFailure(CEIErrorType.XML, "Dup");
        }
        nameMap.put(name, value);
    }

    private <T extends xElement> void checkAuthName(List<T> list, GetKey<T> getKey) {
        if (list == null) {
            return;
        }
        list.forEach(item -> {
            if (authNameMap.containsKey(getKey.get(item))) {
                CEIErrors.showFailure(CEIErrorType.XML, "Dup");
            }
            authNameMap.put(getKey.get(item), item);
        });

    }
}
