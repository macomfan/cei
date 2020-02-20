package cn.ma.cei.model.types;

import cn.ma.cei.finalizer.Alias;
import cn.ma.cei.generator.BuilderContext;
import cn.ma.cei.generator.VariableType;
import cn.ma.cei.model.base.xType;

import javax.xml.bind.annotation.XmlRootElement;

@Alias(name = "Boolean")
@XmlRootElement(name = "boolean")
public class xBoolean extends xType {

    public final static String typeName = "boolean";

    public final static xBoolean inst = new xBoolean();
    
    @Override
    public VariableType getType() {
        return BuilderContext.variableType(typeName);
    }
}
