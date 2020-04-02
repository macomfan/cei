package cn.ma.cei.model.processor;

import cn.ma.cei.model.base.xDataProcessorItem;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "url_escape")
public class xURLEscape extends xDataProcessorItem {
    @XmlAttribute(name = "name", required = true)
    public String name;

    @XmlAttribute(name = "input", required = true)
    public String input;
}
