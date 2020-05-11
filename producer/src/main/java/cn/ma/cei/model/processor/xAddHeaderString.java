package cn.ma.cei.model.processor;

import cn.ma.cei.model.base.xDataProcessorItem;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "add_header_string")
public class xAddHeaderString extends xDataProcessorItem {
    @XmlAttribute(name = "input")
    public String input;

    @XmlAttribute(name = "key", required = true)
    public String key;

    @XmlAttribute(name = "value", required = true)
    public String value;

    @Override
    public void customCheck() {
        super.customCheck();
        checkMemberNotNull(key, "key");
        checkMemberNotNull(value, "value");
    }
}
