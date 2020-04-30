package cn.ma.cei.model.types;

import cn.ma.cei.finalizer.Alias;
import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.buildin.TheArray;
import cn.ma.cei.model.base.xType;

import javax.xml.bind.annotation.XmlRootElement;

@Alias(name = "BooleanArray")
@XmlRootElement(name = "boolean_array")
public class xBooleanArray extends xType {
    public final static String typeName = BuilderContext.genericTypeName(TheArray.typeName, xBoolean.typeName);

    public final static xBooleanArray inst = new xBooleanArray();

    @Override
    public VariableType getType() {
        return BuilderContext.variableType(TheArray.typeName, BuilderContext.variableType(xBoolean.typeName));
    }
}
