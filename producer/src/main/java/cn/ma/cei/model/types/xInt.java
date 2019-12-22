package cn.ma.cei.model.types;

import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.database.VariableFactory;
import cn.ma.cei.model.base.xType;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "int")
public class xInt extends xType {
    public final static String typeName = "int";

    @Override
    public VariableType getType() {
        return VariableFactory.variableType(typeName);
    }
}