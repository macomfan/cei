package cn.ma.cei.model.types;

import cn.ma.cei.finalizer.Alias;
import cn.ma.cei.generator.buildin.TheArray;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.model.base.xType;

import javax.xml.bind.annotation.XmlRootElement;

@Alias(name = "BooleanArray")
@XmlRootElement(name = "boolean_array")
public class xBooleanArray extends xType {
    public final static String typeName = VariableFactory.genericTypeName(TheArray.typeName, xBoolean.typeName);

    public final static xBooleanArray inst = new xBooleanArray();

    @Override
    public VariableType getType() {
        return VariableFactory.variableType(TheArray.typeName, VariableFactory.variableType(xBoolean.typeName));
    }
}
