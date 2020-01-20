package cn.ma.cei.model.types;

import cn.ma.cei.generator.buildin.TheArray;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.model.base.xType;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "long_array")
public class xLongArray extends xType {
    public final static String typeName = VariableFactory.genericTypeName(TheArray.typeName, xLong.typeName);

    public final static xLongArray inst = new xLongArray();

    @Override
    public VariableType getType() {
        return VariableFactory.variableType(TheArray.typeName, VariableFactory.variableType(xLong.typeName));
    }
}