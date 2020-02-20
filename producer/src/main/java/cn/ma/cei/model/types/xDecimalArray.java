package cn.ma.cei.model.types;

import cn.ma.cei.finalizer.Alias;
import cn.ma.cei.generator.buildin.TheArray;
import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.model.base.xType;

import javax.xml.bind.annotation.XmlRootElement;

@Alias(name = "DecimalArray")
@XmlRootElement(name = "decimal_array")
public class xDecimalArray extends xType {
    public final static String typeName = BuilderContext.genericTypeName(TheArray.typeName, xDecimal.typeName);

    public final static xDecimalArray inst = new xDecimalArray();

    @Override
    public VariableType getType() {
        return BuilderContext.variableType(TheArray.typeName, BuilderContext.variableType(xDecimal.typeName));
    }
}
