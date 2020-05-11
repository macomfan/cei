package cn.ma.cei.model.string;

import cn.ma.cei.model.base.xDataProcessorItem;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "add_string_array")
public class xAddStringArray extends xDataProcessorItem {
    @XmlAttribute(name = "input", required = true)
    public String input;

    @XmlAttribute(name = "trim")
    public boolean trim;
}
