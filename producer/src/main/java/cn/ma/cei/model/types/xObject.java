package cn.ma.cei.model.types;

import cn.ma.cei.exception.CEIException;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.database.VariableFactory;
import cn.ma.cei.model.base.xReferable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "object")
public class xObject extends xReferable {
    @Override
    public VariableType getType() {
        if (refer == null || refer.equals("")) {
            throw new CEIException("refer is null");
        }
        return VariableFactory.variableType(refer);
    }

}
