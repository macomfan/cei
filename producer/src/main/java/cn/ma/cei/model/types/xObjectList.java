package cn.ma.cei.model.types;


import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.model.base.xReferable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "object_list")
public class xObjectList extends xReferable {
    @Override
    public VariableType getType() {
        if (refer == null || refer.equals("")) {
            throw new CEIException("refer is null");
        }
        return VariableFactory.variableType("list", VariableFactory.variableType(refer));
    }
}
