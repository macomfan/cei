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
        for (xModel model : sdk.modelList) {
            XMLDatabase.registerModel(sdk.exchange, model.name, model);
        }
        orgSDKList.add(sdk);
    }

    private void mergeSDK(xSDK tag, xSDK src) {

    }

    public List<xSDK> finalizeSDK() {
        System.out.println("-- Start finalize");
        // Merge SDK
//        if (sdkMap.containsKey(sdk.exchange)) {
//            // Merge
//        } else {
//            // Build database
//            sdkMap.put(sdk.exchange, sdk);
//        }
        // Model dependence decision.
        for (xSDK sdk : orgSDKList) {
            Dependence<xModel> dependence = new Dependence<>();
            for (xModel model : sdk.modelList) {
                boolean referToOther = false;
                for (xType item : model.memberList) {
                    if (item instanceof xReferable) {
                        referToOther = true;
                        xModel refer = XMLDatabase.lookupModel(sdk.exchange, ((xReferable) item).refer);
                        dependence.addNode(model, refer);
                    }
                }
                if (!referToOther) {
                    dependence.addNode(model, null);
                }
            }
            sdk.modelList = dependence.decision();
        }
        // Check error
        System.out.println("-- Start end");
        List<xSDK> res = new LinkedList<>(sdkMap.values());
        return orgSDKList;
    }
}
