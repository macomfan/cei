package cn.ma.cei.model.types;

import cn.ma.cei.finalizer.Alias;
import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.model.base.xType;

import javax.xml.bind.annotation.XmlRootElement;

@Alias(name = "Integer")
@XmlRootElement(name = "int")
public class xInt extends xType {
    public final static String typeName = "int";

    public final static xInt inst = new xInt();
    
    @Override
    public VariableType getType() {
        return BuilderContext.variableType(typeName);
    }
}