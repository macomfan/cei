package cn.ma.cei.model.types;

import cn.ma.cei.generator.buildin.TheArray;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.model.base.xType;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "int_array")
public class xIntArray extends xType {
    public final static String typeName = VariableFactory.genericTypeName(TheArray.typeName, xInt.typeName);

    public final static xIntArray inst = new xIntArray();

    @Override
    public VariableType getType() {
        return VariableFactory.variableType(TheArray.typeName, VariableFactory.variableType(xInt.typeName));
    }
}
