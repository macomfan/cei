package cn.ma.cei.model.types;

import cn.ma.cei.finalizer.Alias;
import cn.ma.cei.generator.environment.VariableType;
import cn.ma.cei.generator.environment.VariableFactory;
import cn.ma.cei.model.base.xType;

import javax.xml.bind.annotation.XmlRootElement;

@Alias(name = "String")
@XmlRootElement(name = "string")
public class xString extends xType {
    public final static String typeName = "string";

    public final static xString inst = new xString();
    @Override
    public VariableType getType() {
        return VariableFactory.variableType(typeName);
    }
}
