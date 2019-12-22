package cn.ma.cei.model.types;

import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.database.VariableFactory;
import cn.ma.cei.model.base.xType;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "string_list")
public class xStringList extends xType {
    public final static String typeName = VariableFactory.genericTypeName("list", "string");

    @Override
    public VariableType getType() {
        return VariableFactory.variableType("list", VariableFactory.variableType("string"));
    }
}
