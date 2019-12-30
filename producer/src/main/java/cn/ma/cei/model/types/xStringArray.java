package cn.ma.cei.model.types;

import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.model.base.xType;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "string_array")
public class xStringArray extends xType {
    public final static String typeName = VariableFactory.genericTypeName("array", "string");

    public final static xStringArray inst = new xStringArray();
    
    @Override
    public VariableType getType() {
        return VariableFactory.variableType("array", VariableFactory.variableType("string"));
    }
}
