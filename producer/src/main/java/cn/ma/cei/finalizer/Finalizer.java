package cn.ma.cei.finalizer;

import cn.ma.cei.model.base.xReferable;
import cn.ma.cei.model.base.xType;
import cn.ma.cei.model.xModel;
import cn.ma.cei.model.xSDK;
import cn.ma.cei.utils.Checker;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Finalizer {
    private List<xSDK> orgSDKList = new LinkedList<>();

    public Finalizer() {
        XMLDatabase.reset();
    }

    public void addSDK(List<xSDK> sdks) {
        for (xSDK sdk : sdks) {
            addSDK(sdk);
        }
    }

    public void addSDK(xSDK sdk) {
        // Register Database
        //  - Model
        //  - Restful
        //  - Signature
        sdk.doCheck();
        if (sdk.definition != null) {
            XMLDatabase.registerSDK(sdk);
        }
        orgSDKList.add(sdk);
    }

    private void registerSDK() {
        orgSDKList.forEach(sdk -> {
            if (sdk.modelList != null) {
                sdk.modelList.forEach(model -> XMLDatabase.registerModel(sdk.name, model.name, model));
            }
            if (sdk.clients != null) {
                if (sdk.clients.restfulList != null) {
                    sdk.clients.restfulList.forEach(client -> {
                        if (client.connection != null) {
                            XMLDatabase.registerRestfulClient(sdk.name, client.name, client.connection);
                        }
                        if (client.interfaceList != null) {
                            client.interfaceList.forEach(intf -> {
                                XMLDatabase.registerRestfulInterface(sdk.name, client.name, intf.name, intf);
                            });
                        }
                    });
                }
            }
        });
        orgSDKList.forEach(sdk -> {
            if (sdk.clients == null) {
                return;
            }
            if (sdk.clients.webSocketList != null) {
                sdk.clients.webSocketList.forEach(client -> {
                    if (client.connection != null) {
                        XMLDatabase.registerWebSocketClient(sdk.name, client.name, client.connection);
                    }
                    if (client.interfaces != null) {
                        client.interfaces.forEach(intf -> {
                            XMLDatabase.registerWebSocketInterface(sdk.name, client.name, intf.name, intf);
                        });
                    }
                    if (client.actions != null) {
                        client.actions.forEach(action -> {
                            XMLDatabase.registerWebSocketAction(sdk.name, client.name, action.name, action);
                        });
                    }
                });
            }
        });
    }

    public List<xSDK> finalizeSDK() {
        System.out.println("-- Start finalize");
        registerSDK();
        System.out.println("-- Reg done finalize");
        XMLDatabase.ready();
//        // Merge SDK
//        orgSDKList.forEach(sdk -> {
//            if (sdkMap.containsKey(sdk.name)) {
//                xSDK orgSdk = sdkMap.get(sdk.name);
//                orgSdk.merge(sdk);
//            } else {
//                sdkMap.put(sdk.name, sdk);
//            }
//        });

        // Check model dependency
        XMLDatabase.getSDKs().forEach(sdk -> {
            if (Checker.isNull(sdk.modelList)) {
                return;
            }
            Dependence<xModel> dependence = new Dependence<>();
            sdk.modelList.forEach((model) -> {
                if (Checker.isNull(model.memberList)) {
                    return;
                }
                boolean referToOther = false;
                for (xType item : model.memberList) {
                    if (item instanceof xReferable) {
                        referToOther = true;
                        xModel refer = XMLDatabase.lookupModel(sdk.name, ((xReferable) item).model);
                        dependence.addNode(model, refer);
                    }
                }
                if (!referToOther) {
                    dependence.addNode(model, null);
                }
            });
            sdk.modelList = dependence.decision();
        });
        // Check error
        System.out.println("-- Start end");

        return new LinkedList<>(XMLDatabase.getSDKs());
    }
}
