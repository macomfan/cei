package cn.ma.cei.model.base;

import cn.ma.cei.generator.VariableType;
import cn.ma.cei.generator.builder.MethodBuilder;
import cn.ma.cei.xml.XmlElementBase;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.HashMap;
import java.util.Map;

@XmlElementBase
public abstract class xType extends xElement {
    public abstract VariableType getType();

    @XmlAttribute(name = "name")
    public String name;

    @XmlAttribute(name = "mandatory")
    public String mandatory = "false";

    @XmlAttribute(name = "default")
    public String defaultValue;
}
