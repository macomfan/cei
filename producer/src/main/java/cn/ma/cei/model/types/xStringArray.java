package cn.ma.cei.model.types;

import cn.ma.cei.finalizer.Alias;
import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.buildin.TheArray;
import cn.ma.cei.model.base.xType;

import javax.xml.bind.annotation.XmlRootElement;

@Alias(name = "StringArray")
@XmlRootElement(name = "string_array")
public class xStringArray extends xType {
    public final static String typeName = BuilderContext.genericTypeName(TheArray.typeName, xString.typeName);

    public final static xStringArray inst = new xStringArray();
    
    @Override
    public VariableType getType() {
        return BuilderContext.variableType(TheArray.typeName, BuilderContext.variableType(xString.typeName));
    }
}
