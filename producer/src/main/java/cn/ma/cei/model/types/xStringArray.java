package cn.ma.cei.model.types;

import cn.ma.cei.finalizer.Alias;
import cn.ma.cei.generator.buildin.TheArray;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.model.base.xType;

import javax.xml.bind.annotation.XmlRootElement;

@Alias(name = "StringArray")
@XmlRootElement(name = "string_array")
public class xStringArray extends xType {
    public final static String typeName = VariableFactory.genericTypeName(TheArray.typeName, xString.typeName);

    public final static xStringArray inst = new xStringArray();
    
    @Override
    public VariableType getType() {
        return VariableFactory.variableType(TheArray.typeName, VariableFactory.variableType(xString.typeName));
    }
}
