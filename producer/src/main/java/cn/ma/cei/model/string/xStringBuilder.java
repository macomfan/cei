package cn.ma.cei.model.string;

import cn.ma.cei.model.base.xDataProcessorItem;
import cn.ma.cei.xml.CEIXmlAnyElementTypes;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "string_builder")
public class xStringBuilder extends xDataProcessorItem {

    @XmlAttribute(name = "name")
    public String name;

    @XmlAnyElement(lax = true)
    @CEIXmlAnyElementTypes({
            xAddStringItem.class,
            xCombineStringItems.class})
    public List<xDataProcessorItem> items;
}
