package cn.ma.cei.model.base;

import cn.ma.cei.generator.environment.VariableType;

import javax.xml.bind.annotation.XmlAttribute;

public abstract class xType extends xElement {
    public abstract VariableType getType();

    @XmlAttribute(name = "name")
    public String name;

    @XmlAttribute(name = "mandatory")
    public String mandatory = "false";

    @XmlAttribute(name = "default")
    public String defaultValue;
}
