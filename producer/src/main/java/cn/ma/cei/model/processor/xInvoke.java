package cn.ma.cei.model.processor;

import cn.ma.cei.model.base.xDataProcessorItem;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "invoke")
public class xInvoke extends xDataProcessorItem {
    @XmlAttribute(name = "function")
    public String function;

    @XmlAttribute(name = "return")
    public String returnVariable;

    @XmlAttribute(name = "arguments")
    public String arguments;
}
