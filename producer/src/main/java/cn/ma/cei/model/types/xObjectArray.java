package cn.ma.cei.model.types;


import cn.ma.cei.exception.CEIException;
import cn.ma.cei.finalizer.Alias;
import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.model.base.xReferable;

import javax.xml.bind.annotation.XmlRootElement;

@Alias(name = "ModelArray")
@XmlRootElement(name = "object_array")
public class xObjectArray extends xReferable {
    @Override
    public VariableType getType() {
        if (model == null || model.equals("")) {
            throw new CEIException("refer is null");
        }
        return BuilderContext.variableType("array", BuilderContext.variableType(model));
    }
}
