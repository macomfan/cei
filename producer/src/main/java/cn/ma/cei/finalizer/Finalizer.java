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

    public Finalizer() {
        XMLDatabase.reset();
    }

    public void addSDK(List<xSDK> sdks) {
        sdks.forEach(this::addSDK);
    }

    public void addSDK(xSDK sdk) {
        // Register Database
        XMLDatabase.registrySDK(sdk);
    }

    public List<xSDK> finalizeSDK() {
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
        CEIErrors.showInfo("Reconcile XML configuration done");
        return new LinkedList<>(XMLDatabase.getSDKs());
    }
}
