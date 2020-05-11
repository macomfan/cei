package cn.ma.cei.finalizer;

import cn.ma.cei.exception.CEIErrorType;
import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.model.base.xElement;
import cn.ma.cei.model.restful.xConnection;
import cn.ma.cei.model.restful.xInterface;
import cn.ma.cei.model.websocket.xWSConnection;
import cn.ma.cei.model.websocket.xWSInterface;
import cn.ma.cei.model.websocket.xWSOnMessage;
import cn.ma.cei.model.xFunction;
import cn.ma.cei.model.xModel;
import cn.ma.cei.model.xSDKDefinition;
import cn.ma.cei.utils.NormalMap;
import cn.ma.cei.utils.UniqueList;

import java.util.List;

class ExchangeData {

    @FunctionalInterface
    private interface GetKey<T> {
        String get(T item);
    }

    private static class ListData<T> {
        private final UniqueList<String, T> data = new UniqueList<>();

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
        private final NormalMap<String, UniqueList<String, T>> data = new NormalMap<>();

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
                    CEIErrors.showFailure(CEIErrorType.XML, "Duplicate name exist: %s", getKey.get(item));
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

    private final String name;
    private xSDKDefinition definition;

    private final NormalMap<String, xElement> nameMap = new NormalMap<>();
    private final ListData<xModel> modelList = new ListData<>();
    private final ListData<xConnection> restfulClientList = new ListData<>();
    private final ListData<xWSConnection> wsClientList = new ListData<>();

    private final ListData<xFunction> functionList = new ListData<>();
    private final NormalMap<String, xElement> funcNameMap = new NormalMap<>();

    private final InterfaceData<xInterface> restfulInterfaceList = new InterfaceData<>();
    private final InterfaceData<xWSInterface> wsInterfaceList = new InterfaceData<>();
    private final InterfaceData<xWSOnMessage> wsEventList = new InterfaceData<>();

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

    public void appendFunction(List<xFunction> list) {
        checkFunctionName(list, item -> item.name);
        functionList.appendList(list, item -> item.name);
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

    public void appendWebSocketEventList(String clientName, List<xWSOnMessage> list) {
        wsEventList.appendList(clientName, list, item -> item.name);
    }

    public xSDKDefinition getDefinition() {
        return definition;
    }

    public List<xModel> getModelList() {
        return modelList.getList();
    }

    public List<xFunction> getFunctionList() {
        return functionList.getList();
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

    public List<xWSOnMessage> getWSEventList(String clientName) {
        return wsEventList.getList(clientName);
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

    private <T extends xElement> void checkFunctionName(List<T> list, GetKey<T> getKey) {
        if (list == null) {
            return;
        }
        list.forEach(item -> {
            if (funcNameMap.containsKey(getKey.get(item))) {
                CEIErrors.showFailure(CEIErrorType.XML, "Dup");
            }
            funcNameMap.put(getKey.get(item), item);
        });

    }
}
