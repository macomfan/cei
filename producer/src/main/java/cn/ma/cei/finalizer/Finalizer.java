package cn.ma.cei.finalizer;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.model.base.xReferable;
import cn.ma.cei.model.base.xType;
import cn.ma.cei.model.xModel;
import cn.ma.cei.model.xSDK;
import cn.ma.cei.utils.Checker;

import java.util.LinkedList;
import java.util.List;

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
        //  - Authentication
        if (sdk.definition != null) {
            XMLDatabase.registrySDK(sdk);
        }
        orgSDKList.add(sdk);
    }

    public List<xSDK> finalizeSDK() {
        CEIErrors.showDebug("==== %s ====", "Start finalize");

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
        CEIErrors.showDebug("==== %s ====", "End finalize");
        return new LinkedList<>(XMLDatabase.getSDKs());
    }
}
