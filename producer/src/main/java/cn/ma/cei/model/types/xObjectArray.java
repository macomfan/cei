package cn.ma.cei.model.types;


import cn.ma.cei.exception.CEIException;
import cn.ma.cei.finalizer.Alias;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.model.base.xReferable;

import javax.xml.bind.annotation.XmlRootElement;

@Alias(name = "ModelArray")
@XmlRootElement(name = "object_array")
public class xObjectArray extends xReferable {
    @Override
    public VariableType getType() {
        if (refer == null || refer.equals("")) {
            throw new CEIException("refer is null");
        }
        return VariableFactory.variableType("array", VariableFactory.variableType(refer));
    }
}
