package cn.ma.cei.model.types;

import cn.ma.cei.finalizer.Alias;
import cn.ma.cei.generator.buildin.TheArray;
import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.model.base.xType;

import javax.xml.bind.annotation.XmlRootElement;

@Alias(name = "IntegerArray")
@XmlRootElement(name = "int_array")
public class xIntArray extends xType {
    public final static String typeName = BuilderContext.genericTypeName(TheArray.typeName, xInt.typeName);

    public final static xIntArray inst = new xIntArray();

    @Override
    public VariableType getType() {
        return BuilderContext.variableType(TheArray.typeName, BuilderContext.variableType(xInt.typeName));
    }
}
