package cn.ma.cei.model.types;

import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.database.VariableFactory;
import cn.ma.cei.model.base.xType;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "boolean")
public class xBoolean extends xType {

    public final static String typeName = "boolean";

    @Override
    public VariableType getType() {
        return VariableFactory.variableType(typeName);
    }
}
