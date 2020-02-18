package cn.ma.cei.finalizer;

import cn.ma.cei.model.base.xReferable;
import cn.ma.cei.model.base.xType;
import cn.ma.cei.model.xModel;
import cn.ma.cei.model.xSDK;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Finalizer {
    private Map<String, xSDK> sdkMap = new HashMap<>();
    private List<xSDK> orgSDKList = new LinkedList<>();

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
        if (sdk.modelList != null) {
            sdk.modelList.forEach(model -> XMLDatabase.registerModel(sdk.name, model.name, model));
        }
        orgSDKList.add(sdk);
    }

    private void mergeSDK(xSDK tag, xSDK src) {

    }

    public List<xSDK> finalizeSDK() {
        System.out.println("-- Start finalize");
        orgSDKList.forEach(sdk-> {
            if (sdkMap.containsKey(sdk.name)) {
                xSDK orgSdk = sdkMap.get(sdk.name);
                orgSdk.merge(sdk);
            } else {
                sdkMap.put(sdk.name, sdk);
            }
        });
        // Merge SDK
//        if (sdkMap.containsKey(sdk.exchange)) {
//            // Merge
//        } else {
//            // Build database
//            sdkMap.put(sdk.exchange, sdk);
//        }
        // Model dependence decision.
        sdkMap.values().forEach(sdk -> {
            sdk.doCheck();
            Dependence<xModel> dependence = new Dependence<>();
            for (xModel model : sdk.modelList) {
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
            }
            sdk.modelList = dependence.decision();
        });
        // Check error
        System.out.println("-- Start end");
        sdkMap.values().forEach((sdk) -> {
            sdk.doCheck();
            XMLDatabase.attachSDK(sdk);
        });
        return new LinkedList<>(sdkMap.values());
    }
}
